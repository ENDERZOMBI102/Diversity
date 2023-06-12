package com.enderzombi102.diversity.flora;

import com.enderzombi102.diversity.flora.registry.*;
import net.minecraft.util.ActionResult;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

public class MineralogyClient implements ClientModInitializer {
	@Override
	public void onInitializeClient(ModContainer container) {
//		SoundRegistry.registerClient();
		BiomeRegistry.registerClient();
//		ColorRegistry.registerClient();
		EventListeners.registerClient();
	}
}
