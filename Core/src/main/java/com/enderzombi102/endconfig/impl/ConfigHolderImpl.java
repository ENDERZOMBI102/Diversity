package com.enderzombi102.endconfig.impl;

import blue.endless.jankson.JsonElement;
import blue.endless.jankson.JsonNull;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.JsonPrimitive;
import blue.endless.jankson.api.SyntaxError;
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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;

import static com.enderzombi102.endconfig.impl.Const.*;
import static com.enderzombi102.enderlib.SafeUtils.doSafely;

public class ConfigHolderImpl<T extends Data> implements ConfigHolder<T> {
	private final Class<T> dataClass;
	private final String modid;
	private final Path path;
	private T data = null;
	private boolean overwrittenByServer = false;

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
		return QuiltLoader.getModContainer( this.modid() ).orElseThrow();
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
		try {
			this.data = JANKSON.fromJson( Files.readString( this.path() ), this.dataClass );
		} catch ( SyntaxError | IOException e ) {
			LOGGER.error( "Failed to load {}'s config from disk!", this.modid(), e );
			LOGGER.warn( "Setting {}'s config to default...", this.modid() );
			this.reset( true );
		}
	}

	void update( JsonObject obj ) {
		deserialize( this.data, obj, this.modid() );
	}

	public PacketByteBuf packet() {
		return PacketByteBufs.create()
			.writeString( this.modid() )
			.writeString( doSafely( () -> serialize( get(), true, this.modid() ).toJson(MINIFIED) ) );
	}

	private static JsonElement serialize( Object data, boolean sync, String modid ) throws IllegalAccessException {
		var object = new JsonObject();
		var globalSyncSetting = Util.annotationOr( data.getClass(), Sync.class, Sync::value,false );

		for ( var field : data.getClass().getFields() ) {
			// ignore fields marked with @Ignore
			if ( field.isAnnotationPresent( Ignore.class ) )
				continue;
			// ignore unsyncable elements in sync mode
			if ( sync && !Util.annotationOr( field, Sync.class, Sync::value, globalSyncSetting ) )
				continue;
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
				Function<Enum<?>, String> transform = Util::asIs;
				if ( field.isAnnotationPresent( RenamingPolicy.class ) ) {
					transform = switch ( field.getAnnotation( RenamingPolicy.class ).value() ) {
						case "pascal" -> Util::toPascal; // PascalCase
						case "snake" -> Util::toSnake;   // snake_case
						case "named" -> Util::named;	 // @Name() value
						case "asis" -> Util::asIs;	 	 // Enum::Name() value
						default -> throw new IllegalStateException(
							"Mod %s has set `RenamingPolicy` on field `%s` to an invalid value `%s`!".formatted(
								modid,
								field.getDeclaringClass().getName() + "::" + field.getName(),
								field.getAnnotation( RenamingPolicy.class ).value()
							)
						);
					};
				}

				element = JsonPrimitive.of( transform.apply( anEnum ) );
			} else if ( value == null ) {
				element = JsonNull.INSTANCE;
			} else {
				// it's another object
				element = serialize( value, sync, modid );
			}
			// endregion

			object.put( field.getName(), element );
		}

		return object;
	}

	private static void deserialize( Object dest, JsonObject obj, String modid ) {
		for ( var field : dest.getClass().getDeclaredFields() )
			if ( obj.containsKey( field.getName() ) ) {
				try {
					if ( field.getType().equals( String.class ) ) {
						field.set( dest, obj.get( field.getName() ) );

					} else if ( field.getType().equals( Integer.class ) ) {
						field.set( dest, obj.getInt( field.getName(), field.getInt( dest ) ) );

					} else if ( field.getType().equals( Long.class ) ) {
						field.set( dest, obj.getLong( field.getName(), field.getLong( dest ) ) );

					} else if ( field.getType().equals( Double.class ) ) {
						field.set( dest, obj.get( field.getName() );

					} else if ( field.getType().equals( Float.class ) ) {
						field.set( dest, obj.get( field.getName() );

					} else if ( field.getType().equals( Boolean.class ) ) {
						field.set( dest, obj.get( field.getName() );

					} else if ( field.getType().equals( Short.class ) ) {
						field.set( dest, obj.get( field.getName() );

					} else if ( field.getType().equals( Byte.class ) ) {
						field.set( dest, obj.get( field.getName() );

					} else if ( value instanceof Enum<?> anEnum ) {
						// it's an enum
						Function<Enum<?>, String> transform = Util::asIs;
						if ( field.isAnnotationPresent( RenamingPolicy.class ) ) {
							transform = switch ( field.getAnnotation( RenamingPolicy.class ).value() ) {
								case "pascal" -> Util::toPascal; // PascalCase
								case "snake" -> Util::toSnake;   // snake_case
								case "named" -> Util::named;	 // @Name() value
								case "asis" -> Util::asIs;	 	 // Enum::Name() value
								default -> throw new IllegalStateException(
									"Mod %s has set `RenamingPolicy` on field `%s` to an invalid value `%s`!".formatted(
										modid,
										field.getDeclaringClass().getName() + "::" + field.getName(),
										field.getAnnotation( RenamingPolicy.class ).value()
									)
								);
							};
						}

						field.set( dest, JsonPrimitive.of( transform.apply( anEnum ) );
					} else if ( value == null ) {
						field.set( dest, JsonNull.INSTANCE;
					} else {
						// it's another object
						deserialize( field.get( dest ), obj.getObject( field.getName() ), modid );
					}
				} catch ( IllegalAccessException ignore ) { }
			}
	}

	// endregion
}
