package com.enderzombi102.floraldiversity.registry;

import com.enderzombi102.floraldiversity.block.Cataplant;
import com.enderzombi102.floraldiversity.block.CrystalGroundBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

import static com.enderzombi102.floraldiversity.FloralDiversity.getID;

public class BlockRegistry {

	private static final HashMap<String, Block> BLOCKS = new HashMap<>() {{
		put( "crystal_ground", new CrystalGroundBlock() );
		put( "cataplant", new Cataplant() );
	}};

	public static void register() {
		for ( Map.Entry<String, Block> entry : BLOCKS.entrySet() ) {
			Registry.register(
					Registry.BLOCK,
					getID( entry.getKey() ),
					entry.getValue()
			);
		}
	}

	public static Block get(String blockId) {
		return BLOCKS.getOrDefault(blockId, Blocks.AIR);
	}
}
