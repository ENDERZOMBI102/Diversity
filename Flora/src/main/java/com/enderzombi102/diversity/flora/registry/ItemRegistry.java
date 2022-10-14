package com.enderzombi102.diversity.flora.registry;

import com.enderzombi102.diversity.flora.util.Const;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.HashMap;
import java.util.Map;

import static com.enderzombi102.diversity.core.registry.Registries.ITEMS;
import static com.enderzombi102.diversity.flora.Flora.FLORAL_TAB;
import static com.enderzombi102.diversity.flora.util.Const.getId;

public final class ItemRegistry {
	private static final Item CRYSTAL_GROUND = ITEMS.register( "crystal_ground", new BlockItem( BlockRegistry.get( "crystal_ground" ), settings() ) );
	private static final Item CATAPLANT = ITEMS.register( "cataplant", new BlockItem( BlockRegistry.get( "cataplant" ), settings() ) );

	public static void init() { }

	public static Item get( String itemId ) {
		return ITEMS.getOrDefault( new Identifier( Const.ID, itemId ), Items.AIR );
	}

	private static QuiltItemSettings settings() {
		return new QuiltItemSettings().group( FLORAL_TAB );
	}
}
