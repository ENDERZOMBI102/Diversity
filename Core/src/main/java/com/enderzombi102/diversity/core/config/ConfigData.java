package com.enderzombi102.diversity.core.config;

import com.enderzombi102.endconfig.api.ChangeListener;
import com.enderzombi102.endconfig.api.ConfigHolder;
import com.enderzombi102.endconfig.api.Data;

import java.util.Locale;

import static com.enderzombi102.endconfig.api.ConfigOptions.*;

public class ConfigData implements Data, ChangeListener<ConfigData> {
	@Options({ "shaka", "laka" })
	@Tooltip()
	public String test = "shaka";

	@RenamingPolicy("custom")
	public SomeEnum enumValue = SomeEnum.SOME;

	@RenamingPolicy("named")
	public SomeOtherEnum enumValue2 = SomeOtherEnum.ENUM;

	@RenamingPolicy("camel")
	public SomeEnum enumValue3 = SomeEnum.ENUM;


	@Sync()
	@Range( min=0, max=100 )
	@Tooltip(2)
	public int value = 0;

	@Ignore
	public int shouldNotExist = 0;

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

	public enum SomeOtherEnum {
		@Name("emos") SOME,
		@Name("rehto") OTHER,
		@Name("mune") ENUM
	}
}
