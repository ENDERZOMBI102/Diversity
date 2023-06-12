@file:JvmName("JsonUtil")
package com.enderzombi102.diversity.core.util

import blue.endless.jankson.Jankson
import blue.endless.jankson.JsonElement
import blue.endless.jankson.JsonObject

@JvmField
var JANKSON = Jankson.builder().allowBareRootObject().build()!!

fun JsonElement.getString( key: String ): String? =
	( this as JsonObject ).getString( key )

fun JsonObject.getString( key: String ): String? =
	this.get( String::class.java, key )
