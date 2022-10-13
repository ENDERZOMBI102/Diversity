package com.enderzombi102.diversity.flora.registry;

import com.enderzombi102.diversity.flora.block.CataplantBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

import java.util.HashMap;
import java.util.Map;

import static com.enderzombi102.diversity.flora.util.Const.getId;

public class BlockRegistry {

	private static final HashMap<String, Block> BLOCKS = new HashMap<>() {{
		put(
			"crystal_ground",
			new Block(
				QuiltBlockSettings.of( Material.SOIL )
					.requiresTool()
					.hardness(0.4F)
					.resistance(20F)
					.sounds( BlockSoundGroup.GLASS )
			)
		);
		put( "cataplant", new CataplantBlock() );
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
