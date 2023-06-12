package com.enderzombi102.diversity.flora.registry

import com.enderzombi102.diversity.core.registry.Registries.ITEMS
import com.enderzombi102.diversity.core.registry.Registries.ItemUtil.group
import com.enderzombi102.diversity.flora.util.Const
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.Items
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings


object ItemRegistry {
	private val CATAPLANT = ITEMS.register( "cataplant", blockItem( "cataplant", settings().group( "floral" ) ) )
	private val CRYSTAL_GROUND = ITEMS.register( "crystal_ground", blockItem( "crystal_ground", settings().group( "floral" ) ) )

	@JvmStatic
	fun init() {}
	@JvmStatic
	operator fun get( itemId: String? ): Item {
		return ITEMS.getOrDefault( Const.getId( itemId ), Items.AIR )
	}

	private fun settings(): QuiltItemSettings {
		return QuiltItemSettings()
	}

	private fun blockItem( id: String, settings: Item.Settings ): Item {
		return BlockItem( BlockRegistry.get( id ), settings )
	}
}
