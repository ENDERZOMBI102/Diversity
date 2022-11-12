package com.enderzombi102.diversity.core

import com.enderzombi102.diversity.core.module.DiversityModules.modules
import com.enderzombi102.diversity.core.util.Used
import org.quiltmc.loader.api.ModContainer
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer

@Used
class CoreClient : ClientModInitializer {
	override fun onInitializeClient( mod: ModContainer ) {
		for ( module in modules() )
			module.client?.instance?.onInitializeClient( mod )
	}
}
