package com.enderzombi102.floraldiversity.block;

import com.enderzombi102.floraldiversity.registry.ItemRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.sound.BlockSoundGroup;

import java.util.List;

public class CrystalGroundBlock extends Block {

	public CrystalGroundBlock() {
		super(
				FabricBlockSettings.of( Material.SOIL )
						.requiresTool()
						.breakByTool( FabricToolTags.PICKAXES )
						.hardness(0.4F)
						.resistance(20F)
						.sounds( BlockSoundGroup.GLASS )
		);
	}

	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
		return List.of( new ItemStack( ItemRegistry.get("crystal_ground") ) );
	}
}
