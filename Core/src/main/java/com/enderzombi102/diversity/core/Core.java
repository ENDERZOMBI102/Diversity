package com.enderzombi102.diversity.core;

import com.enderzombi102.diversity.core.config.ConfigData;
import com.enderzombi102.diversity.core.config.api.ConfigHolder;
import com.enderzombi102.diversity.core.config.api.Data;
import com.enderzombi102.diversity.core.config.api.EndConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

public class Core implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("Diversity | Core");
	private static final String MODID = "diversity-core";

	@Override
	public void onInitialize(ModContainer mod) {
		EndConfig.register( MODID, new ConfigData() );
		EndConfig.registerChangeListener( MODID, Core::onConfigChanged );

		LOGGER.info( "Let's get diverse!" );
		LOGGER.info( "Diversity Core v{}", mod.metadata().version().raw() );
	}


	public static ConfigData config() {
		return EndConfig.get( MODID );
	}

	public static void onConfigChanged( ConfigHolder<ConfigData> config ) {
		System.out.println("Config changed!");
	}
}
