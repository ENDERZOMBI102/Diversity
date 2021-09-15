package com.enderzombi102.floraldiversity.mixin.config;

import com.enderzombi102.floraldiversity.config.ConfigManager;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {
	@Inject(method = "disconnect", at = @At("TAIL"))
	private void onDisconnect(CallbackInfo ci) {
		ConfigManager.loadConfig(null);
	}
}
