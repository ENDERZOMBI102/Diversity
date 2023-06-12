package com.enderzombi102.diversity.flora;

import com.enderzombi102.diversity.flora.config.Config;
import com.enderzombi102.diversity.flora.registry.*;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mineralogy implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger( "Diversity | Mineralogy" );

	@Override
	public void onInitialize( ModContainer container ) {
		Config.save();
		BlockRegistry.init();
		BlockEntityRegistry.init();
		ItemRegistry.init();
		BiomeRegistry.register();
		EventListeners.register();
		LOGGER.info( "Diversity module `Flora` loaded!" );
	}
}
