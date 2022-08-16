package com.enderzombi102.diversity.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

public class Core implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("Diversity | Core");

	@Override
	public void onInitialize(ModContainer mod) {
		LOGGER.info( "Let's get diverse!" );
		LOGGER.info( "Diversity Core v{}", mod.metadata().version().raw() );
	}
}
