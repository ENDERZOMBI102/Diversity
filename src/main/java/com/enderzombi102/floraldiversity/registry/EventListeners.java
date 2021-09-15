package com.enderzombi102.floraldiversity.registry;

import com.enderzombi102.floraldiversity.config.ConfigManager;
import com.enderzombi102.floraldiversity.config.data.MainConfig;
import com.google.gson.JsonSyntaxException;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

import static com.enderzombi102.floraldiversity.util.Const.CONFIG_SYNC_ID;
import static com.enderzombi102.floraldiversity.FloralDiversity.LOGGER;
import static com.enderzombi102.floraldiversity.util.Const.MOD_VERSION;

public final class EventListeners {
	private EventListeners() {}

	public static void register() {

	}

	public static void registerClient() {
		ClientPlayNetworking.registerGlobalReceiver(
				CONFIG_SYNC_ID,
				(client, networkHandler, data, sender) -> {
					MainConfig config = null;
					try {
						if ( MOD_VERSION.equals( data.readString() ) )
							config = ConfigManager.CONFIG_GSON.fromJson(data.readString(), MainConfig.class);
					} catch (JsonSyntaxException e) {
						LOGGER.error("Can't parse config!", e);
					}
					if (config == null)
						LOGGER.warn(
								"Failed to load synced config, falling back to local config!"
						);

					MainConfig finalConfig = config;
					client.execute(() -> {
						ConfigManager.loadConfig(finalConfig);
						ConfigManager.onLoad();
					});
				}
		);
	}
}
