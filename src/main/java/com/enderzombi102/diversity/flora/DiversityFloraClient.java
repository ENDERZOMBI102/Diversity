package com.enderzombi102.diversity.flora;

import com.enderzombi102.diversity.flora.registry.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

@Environment(EnvType.CLIENT)
public class DiversityFloraClient implements ClientModInitializer {
	@Override
	public void onInitializeClient(ModContainer container) {
		SoundRegistry.registerClient();
		BiomeRegistry.registerClient();
//		ColorRegistry.registerClient();
		EventListeners.registerClient();
	}
}
