package com.enderzombi102.diversity.flora.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.random.RandomGenerator;

public class LeavesWithBerriesBlock extends LeavesBlock {
	public LeavesWithBerriesBlock() {
		super(
			Settings.of(Material.LEAVES)
				.strength(0.2F)
				.ticksRandomly()
				.sounds( BlockSoundGroup.GRASS )
				.nonOpaque()
				.allowsSpawning( ( blockState, blockView, blockPos, object ) -> object == EntityType.OCELOT || object == EntityType.PARROT )
				.suffocates( ( blockState, blockView, blockPos ) -> false )
				.blockVision( ( blockState, blockView, blockPos ) -> false )
		);
	}

	@Override
	protected void appendProperties( StateManager.Builder<Block, BlockState> builder ) {
		super.appendProperties( builder );
	}

	@Override
	public void randomTick( BlockState state, ServerWorld world, BlockPos pos, RandomGenerator random ) {
		super.randomTick( state, world, pos, random );
	}
}
