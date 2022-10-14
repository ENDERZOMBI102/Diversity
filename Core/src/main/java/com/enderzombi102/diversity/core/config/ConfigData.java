package com.enderzombi102.diversity.core.config;

import com.enderzombi102.endconfig.api.ChangeListener;
import com.enderzombi102.endconfig.api.ConfigHolder;
import com.enderzombi102.endconfig.api.Data;

import java.util.List;
import java.util.Locale;

import static com.enderzombi102.endconfig.api.ConfigOptions.*;
import static com.enderzombi102.enderlib.collections.ListUtil.mutableListOf;

@SuppressWarnings("UnstableApiUsage")
@Sync
public class ConfigData implements Data, ChangeListener<ConfigData> {
	@Tooltip
	@Options({ "shaka", "laka" })
	public String test = "shaka";

	public SomeEnum enumValue = SomeEnum.SOME;

	@RenamingPolicy("named")
	public SomeOtherEnum enumValue2 = SomeOtherEnum.ENUM;

	@RenamingPolicy("pascal")
	public SomeEnum enumValue3 = SomeEnum.ENUM;

	@Tooltip(2)
	@Range( min=0, max=100 )
	public int value = 0;

	@Sync(false)
	public int notSynced = 199;

	@Tooltip
	@Comment( "Comment made from code!" )
	public List<String> data = mutableListOf( "Hello", "world", "!" );

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
		public static String to( SomeEnum value ) {
			return value.name().toLowerCase( Locale.ROOT );
		}

		// from config
		public static SomeEnum from( String value ) {
			return SomeEnum.SOME;
		}
	}

	public enum SomeOtherEnum {
		@Name("emos") SOME,
		@Name("rehto") OTHER,
		@Name("mune") ENUM
	}
}
