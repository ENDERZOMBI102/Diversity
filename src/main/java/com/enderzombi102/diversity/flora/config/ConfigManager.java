package com.enderzombi102.diversity.flora.config;


import com.enderzombi102.diversity.flora.config.data.MainConfig;
import com.enderzombi102.diversity.flora.util.Const;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigManager {
	private static final Gson GSON = new GsonBuilder()
			.setExclusionStrategies(new AnnotationExclusionStrategy())
			.setPrettyPrinting()
			.create();
	private static final Logger LOGGER = LoggerFactory.getLogger("Diversity | Flora | Config");
	private static MainConfig activeConfig = null;

	private ConfigManager() { }

	public static MainConfig getActiveConfig() {
		if ( activeConfig == null ) {
			AutoConfig.register(
				MainConfig.class,
				PartitioningSerializer.wrap(
					(definition, configClass) -> new GsonConfigSerializer<>(
						definition,
						configClass,
						GSON
					)
				)
			);
			LOGGER.info("config registered!");
			setActiveConfig(null);
		}

		return activeConfig;
	}

	public static void setActiveConfig(@Nullable MainConfig config) {
		if (config == null)
			config = getHolder().get();

		activeConfig = config;
	}

	public static ConfigHolder<MainConfig> getHolder() {
		return AutoConfig.getConfigHolder(MainConfig.class);
	}

	public static void fromPacket(String version, String configData) {
		MainConfig config = null;
		try {
			if ( Const.VERSION.equals(version) )
				config = GSON.fromJson( configData, MainConfig.class );
			else
				throw new IllegalStateException("Non matching Diversity: Flora versions!");
		} catch (JsonSyntaxException e) {
			LOGGER.error( "Can't parse config! Falling back to local", e );
		}

		setActiveConfig(config);
		MinecraftClient.getInstance().worldRenderer.reload();
	}

	public static PacketByteBuf toPacketByteBuf() {
		String config = GSON.toJson( getActiveConfig() );
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeString(Const.VERSION);
		buf.writeString(config);
		return buf;
	}

	public static void syncConfig( MinecraftServer server, @Nullable PlayerEntity excluded ) {
		for ( ServerPlayerEntity player : server.getPlayerManager().getPlayerList() ) {
			if ( player != excluded )
				ServerPlayNetworking.send(
					player,
					Const.CONFIG_SYNC_ID,
					toPacketByteBuf()
				);
		}
	}
}

