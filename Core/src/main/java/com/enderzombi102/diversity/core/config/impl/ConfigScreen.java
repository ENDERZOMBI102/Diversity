package com.enderzombi102.diversity.core.config.impl;

import com.enderzombi102.diversity.core.config.api.ConfigHolder;
import dev.lambdaurora.spruceui.screen.SpruceScreen;
import net.minecraft.text.Text;

public class ConfigScreen extends SpruceScreen {
	private final ConfigHolder<?> holder;

	protected ConfigScreen( ConfigHolder<?> holder ) {
		super( Text.translatable( "title." + holder.modid() + ".endconfig" ) );
		this.holder = holder;
	}
}
