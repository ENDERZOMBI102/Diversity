package com.enderzombi102.endconfig.impl;

import blue.endless.jankson.JsonGrammar;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.api.SyntaxError;
import com.enderzombi102.endconfig.api.ConfigHolder;
import com.enderzombi102.endconfig.api.Data;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.network.PacketByteBuf;
import org.jetbrains.annotations.ApiStatus;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.qsl.networking.api.PacketByteBufs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static com.enderzombi102.endconfig.impl.Const.*;
import static com.enderzombi102.endconfig.impl.Util.*;
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
		if ( this.data == null && this.path().toFile().exists() ) {
			LOGGER.info( "Loading {}'s config from disk...", this.modid() );
			this.load();
		} else {
			LOGGER.info( "Creating {}'s config...", this.modid() );
			this.loadDefaults();
			this.save();
		}
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
		LOGGER.info( "Creating screen object for {}...", this.modid() );
		return new ConfigScreen( this, parent );
	}

	@Override
	public void load() {
		try {
			this.data = JANKSON.fromJson( Files.readString( this.path() ), this.dataClass );
		} catch ( SyntaxError | IOException e ) {
			LOGGER.error( "Failed to load {}'s config from disk!", this.modid(), e );
			LOGGER.warn( "Resetting {}'s config to default...", this.modid() );
			this.loadDefaults();
		}
	}

	@Override
	public void loadDefaults() {
		this.data = doSafely( () -> this.dataClass.getConstructor().newInstance() );
	}

	@Override
	public void save() {
		try {
			Files.writeString(
				this.path(),
				doSafely( () -> serialize( data, false, "", this.modid() ).toJson( JSON5 ) ),
				StandardOpenOption.CREATE
			);
			LOGGER.info( "Saved {} to disk!", this.path().getFileName() );
		} catch ( IOException e ) {
			LOGGER.error( "Failed to save {}'s config to disk!", this.modid(), e );
		}
	}

	// region IMPL DETAIL

	void update( JsonObject obj ) {
		deserialize( this.data, obj, this.modid() );
	}

	@ApiStatus.Internal
	public PacketByteBuf packet() {
		return PacketByteBufs.create()
			.writeString( this.modid() )
			.writeString( doSafely( () -> serialize( get(), true, "", this.modid() ).toJson(MINIFIED) ) );
	}

	// endregion
}
