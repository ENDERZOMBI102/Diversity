package com.enderzombi102.diversity.flora.registry;

import com.enderzombi102.diversity.flora.config.ConfigManager;
import com.enderzombi102.diversity.flora.util.Const;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.ActionResult;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

import java.util.Objects;

public final class EventListeners {
	private EventListeners() {}

	public static void register() {

	}

	public static void registerClient() {
		// <config>
		ConfigManager.getHolder().registerSaveListener( ( holder, cfg ) -> {
			if ( MinecraftClient.getInstance().isIntegratedServerRunning() ) {
				ConfigManager.setActiveConfig( cfg );
				ConfigManager.syncConfig(
					Objects.requireNonNull( MinecraftClient.getInstance().getServer() ),
					MinecraftClient.getInstance().getServer().getPlayerManager().getPlayer(
						Objects.requireNonNull( MinecraftClient.getInstance().player ).getUuid()
					)
				);
			}
			return ActionResult.SUCCESS;
		} );
		ClientPlayNetworking.registerGlobalReceiver( Const.CONFIG_SYNC_ID, (client, networkHandler, data, sender) -> {
			String version = data.readString();
			String config = data.readString();

			client.execute( () -> ConfigManager.fromPacket(version, config) );
		});
		// </config>
	}
}
