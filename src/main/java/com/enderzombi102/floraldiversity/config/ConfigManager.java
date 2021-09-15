package com.enderzombi102.floraldiversity.config;


import com.enderzombi102.floraldiversity.config.data.MainConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gl.ShaderEffect;
import net.minecraft.network.PacketByteBuf;
import org.jetbrains.annotations.Nullable;

import static com.enderzombi102.floraldiversity.util.Const.MOD_ID;
import static com.enderzombi102.floraldiversity.FloralDiversity.LOGGER;
import static com.enderzombi102.floraldiversity.util.Const.MOD_NAME;

public final class ConfigManager {
	public static final Gson CONFIG_GSON = new GsonBuilder()
			.setExclusionStrategies( new AnnotationExclusionStrategy() )
			.setPrettyPrinting()
			.create();
	public static ShaderEffect shader = null;
	private static MainConfig CURRENT_CONFIG = null;

	private ConfigManager() {}

	public static MainConfig getLoadedConfig() {
		if (CURRENT_CONFIG == null) {
			AutoConfig.register(
					MainConfig.class,
					PartitioningSerializer.wrap(
							(definition, configClass) -> new GsonConfigSerializer<>(
									definition,
									configClass,
									CONFIG_GSON
							)
					)
			);
			LOGGER.info("{} config registered!", MOD_NAME );
			CURRENT_CONFIG = getHolder().getConfig();
		}

		return CURRENT_CONFIG;
	}

	public static void loadConfig(@Nullable MainConfig config) {
		if (config == null)
			config = getHolder().getConfig();

		CURRENT_CONFIG = config;
	}

	public static ConfigHolder<MainConfig> getHolder() {
		return AutoConfig.getConfigHolder(MainConfig.class);
	}

	public static PacketByteBuf toPacketByteBuf() {
		String version = FabricLoader.getInstance()
				.getModContainer(MOD_ID)
				.orElseThrow()
				.getMetadata()
				.getVersion()
				.getFriendlyString();
		String config = CONFIG_GSON.toJson(getLoadedConfig());
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeString(version);
		buf.writeString(config);
		return buf;
	}

	public static void load() {
		getHolder().load();
	}

	public static void onLoad() {
	}
}

