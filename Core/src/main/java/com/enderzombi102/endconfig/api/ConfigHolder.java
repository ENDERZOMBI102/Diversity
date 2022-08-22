package com.enderzombi102.endconfig.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;

import java.nio.file.Path;

public interface ConfigHolder<T extends Data> {
	/**
	 * Returns the config object
	 */
	T get();

	/**
	 * Returns the config file position
	 */
	Path path();

	/**
	 * Returns the modid this config is associated with
	 */
	String modid();

	/**
	 * Returns the config screen for this config
	 * @param parent the parent of the config screen
	 */
	@Environment( EnvType.CLIENT )
	Screen screen( Screen parent );

	/**
	 * Resets this config to default values.
	 */
	void reset();
}
