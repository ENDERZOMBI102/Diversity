package com.enderzombi102.diversity.flora.config;

import com.enderzombi102.diversity.flora.config.data.ConfigData;

public class Config {
	private static ConfigData cached = null;

	public static ConfigData get() {
//		if ( cached == null )
//			cached = EndConfig.get( Const.ID );
		return cached;
	}

	public static void save() {
//		EndConfig.save( Const.ID );
	}

	static {
//		EndConfig.register( Const.ID, ConfigData.class );
	}
}
