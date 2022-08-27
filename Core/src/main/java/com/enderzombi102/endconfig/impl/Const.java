package com.enderzombi102.endconfig.impl;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonGrammar;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApiStatus.Experimental
public class Const {
	static final Identifier CONFIG_SYNC_ID = new Identifier( "endconfig", "config_sync" );
	static final Jankson JANKSON = Jankson.builder().build();
	static final Logger LOGGER = LoggerFactory.getLogger( "EndConfig" );
	static final JsonGrammar MINIFIED = JsonGrammar.builder()
		.withComments( false )
		.printWhitespace( false )
		.printUnquotedKeys( true )
		.printCommas( true )
		.build();
}
