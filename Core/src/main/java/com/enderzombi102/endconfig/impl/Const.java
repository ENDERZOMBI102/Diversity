package com.enderzombi102.endconfig.impl;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonGrammar;
import net.minecraft.util.Identifier;

public class Const {
	static final Identifier CONFIG_SYNC_ID = new Identifier( "endconfig", "config_sync" );
	static final Jankson JANKSON = Jankson.builder().build();
	static final JsonGrammar MINIFIED = JsonGrammar.builder()
		.withComments( false )
		.printWhitespace( false )
		.printUnquotedKeys( true )
		.printCommas( true )
		.build();
}
