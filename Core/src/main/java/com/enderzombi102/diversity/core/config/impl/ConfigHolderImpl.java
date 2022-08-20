package com.enderzombi102.diversity.core.config.impl;

import com.enderzombi102.diversity.core.config.api.ConfigHolder;
import com.enderzombi102.diversity.core.config.api.Data;
import net.minecraft.client.gui.screen.Screen;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;

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
	public ModContainer mod() {
		return QuiltLoader.getModContainer( this.modid ).orElseThrow();
	}

	@Override
	public Screen screen() {
		return new ConfigScreen();
	}

	@Override
	@SuppressWarnings("unchecked")
	public void reset() {
		this.data = doSafely( () -> (T) this.data.getClass().getConstructor().newInstance() );
	}
}
