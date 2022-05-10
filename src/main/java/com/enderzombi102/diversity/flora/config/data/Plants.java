package com.enderzombi102.diversity.flora.config.data;

import com.enderzombi102.diversity.flora.config.SyncOptions;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import static com.enderzombi102.diversity.flora.config.SyncOptions.*;


@Config( name = "plants" )
public class Plants implements ConfigData {
	@ConfigEntry.Gui.Tooltip
	@ConfigEntry.Category("cataplant")
	public Cataplant cataplant = new Cataplant();

	@Config( name = "cataplant" )
	public static class Cataplant implements ConfigData {
		@SyncOptions( BOTH | SYNCED )
		@ConfigEntry.Gui.Tooltip
		public int power = 2;
	}
}
