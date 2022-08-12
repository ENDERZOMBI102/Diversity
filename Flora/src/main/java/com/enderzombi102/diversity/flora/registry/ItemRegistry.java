package com.enderzombi102.diversity.flora.registry;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.HashMap;
import java.util.Map;

import static com.enderzombi102.diversity.flora.Flora.FLORAL_TAB;
import static com.enderzombi102.diversity.flora.util.Const.getId;

public final class ItemRegistry {

	private static final HashMap<String, Item> ITEMS = new HashMap<>() {{
		put( "crystal_ground",  new BlockItem( BlockRegistry.get("crystal_ground"), settings() ) );
		put( "cataplant",  new BlockItem( BlockRegistry.get("cataplant"), settings() ) );
	}};

	public static void register() {
		for ( Map.Entry<String, Item> item : ITEMS.entrySet() ) {
			Registry.register(
				Registry.ITEM,
				getId( item.getKey() ),
				item.getValue()
			);
		}
	}

	public static Item get(String itemId) {
		return ITEMS.getOrDefault(itemId, Items.AIR);
	}

	private static QuiltItemSettings settings() {
		return new QuiltItemSettings().group( FLORAL_TAB );
	}

}
