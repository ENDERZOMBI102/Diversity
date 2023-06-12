package com.enderzombi102.diversity.flora.biome;

import com.enderzombi102.diversity.flora.registry.BlockRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Holder;
import net.minecraft.registry.HolderSet;
import net.minecraft.util.math.intprovider.ClampedIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.biome.SpawnSettings.SpawnEntry;
import net.minecraft.world.gen.GenerationStep.Feature;
import net.minecraft.world.gen.decorator.BiomePlacementModifier;
import net.minecraft.world.gen.decorator.CountPlacementModifier;
import net.minecraft.world.gen.decorator.InSquarePlacementModifier;
import net.minecraft.world.gen.decorator.RarityFilterPlacementModifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.util.ConfiguredFeatureUtil;
import net.minecraft.world.gen.feature.util.PlacedFeatureUtil;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class CrystalForestBiome {

//	public static final Holder<ConfiguredFeature<SimpleRandomFeatureConfig, ?>> CRYSTAL_SHARDS_CONFIG = ConfiguredFeatureUtil.register(
//		"diversity-flora:crystal_shard",
//		net.minecraft.world.gen.feature.Feature.SIMPLE_RANDOM_SELECTOR,
//		new SimpleRandomFeatureConfig(
//			HolderSet.createDirect(
//				PlacedFeatureUtil.placedInline(
//					net.minecraft.world.gen.feature.Feature.RANDOM_PATCH,
//					ConfiguredFeatureUtil.createRandomPatchFeatureConfig(
//						net.minecraft.world.gen.feature.Feature.SIMPLE_BLOCK,
//						new SimpleBlockFeatureConfig( BlockStateProvider.of( BlockRegistry.get( "crystal_shard" ) ) )
//					)
//				),
//				PlacedFeatureUtil.placedInline(
//					net.minecraft.world.gen.feature.Feature.RANDOM_PATCH,
//					ConfiguredFeatureUtil.createRandomPatchFeatureConfig(
//						net.minecraft.world.gen.feature.Feature.SIMPLE_BLOCK,
//						new SimpleBlockFeatureConfig( BlockStateProvider.of( BlockRegistry.get( "dark_crystal_shard" ) ) )
//					)
//				)
//			)
//		)
//	);
//	public static final Holder<PlacedFeature> CRYSTAL_SHARD = PlacedFeatureUtil.register(
//		"diversity-flora:crystal_shard",
//		CRYSTAL_SHARDS_CONFIG,
//
//		RarityFilterPlacementModifier.create( 60 ),
//		InSquarePlacementModifier.getInstance(),
//		PlacedFeatureUtil.MOTION_BLOCKING_HEIGHTMAP,
//		CountPlacementModifier.create(
//			ClampedIntProvider.create(
//				UniformIntProvider.create( -3, 1 ),
//				0,
//				1
//			)
//		),
//		BiomePlacementModifier.getInstance()
//	);
//
//
//	public static Biome make() {
//		var generation = new GenerationSettings.Builder()
//			.feature( Feature.VEGETAL_DECORATION, CRYSTAL_SHARD );
//		var spawn = new SpawnSettings.Builder()
//			.spawn( SpawnGroup.MONSTER, new SpawnEntry( EntityType.SKELETON, 10, 1, 2 ) )
//			.spawn( SpawnGroup.MONSTER, new SpawnEntry( EntityType.CREEPER, 3, 1, 1 ) )
//			.spawn( SpawnGroup.MONSTER, new SpawnEntry( EntityType.SPIDER, 7, 1, 2 ) )
//			.spawn( SpawnGroup.CREATURE, new SpawnEntry( EntityType.RABBIT, 5, 2, 5 ) )
//			.spawn( SpawnGroup.MONSTER, new SpawnEntry( EntityType.SILVERFISH, 1, 1, 1 ) );
//		var effects = new BiomeEffects.Builder()
//			.fogColor( 0x000000 )
//			.waterColor( 0x000000 )
//			.waterFogColor( 0x000000 )
//			.skyColor( 0x0000FF );
//
//		return new Biome.Builder()
//			.precipitation( Biome.Precipitation.RAIN )
//			.temperature( 0.8F )
//			.temperatureModifier( Biome.TemperatureModifier.NONE )
//			.downfall( 0.2F )
//			.spawnSettings( spawn.build() )
//			.generationSettings( generation.build() )
//			.effects( effects.build() )
//			.build();
//	}
}
