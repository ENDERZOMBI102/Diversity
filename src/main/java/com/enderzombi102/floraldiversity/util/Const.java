package com.enderzombi102.floraldiversity.util;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.util.Identifier;

import static com.enderzombi102.floraldiversity.FloralDiversity.getId;

public final class Const {
	private Const() {}

	public static final String MOD_NAME = "Floral Diversity";
	public static final String MOD_ID = "floraldiversity";
	public static final Identifier CONFIG_SYNC_ID = getId("config_sync");
	public static final String MOD_VERSION = getMetadata()
			.getVersion()
			.getFriendlyString();

	private static ModContainer getContainer() {
		return FabricLoader.getInstance()
				.getModContainer(MOD_ID)
				.orElseThrow();
	}

	private static ModMetadata getMetadata() {
		return getContainer().getMetadata();
	}



}
