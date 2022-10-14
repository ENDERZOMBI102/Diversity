package com.enderzombi102.diversity.core.util

import blue.endless.jankson.JsonElement
import blue.endless.jankson.JsonObject
import blue.endless.jankson.annotation.Nullable

object JsonUtil {
	@JvmStatic
	fun string( element: JsonElement, key: String )= (element as JsonObject).get(String::class.java, key)
}