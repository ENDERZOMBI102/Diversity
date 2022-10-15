package com.enderzombi102.diversity.flora.registry;

import com.enderzombi102.diversity.flora.util.Const;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Settings;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import static com.enderzombi102.diversity.core.registry.Registries.ITEMS;
import static com.enderzombi102.diversity.flora.Flora.FLORAL_TAB;

public final class ItemRegistry {
	private static final Item CATAPLANT = ITEMS.register( "cataplant", blockItem( "cataplant", settings() ) );
	private static final Item CRYSTAL_GROUND = ITEMS.register( "crystal_ground", blockItem( "crystal_ground", settings() ) );

	public static void init() { }

	public static Item get( String itemId ) {
		return ITEMS.getOrDefault( new Identifier( Const.ID, itemId ), Items.AIR );
	}

	private static QuiltItemSettings settings() {
		return new QuiltItemSettings().group( FLORAL_TAB );
	}

	private static @NotNull Item blockItem( @NotNull String id, @NotNull Settings settings ) {
		return new BlockItem( BlockRegistry.get( id ), settings );
	}
}
