package com.enderzombi102.endconfig.impl;

import blue.endless.jankson.JsonObject;
import com.enderzombi102.endconfig.api.ConfigHolder;
import com.enderzombi102.endconfig.api.Data;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.network.PacketByteBuf;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.qsl.networking.api.PacketByteBufs;

import java.nio.file.Path;

import static com.enderzombi102.endconfig.impl.Const.*;
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
	@SuppressWarnings("unchecked")
	public void reset( boolean toDefault ) {
		if ( toDefault )
			this.data = doSafely( () -> (T) this.data.getClass().getConstructor().newInstance() );
		else
			this.loadFromDisk();
	}

	// region IMPL DETAIL

	private void loadFromDisk() {

	}

	void update( JsonObject obj ) {
		// TODO: Read from json
		LOGGER.info("{}", obj);
	}

	public PacketByteBuf packet() {
		// return the PacketByteBuf version of this config
		var buf = PacketByteBufs.create();
		buf.writeString( modid );
		buf.writeString( JANKSON.toJson( get() ).toJson(MINIFIED) );
		return buf;
	}

	// endregion
}
