package com.enderzombi102.endconfig.impl;

import blue.endless.jankson.JsonElement;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.JsonPrimitive;
import com.enderzombi102.endconfig.api.ConfigHolder;
import com.enderzombi102.endconfig.api.ConfigOptions.Ignore;
import com.enderzombi102.endconfig.api.ConfigOptions.RenamingPolicy;
import com.enderzombi102.endconfig.api.ConfigOptions.Sync;
import com.enderzombi102.endconfig.api.Data;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.network.PacketByteBuf;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.qsl.networking.api.PacketByteBufs;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Function;

import static com.enderzombi102.endconfig.impl.Const.LOGGER;
import static com.enderzombi102.endconfig.impl.Const.MINIFIED;
import static com.enderzombi102.enderlib.SafeUtils.doSafely;

public class ConfigHolderImpl<T extends Data> implements ConfigHolder<T> {
	private final Class<T> dataClass;
	private final String modid;
	private final Path path;
	private T data = null;

	public ConfigHolderImpl( String modid, Path path, Class<T> dataClass ) {
		this.dataClass = dataClass;
		this.modid = modid;
		this.path = path;
	}

	@Override
	public T get() {
		if ( this.data == null )
			this.loadFromDisk();
		return this.data;
	}

	@Override
	public Path path() {
		return this.path;
	}

	@Override
	public String modid() {
		return this.modid;
	}

	@Override
	public ModContainer mod() {
		return QuiltLoader.getModContainer( this.modid ).orElseThrow();
	}

	@Override
	@Environment( EnvType.CLIENT )
	public Screen screen( Screen parent ) {
		return new ConfigScreen( this, parent );
	}

	@Override
	public void reset( boolean toDefault ) {
		if ( toDefault )
			this.data = doSafely( () -> this.dataClass.getConstructor().newInstance() );
		else
			this.loadFromDisk();
	}

	// region IMPL DETAIL

	private void loadFromDisk() {
		this.data = doSafely( this.dataClass::newInstance );
	}

	void update( JsonObject obj ) {
		// TODO: Read from json
		LOGGER.info("{}", obj);
	}

	public PacketByteBuf packet() {
		// return the PacketByteBuf version of this config
		var buf = PacketByteBufs.create();
		buf.writeString( modid );
		buf.writeString( doSafely( () -> serialize( get(), true ).toJson(MINIFIED) ) );
		return buf;
	}

	private static JsonElement serialize( Object data, boolean sync ) throws IllegalAccessException {
		var object = new JsonObject();
		var globalSyncSetting = Optional.ofNullable( data.getClass().getAnnotation( Sync.class ) )
			.map( Sync::value )
			.orElse( false );

		for ( var field : data.getClass().getFields() ) {
			// ignore fields marked with @Ignore
			if ( field.isAnnotationPresent( Ignore.class ) )
				continue;
			// ignore unsyncable elements in sync mode
			if (
				sync &&
				!Optional.ofNullable( field.getAnnotation( Sync.class ) ).map( Sync::value ).orElse( globalSyncSetting )
			) continue;
			var value = field.get( data );

			// region object to json element
			JsonElement element;
			if ( value instanceof String string ) {
				element = JsonPrimitive.of( string );
			} else if ( value instanceof Integer integer ) {
				element = JsonPrimitive.of( integer.longValue() );
			} else if ( value instanceof Long aLong ) {
				element = JsonPrimitive.of( aLong );
			} else if ( value instanceof Double aDouble ) {
				element = JsonPrimitive.of( aDouble );
			} else if ( value instanceof Float aFloat ) {
				element = JsonPrimitive.of( aFloat.doubleValue() );
			} else if ( value instanceof Boolean aBoolean ) {
				element = JsonPrimitive.of( aBoolean );
			} else if ( value instanceof Short aShort ) {
				element = JsonPrimitive.of( aShort.longValue() );
			} else if ( value instanceof Byte aByte ) {
				element = JsonPrimitive.of( aByte.longValue() );
			} else if ( value instanceof Enum<?> anEnum ) {
				// it's an enum
				Function<Enum<?>, String> transform = Util::toPascal;
				if ( field.isAnnotationPresent( RenamingPolicy.class ) ) {
					transform = switch ( field.getAnnotation( RenamingPolicy.class ).value() ) {
						case "pascal" -> Util::toPascal; // PascalCase
						case "snake" -> Util::toSnake;   // snake_case
						case "custom" -> Util::custom;	 // to() call
						case "named" -> Util::named;	 // @Name() value
						default -> throw new IllegalStateException();
					};
				}

				element = JsonPrimitive.of( transform.apply( anEnum ) );
			} else {
				// it's another object
				element = serialize( value, sync );
			}
			// endregion

			object.put( field.getName(), element );
		}


		return object;
	}

	// endregion
}
