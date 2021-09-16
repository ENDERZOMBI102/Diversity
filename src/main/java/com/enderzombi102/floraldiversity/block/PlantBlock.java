package com.enderzombi102.floraldiversity.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.GrassBlock;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.Material;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.ArrayList;
import java.util.List;

public abstract class PlantBlock extends net.minecraft.block.PlantBlock {

	public PlantBlock() {
		super(
				FabricBlockSettings.of(Material.PLANT)
						.sounds(BlockSoundGroup.GRASS)
						.breakInstantly()
						.collidable(false)
		);
	}

	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
		List<ItemStack> drops = new ArrayList<>();
		addDrops(drops, state);
		return drops;
	}

	@Override
	public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
		return super.getOpacity(state, world, pos);
	}

	/* API */

	/**
	 * Add drops to this block
	 */
	protected abstract void addDrops(List<ItemStack> drops, BlockState state);

}
