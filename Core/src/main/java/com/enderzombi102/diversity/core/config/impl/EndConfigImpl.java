package com.enderzombi102.diversity.core.config.impl;

import blue.endless.jankson.Jankson;
import com.enderzombi102.diversity.core.config.api.ChangeListener;
import com.enderzombi102.diversity.core.config.api.ConfigHolder;
import com.enderzombi102.diversity.core.config.api.Data;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EndConfigImpl {
	static final Jankson JANKSON = Jankson.builder().build();
	static final BiMap<String, ConfigHolder<?>> CONFIGS = HashBiMap.create();
	static final Map<String, List<ChangeListener<?>>> LISTENERS = new HashMap<>();

	public static <T extends Data> void register( String modid, Path path, T config ) {
		CONFIGS.put( modid, new ConfigHolderImpl<>( modid, path, config ) );
	}

	@SuppressWarnings("unchecked")
	public static <T extends Data> ConfigHolder<T> get( String modid ) {
		if (! CONFIGS.containsKey( modid ) )
			throw new IllegalStateException( "Config for mod " + modid + " was not registered!" );

		return (ConfigHolder<T>) CONFIGS.get( modid );
	}

	public static void save( String modid ) {
		var config = get( modid );
	}



	public static <T extends Data> void registerChangeListener( String modid, ChangeListener<T> listener ) {
	}
}
