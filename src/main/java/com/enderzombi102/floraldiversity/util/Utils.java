package com.enderzombi102.floraldiversity.util;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public final class Utils {
	private Utils() {}

	public static Identifier getIdentifier(Item item) {
		return Registry.ITEM.getId(item);
	}
}
