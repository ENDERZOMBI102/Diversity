package com.enderzombi102.floraldiversity.config.data;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config( name = "rotting_mechanic" )
public class RottingMechanic implements ConfigData {

	@ConfigEntry.Gui.Tooltip
	public boolean enabled = true;
	@ConfigEntry.Gui.Tooltip
	public int defaultRotDays = 3;

}
