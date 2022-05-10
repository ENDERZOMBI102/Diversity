package com.enderzombi102.diversity.flora.config.data;

import com.enderzombi102.diversity.flora.util.Const;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;

@Config( name = Const.ID )
public class MainConfig extends PartitioningSerializer.GlobalData {
	@ConfigEntry.Gui.CollapsibleObject
	@ConfigEntry.Category("plants")
	public Plants plants = new Plants();
}
