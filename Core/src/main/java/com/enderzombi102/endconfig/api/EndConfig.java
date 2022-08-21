package com.enderzombi102.endconfig.api;

import com.enderzombi102.endconfig.impl.EndConfigImpl;
import org.quiltmc.loader.api.QuiltLoader;

import java.nio.file.Path;

public class EndConfig {
	/**
	 * Registers a config.
	 * @param modid id of the owner mod
	 * @param filePath path to save the config to
	 * @param config config object
	 */
	public static <T extends Data> void register( String modid, Path filePath, T config ) {
		EndConfigImpl.register( modid, filePath, config );
	}

	/**
	 * Registers a config.
	 * @param modid id of the owner mod
	 * @param filename name of the config file on disk
	 * @param config config object
	 */
	public static <T extends Data> void register( String modid, String filename, T config ) {
		EndConfigImpl.register( modid, QuiltLoader.getConfigDir().resolve( filename ), config );
	}

	/**
	 * Registers a config.
	 * @param modid id of the owner mod
	 * @param config config object
	 */
	public static <T extends Data> void register( String modid, T config ) {
		EndConfigImpl.register( modid, QuiltLoader.getConfigDir().resolve( modid + ".json5" ), config );
	}

	/**
	 * Gets a config object from the modid
	 * @param modid id of the owner mod
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Data> T get( String modid ) {
		return (T) EndConfigImpl.get( modid ).get();
	}

	/**
	 * Gets the holder for the config of the requested mod
	 * @param modid id of the owner mod
	 */
	public static <T extends Data> ConfigHolder<T> getHolder( String modid ) {
		return EndConfigImpl.get( modid );
	}

	/**
	 * Saves a given config
	 * @param modid id of the owner mod
	 */
	public static void save( String modid ) {
		EndConfigImpl.save( modid );
	}

	/**
	 * @param modid id of the owner mod
	 * @param listener listener object
	 */
	public static <T extends Data> void registerChangeListener( String modid, ChangeListener<T> listener ) {
		EndConfigImpl.registerChangeListener( modid, listener );
	}
}
