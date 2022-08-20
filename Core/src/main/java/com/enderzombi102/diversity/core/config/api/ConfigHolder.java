package com.enderzombi102.diversity.core.config.api;

import net.minecraft.client.gui.screen.Screen;
import org.quiltmc.loader.api.ModContainer;

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
	 * Returns the ModContainer of the mod associated with this config
	 */
	ModContainer mod();

	/**
	 * Returns the config screen for this config
	 */
	Screen screen();

	/**
	 * Resets this config to default values.
	 */
	void reset();
}
