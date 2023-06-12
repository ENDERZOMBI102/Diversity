package com.enderzombi102.diversity.core

import com.enderzombi102.diversity.core.module.DiversityModules
import com.enderzombi102.diversity.core.util.Used
import org.quiltmc.loader.api.ModContainer
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer

@Used
class CoreClient : ClientModInitializer {
	override fun onInitializeClient( mod: ModContainer ) {
		// propagate client module initialization
		for ( module in DiversityModules.modules() ) {
			Core.currentId = module.container.metadata().id()
			module.client?.instance?.onInitializeClient( module.container )
		}
		Core.currentId = null
	}
}
