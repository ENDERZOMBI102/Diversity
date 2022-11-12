package com.enderzombi102.diversity.flora.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.sound.BlockSoundGroup;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@SuppressWarnings("deprecation")
public abstract class PlantBlock extends net.minecraft.block.PlantBlock {

	public PlantBlock() {
		this( settings -> settings );
	}

	public PlantBlock( Function<QuiltBlockSettings, Settings> settingsChanger ) {
		super(
			settingsChanger.apply(
				QuiltBlockSettings.of( Material.PLANT )
					.sounds( BlockSoundGroup.GRASS )
					.breakInstantly()
					.collidable( false )
			)
		);
	}

	@Override
	public List<ItemStack> getDroppedStacks( BlockState state, LootContext.Builder builder ) {
		List<ItemStack> drops = new ArrayList<>();
		addDrops( drops, state );
		return drops;
	}

	/* API */

	/**
	 * Add drops to this block
	 */
	protected abstract void addDrops( List<ItemStack> drops, BlockState state );
}
