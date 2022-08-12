package com.enderzombi102.diversity.flora.biome;

import com.enderzombi102.diversity.flora.registry.BlockRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Holder;
import net.minecraft.util.HolderSet;
import net.minecraft.util.math.intprovider.ClampedIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.biome.Biome;
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

	public static final Holder<ConfiguredFeature<SimpleRandomFeatureConfig, ?>> CRYSTAL_SHARDS_CONFIG = ConfiguredFeatureUtil.register(
		"crystal_shard",
		net.minecraft.world.gen.feature.Feature.SIMPLE_RANDOM_SELECTOR,
		new SimpleRandomFeatureConfig(
			HolderSet.createDirect(
				PlacedFeatureUtil.placedInline(
					net.minecraft.world.gen.feature.Feature.RANDOM_PATCH,
					ConfiguredFeatureUtil.createRandomPatchFeatureConfig(
						net.minecraft.world.gen.feature.Feature.SIMPLE_BLOCK,
						new SimpleBlockFeatureConfig( BlockStateProvider.of( BlockRegistry.get( "crystal_shard" ) ) )
					)
				),
				PlacedFeatureUtil.placedInline(
					net.minecraft.world.gen.feature.Feature.RANDOM_PATCH,
					ConfiguredFeatureUtil.createRandomPatchFeatureConfig(
						net.minecraft.world.gen.feature.Feature.SIMPLE_BLOCK,
						new SimpleBlockFeatureConfig( BlockStateProvider.of( BlockRegistry.get( "dark_crystal_shard" ) ) )
					)
				)
			)
		)
	);
	public static final Holder<PlacedFeature> CRYSTAL_SHARD = PlacedFeatureUtil.register(
		"crystal_shard",
		CRYSTAL_SHARDS_CONFIG,

		RarityFilterPlacementModifier.create( 60 ),
		InSquarePlacementModifier.getInstance(),
		PlacedFeatureUtil.MOTION_BLOCKING_HEIGHTMAP,
		CountPlacementModifier.create(
			ClampedIntProvider.create(
				UniformIntProvider.create( -3, 1 ),
				0,
				1
			)
		),
		BiomePlacementModifier.getInstance()
	);


	public static Biome make() {
		var generation = new GenerationSettings.Builder();
		generation.feature( Feature.VEGETAL_DECORATION, CRYSTAL_SHARD );
		var spawn = new SpawnSettings.Builder();
		spawn.spawn( SpawnGroup.MONSTER, new SpawnEntry( EntityType.SKELETON, 10, 1, 2 ) );
		spawn.spawn( SpawnGroup.MONSTER, new SpawnEntry( EntityType.CREEPER, 3, 1, 1 ) );
		spawn.spawn( SpawnGroup.MONSTER, new SpawnEntry( EntityType.SPIDER, 7, 1, 2 ) );
		spawn.spawn( SpawnGroup.CREATURE, new SpawnEntry( EntityType.RABBIT, 5, 2, 5 ) );
		spawn.spawn( SpawnGroup.MONSTER, new SpawnEntry( EntityType.SILVERFISH, 1, 1, 1 ) );
		return new Biome.Builder()
			.precipitation( Biome.Precipitation.RAIN )
			.temperatureModifier( Biome.TemperatureModifier.NONE )
			.temperature( 0.8F )
			.downfall( 0.2F )
			.spawnSettings( spawn.build() )
			.generationSettings( generation.build() ).build();
	}
}
