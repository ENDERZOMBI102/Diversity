@file:JvmName("JsonUtil")
package com.enderzombi102.diversity.core.util

import blue.endless.jankson.Jankson
import blue.endless.jankson.JsonElement
import blue.endless.jankson.JsonObject

@JvmField
var JANKSON = Jankson.builder().allowBareRootObject().build()!!

fun string( element: JsonElement, key: String )= ( element as JsonObject ).getString( key )

fun JsonObject.getString(key: String ) = this.get( String::class.java, key )
