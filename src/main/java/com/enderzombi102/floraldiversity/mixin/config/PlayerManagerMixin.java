package com.enderzombi102.floraldiversity.mixin.config;

import com.enderzombi102.floraldiversity.config.ConfigManager;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.enderzombi102.floraldiversity.util.Const.CONFIG_SYNC_ID;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
	@Inject( method = "onPlayerConnect", at = @At("TAIL") )
	private void onPlayerConnect(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
		ServerPlayNetworking.send(
				player,
				CONFIG_SYNC_ID,
				ConfigManager.toPacketByteBuf()
		);
	}
}
