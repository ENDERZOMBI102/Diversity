package com.enderzombi102.diversity.flora.compat;

import com.enderzombi102.diversity.flora.util.Const;
import com.enderzombi102.endconfig.api.EndConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class ModMenuCompat implements ModMenuApi {
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return EndConfig.getHolder( Const.ID )::screen;
	}
}
