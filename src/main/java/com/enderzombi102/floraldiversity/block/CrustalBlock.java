package com.enderzombi102.floraldiversity.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

public class CrustalBlock extends Block {
	public CrustalBlock() {
		super(
				FabricBlockSettings.of(Material.GLASS)
						.sounds(BlockSoundGroup.GLASS)
						.hardness(1.4F)
						.resistance(20)
						.requiresTool()
						.breakByTool( FabricToolTags.PICKAXES )
		);
	}
}
