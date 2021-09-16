package com.enderzombi102.floraldiversity.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

import static com.enderzombi102.floraldiversity.FloralDiversity.FLORAL_TAB;
import static com.enderzombi102.floraldiversity.FloralDiversity.getID;

public class ItemRegistry {

	private static final HashMap<String, Item> ITEMS = new HashMap<>() {{
		put( "crystal_ground",  new BlockItem( BlockRegistry.get("crystal_ground"), settings() ) );
		put( "cataplant",  new BlockItem( BlockRegistry.get("cataplant"), settings() ) );
	}};

	public static void register() {
		for ( Map.Entry<String, Item> item : ITEMS.entrySet() ) {
			Registry.register(
					Registry.ITEM,
					getID( item.getKey() ),
					item.getValue()
			);
		}
	}

	public static Item get(String itemId) {
		return ITEMS.getOrDefault(itemId, Items.AIR);
	}

	public static void registerClient() {

	}

	private static FabricItemSettings settings() {
		return new FabricItemSettings().group( FLORAL_TAB );
	}

}
