package com.enderzombi102.floraldiversity.registry;

import com.enderzombi102.floraldiversity.block.Cataplant;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

import static com.enderzombi102.floraldiversity.FloralDiversity.getId;

public class BlockRegistry {

	private static final HashMap<String, Block> BLOCKS = new HashMap<>() {{
		put( "crystal_ground", new Block( FabricBlockSettings.of( Material.SOIL ).requiresTool().hardness(0.4F).resistance(20F).sounds( BlockSoundGroup.GLASS ) ) );
		put( "cataplant", new Cataplant() );
	}};

	public static void register() {
		for ( Map.Entry<String, Block> entry : BLOCKS.entrySet() ) {
			Registry.register(
					Registry.BLOCK,
					getId( entry.getKey() ),
					entry.getValue()
			);
		}
	}

	public static Block get(String blockId) {
		return BLOCKS.getOrDefault(blockId, Blocks.AIR);
	}
}
