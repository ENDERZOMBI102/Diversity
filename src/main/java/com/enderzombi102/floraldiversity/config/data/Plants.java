package com.enderzombi102.floraldiversity.config.data;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config( name = "plants" )
public class Plants implements ConfigData {

	@ConfigEntry.Gui.Tooltip
	public boolean enabled = true;
	@ConfigEntry.Gui.Tooltip
	@ConfigEntry.Category("cataplant")
	public Cataplant cataplant = new Cataplant();

	@Config( name = "cataplant" )
	public static class Cataplant implements ConfigData {
		public int power = 2;
	}

}
