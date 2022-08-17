package com.enderzombi102.diversity.flora.mixin;

import com.enderzombi102.diversity.flora.biome.CrystalForestBiome;
import net.minecraft.util.Holder;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BuiltinBiomes;
import net.minecraft.world.biome.OverworldBiomeCreator;
import net.minecraft.world.biome.source.util.OverworldBiomeParameters;
import org.quiltmc.qsl.worldgen.surface_rule.impl.SurfaceRuleContextImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.enderzombi102.diversity.flora.util.Const.getId;

@Mixin(BuiltinBiomes.class)
public class BuiltinBiomesMixin {
	@Inject( method = "bootstrap", at = @At( value = "RETURN", shift = At.Shift.BEFORE ) )
	private static void onBootstrap( Registry<Biome> registry, CallbackInfoReturnable<Holder<Biome>> cir ) {
		BuiltinRegistries.register( registry, RegistryKey.of( Registry.BIOME_KEY, getId("crystal_forest") ), CrystalForestBiome.make() );
	}
}
