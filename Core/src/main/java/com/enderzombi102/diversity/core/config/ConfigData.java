package com.enderzombi102.diversity.core.config;

import com.enderzombi102.diversity.core.config.api.ChangeListener;
import com.enderzombi102.diversity.core.config.api.ConfigHolder;
import com.enderzombi102.diversity.core.config.api.Data;

import java.util.Locale;

import static com.enderzombi102.diversity.core.config.api.ConfigOptions.*;

public class ConfigData implements Data, ChangeListener<ConfigData> {
	@Options({ "shaka", "laka" })
	@Description( "String with limited possibilities" )
	public String test = "shaka";

	@RenamingPolicy("custom")
	public SomeEnum enumValue = SomeEnum.SOME;

	@RenamingPolicy("camel")
	public SomeEnum enumValue2 = SomeEnum.ENUM;

	@Sync( SIDE_SERVER | SIDE_CLIENT )
	@Range( min=0, max=100 )
	@Description( "A cool integer" )
	public int value = 0;

	@Override
	public void onChange( ConfigHolder<ConfigData> config ) {

	}


	public enum SomeEnum {
		SOME,
		ENUM;

		// to config
		public static String to( String value ) {
			return value.toLowerCase( Locale.ROOT );
		}

		// from config
		public static String from( String value ) {
			return value.toUpperCase(Locale.ROOT);
		}
	}
}
