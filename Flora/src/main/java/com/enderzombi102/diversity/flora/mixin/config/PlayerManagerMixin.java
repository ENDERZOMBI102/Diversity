package com.enderzombi102.diversity.flora.mixin.config;

import com.enderzombi102.diversity.flora.config.ConfigManager;
import com.enderzombi102.diversity.flora.util.Const;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
	@Inject( method = "onPlayerConnect", at = @At("TAIL") )
	private void onPlayerConnect(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
		ServerPlayNetworking.send(
			player,
			Const.CONFIG_SYNC_ID,
			ConfigManager.toPacketByteBuf()
		);
	}
}
