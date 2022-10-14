package com.enderzombi102.diversity.flora.registry;

import blue.endless.jankson.JsonObject;
import blue.endless.jankson.api.SyntaxError;
import com.enderzombi102.diversity.flora.block.CataplantBlock;
import net.minecraft.block.AbstractBlock.OffsetType;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.block.*;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.registry.Registry;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.enderzombi102.diversity.core.module.DiversityModules.find;
import static com.enderzombi102.diversity.core.util.JsonUtil.string;
import static com.enderzombi102.diversity.core.util.Util.JANKSON;
import static com.enderzombi102.diversity.flora.util.Const.getId;

public class BlockRegistry {
	public static void register() {
		for ( Map.Entry<String, Block> entry : BLOCKS.entrySet() ) {
			Registry.register(
				Registry.BLOCK,
				getId( entry.getKey() ),
				entry.getValue()
			);
		}
	}

	private static final Map<String, Block> BLOCKS = new HashMap<>() {{
		put(
			"crystal_ground",
			new Block(
				QuiltBlockSettings.of( Material.SOIL )
					.requiresTool()
					.hardness( 0.4F )
					.resistance( 20F )
					.sounds( BlockSoundGroup.GLASS )
			)
		);
		put( "cataplant", new CataplantBlock() );

		// load common plants
		try {
			for ( var plant : JANKSON.load( find( "diversity/flora/common_plants.json5" ).toFile() ).entrySet() ) {
				var name = plant.getKey();
				var settings = Settings.of( Material.PLANT, DyeColor.valueOf( string( plant.getValue(), "color" ) ) )
					.noCollision()
					.breakInstantly()
					.sounds( BlockSoundGroup.GRASS )
					.offsetType( OffsetType.XZ );
				put(
					name,
					( (JsonObject) plant.getValue() ).getBoolean( "flower", false ) ?
						new FlowerBlock( StatusEffects.LUCK, 0, settings ) :
						new PlantBlock( settings )
				);
			}
		} catch ( IOException | SyntaxError e ) {
			throw new RuntimeException( e );
		}
	}};

	public static Block get( String blockId ) {
		return BLOCKS.getOrDefault( blockId, Blocks.AIR );
	}
}
