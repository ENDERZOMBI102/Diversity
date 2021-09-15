package com.enderzombi102.floraldiversity;

import com.enderzombi102.floraldiversity.registry.EventListeners;
import com.enderzombi102.floraldiversity.registry.ItemRegistry;
import com.enderzombi102.floraldiversity.registry.SoundRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class FloralDiversityClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ItemRegistry.registerClient();
		SoundRegistry.registerClient();
		EventListeners.registerClient();
	}
}
