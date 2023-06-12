package com.enderzombi102.diversity.flora.registry;

import com.enderzombi102.diversity.core.registry.Registries;
//import com.enderzombi102.diversity.flora.block.CataplantBlock;
import com.enderzombi102.diversity.flora.util.Const;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

import static com.enderzombi102.diversity.core.registry.Registries.BLOCKS;

public class BlockRegistry {
//	public static final Block CATAPLANT = BLOCKS.register( "cataplant", new CataplantBlock() );
	public static final Block CRYSTAL_GROUND = BLOCKS.register(
		"crystal_ground",
		new Block(
			QuiltBlockSettings.of( Material.SOIL )
				.requiresTool()
				.hardness( 0.4F )
				.resistance( 20F )
				.sounds( BlockSoundGroup.GLASS )
		)
	);

	public static Block get( String blockId ) {
		return Registries.BLOCKS.getOrDefault( new Identifier( Const.ID, blockId ), Blocks.AIR );
	}

	public static void init() { }
}
