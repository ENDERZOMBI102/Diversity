package com.enderzombi102.diversity.core

import com.enderzombi102.diversity.core.config.ConfigData
import com.enderzombi102.diversity.core.module.DiversityModules
import com.enderzombi102.diversity.core.registry.Registries
import com.enderzombi102.endconfig.api.ConfigHolder
import com.enderzombi102.endconfig.api.EndConfig
import org.quiltmc.loader.api.ModContainer
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer
import org.slf4j.LoggerFactory

class Core : ModInitializer {
	init { DiversityModules.construct() }

	override fun onInitialize( mod: ModContainer ) {
		EndConfig.register( MODID, ConfigData::class.java )
		EndConfig.registerChangeListener( MODID, ::onConfigChanged )

		saveConfig()

		for ( module in DiversityModules.modules() ) {
			currentId = module.container.metadata().id()
			module.main?.instance?.onInitialize( module.container )
		}
		currentId = null

		Registries.register()

		LOGGER.info("Let's get diverse!")
		LOGGER.info("Diversity Core v{}", mod.metadata().version().raw() )
	}

	companion object {
		var currentId: String? = null

		@JvmStatic
		val LOGGER = LoggerFactory.getLogger("Diversity | Core")!!
		const val MODID = "diversity-core"

		@JvmStatic
		fun config(): ConfigData {
			return EndConfig.get(MODID)
		}

		@JvmStatic
		fun saveConfig() {
			EndConfig.save(MODID)
		}

		@JvmStatic
		fun holder(): ConfigHolder<ConfigData> {
			return EndConfig.getHolder(MODID)
		}

		@JvmStatic
		fun onConfigChanged( config: ConfigHolder<ConfigData> ) {
			println("Config changed!")
		}
	}
}