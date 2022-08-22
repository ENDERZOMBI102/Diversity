package com.enderzombi102.diversity.core.config;

import com.enderzombi102.endconfig.api.ChangeListener;
import com.enderzombi102.endconfig.api.ConfigHolder;
import com.enderzombi102.endconfig.api.Data;

import java.util.Locale;

import static com.enderzombi102.endconfig.api.ConfigOptions.*;

@Sync
public class ConfigData implements Data, ChangeListener<ConfigData> {
	@Tooltip
	@Options({ "shaka", "laka" })
	public String test = "shaka";

	@RenamingPolicy("custom")
	public SomeEnum enumValue = SomeEnum.SOME;

	@RenamingPolicy("named")
	public SomeOtherEnum enumValue2 = SomeOtherEnum.ENUM;

	@RenamingPolicy("camel")
	public SomeEnum enumValue3 = SomeEnum.ENUM;

	@Tooltip(2)
	@Range( min=0, max=100 )
	public int value = 0;

	@Sync(false)
	public int notSynced = 199;

	@Ignore
	public int shouldNotExist = 0;

	@Override
	public void onChange( ConfigHolder<ConfigData> config ) {
		System.out.println("[ConfigData::onChange()] Received change event!");
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
