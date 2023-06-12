package com.enderzombi102.diversity.core

import com.enderzombi102.diversity.core.module.DiversityModules
import com.enderzombi102.diversity.core.registry.Registries
import org.jetbrains.annotations.ApiStatus.Internal
import org.quiltmc.loader.api.ModContainer
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer
import org.slf4j.LoggerFactory

class Core : ModInitializer {
	init { DiversityModules.construct() }

	override fun onInitialize( mod: ModContainer ) {
		// propagate module initialization
		for ( module in DiversityModules.modules() ) {
			currentId = module.container.metadata().id()
			module.main?.instance?.onInitialize( module.container )
		}
		currentId = null

		Registries.register()

		LOGGER.info( "Let's get diverse!" )
		LOGGER.info( "Diversity Core v{}", mod.metadata().version().raw() )
	}

	companion object {
		@Internal
		internal var currentId: String? = null

		@JvmStatic
		val LOGGER = LoggerFactory.getLogger( "Diversity | Core" )!!
		const val MODID = "diversity-core"
	}
}