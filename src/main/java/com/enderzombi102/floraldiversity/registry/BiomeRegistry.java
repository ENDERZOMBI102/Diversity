package com.enderzombi102.floraldiversity.registry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.BlockStateProviderType;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.util.Map;
import java.util.Random;

import static com.enderzombi102.floraldiversity.FloralDiversity.getID;

public class BiomeRegistry {

	/*private static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> CRYSTAL_FOREST_SURFACE_BUILDER = SurfaceBuilder.DEFAULT
			.withConfig(
					new TernarySurfaceConfig(
							BlockRegistry.get("crystal_ground").getDefaultState(),
							BlockRegistry.get("crystal_ground").getDefaultState(),
							BlockRegistry.get("crystal_ground").getDefaultState()
					)
			);*/

	public static void register() {

	}

	public static void registerClient() {

	}

	/*private static Biome crystalForest() {
		SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
		spawnSettings.spawn( SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry( EntityType.SKELETON, 10, 1, 2 ) );
		spawnSettings.spawn( SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry( EntityType.CREEPER, 3, 1, 1 ) );
		spawnSettings.spawn( SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry( EntityType.SPIDER, 7, 1, 2 ) );
		spawnSettings.spawn( SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry( EntityType.RABBIT, 5, 2, 5 ) );
		spawnSettings.spawn( SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry( EntityType.SILVERFISH, 1, 1, 1 ) );

		GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
		generationSettings.surfaceBuilder(CRYSTAL_FOREST_SURFACE_BUILDER);
		// TODO: ADD TREES


		// TODO: ADD FLOWERS
		new DefaultFlowerFeature( RandomPatchFeatureConfig.CODEC )
				.configure( new RandomPatchFeatureConfig.Builder(new BlockStateProvider() {
					@Override
					protected BlockStateProviderType<?> getType() {
						return new BlockStateProviderType<>();
					}

					@Override
					public BlockState getBlockState(Random random, BlockPos pos) {
						return null;
					}
				}() -> BlockRegistry.get("crystal_ground").getDefaultState(), null ).build() )

		DefaultBiomeFeatures.addDefaultUndergroundStructures(generationSettings);
		DefaultBiomeFeatures.addLandCarvers(generationSettings);
		DefaultBiomeFeatures.addDefaultLakes(generationSettings);
		DefaultBiomeFeatures.addDungeons(generationSettings);
		DefaultBiomeFeatures.addMineables(generationSettings);
		DefaultBiomeFeatures.addDefaultOres(generationSettings);
		DefaultBiomeFeatures.addDefaultDisks(generationSettings);
		DefaultBiomeFeatures.addSprings(generationSettings);
		DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);

		return new Biome.Builder()
				.precipitation(Biome.Precipitation.RAIN)
				.category(Biome.Category.FOREST)
				.depth(0.3F)
				.scale(0.1F)
				.temperature(0.8F)
				.downfall(0.2F)
				.effects(
						new BiomeEffects.Builder()
								.waterColor(0x3f76e4)
								.waterFogColor(0x050533)
								.fogColor(0xc0d8ff)
								.skyColor(0x77adff)
								.build()
				)
				.spawnSettings( spawnSettings.build() )
				.generationSettings( generationSettings.build() )
				.build();
	}*/
}
