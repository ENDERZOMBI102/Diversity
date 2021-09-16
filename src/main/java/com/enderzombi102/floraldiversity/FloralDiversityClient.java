package com.enderzombi102.floraldiversity;

import com.enderzombi102.floraldiversity.registry.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class FloralDiversityClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ItemRegistry.registerClient();
		SoundRegistry.registerClient();
		BiomeRegistry.registerClient();
		ColorRegistry.registerClient();
		EventListeners.registerClient();
	}
}
