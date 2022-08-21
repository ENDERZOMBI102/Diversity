package com.enderzombi102.endconfig.impl;

import blue.endless.jankson.JsonObject;
import com.enderzombi102.endconfig.api.ConfigHolder;
import com.enderzombi102.endconfig.api.Data;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.network.PacketByteBuf;
import org.quiltmc.qsl.networking.api.PacketByteBufs;

import java.nio.file.Path;

import static com.enderzombi102.endconfig.impl.Const.JANKSON;
import static com.enderzombi102.endconfig.impl.Const.MINIFIED;
import static com.enderzombi102.enderlib.SafeUtils.doSafely;

public class ConfigHolderImpl<T extends Data> implements ConfigHolder<T> {
	private T data;
	private final String modid;
	private final Path path;

	public ConfigHolderImpl( String modid, Path path, T data ) {
		this.modid = modid;
		this.data = data;
		this.path = path;
	}

	@Override
	public T get() {
		// TODO: load from disk if necessary
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
	public Screen screen( Screen parent ) {
		return new ConfigScreen( this, parent );
	}

	@Override
	@SuppressWarnings("unchecked")
	public void reset() {
		this.data = doSafely( () -> (T) this.data.getClass().getConstructor().newInstance() );
	}

	// region IMPL DETAIL

	void update( JsonObject obj ) {
		// TODO: Read from json
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
