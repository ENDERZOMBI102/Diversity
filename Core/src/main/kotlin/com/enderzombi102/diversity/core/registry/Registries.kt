package com.enderzombi102.diversity.core.registry

import com.enderzombi102.diversity.core.Core
import com.enderzombi102.diversity.core.Core.Companion.LOGGER
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object Registries {
	@JvmField
	val BLOCKS = registry<Block>( Registry.BLOCK )
	@JvmField
	val ITEMS = registry<Item>( Registry.ITEM )

	fun register() {
		BLOCKS.registerInternal()
		ITEMS.registerInternal()
	}
}

private inline fun <reified T> registry( reg: Registry<T> ) =
	DRegistry( reg, T::class.java.simpleName.lowercase() )

class DRegistry<T>( private val reg: Registry<T>, private val type: String ) {
	private val map: MutableMap<Identifier, T> = HashMap()

	fun register( name: String, obj: T ) = obj.apply { map[Identifier( Core.currentId, name )] = obj }

	fun registerInternal() {
		LOGGER.info( "Registering ${type}s" )
		for ( (id, obj) in map )
			Registry.register( reg, id, obj )
	}

	fun getOrDefault( id: Identifier, default: T ): T = map.getOrDefault( id, default )
}
