package com.enderzombi102.diversity.flora;

import com.enderzombi102.diversity.flora.registry.*;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

public class FloraClient implements ClientModInitializer {
	@Override
	public void onInitializeClient(ModContainer container) {
//		SoundRegistry.registerClient();
		BiomeRegistry.registerClient();
//		ColorRegistry.registerClient();
		EventListeners.registerClient();
		Flora.DATA.registerCreatorsClient();
	}
}
