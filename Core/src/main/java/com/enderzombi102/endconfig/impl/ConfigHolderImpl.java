package com.enderzombi102.endconfig.impl;

import com.enderzombi102.endconfig.ConfigHolder;
import com.enderzombi102.endconfig.Data;
import net.minecraft.client.gui.screen.Screen;

import java.nio.file.Path;

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
	public Screen screen() {
		return new ConfigScreen( this );
	}

	@Override
	@SuppressWarnings("unchecked")
	public void reset() {
		this.data = doSafely( () -> (T) this.data.getClass().getConstructor().newInstance() );
	}
}
