package com.enderzombi102.endconfig.impl;

import com.enderzombi102.endconfig.api.ConfigHolder;
import dev.lambdaurora.spruceui.screen.SpruceScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public class ConfigScreen extends SpruceScreen {
	private final ConfigHolder<?> holder;
	private final @Nullable Screen parent;

	ConfigScreen( ConfigHolder<?> holder, @Nullable Screen parent ) {
		super( Text.translatable( "endconfig." + holder.modid() + ".title" ) );
		this.holder = holder;
		this.parent = parent;
	}

	@Override
	protected void init() {
		super.init();

		for ( var field : this.holder.get().getClass().getFields() ) {

		}
	}

	@Override
	public void renderTitle( MatrixStack matrices, int mouseX, int mouseY, float delta ) {
		drawCenteredText(
			matrices,
			this.textRenderer,
			this.title,
			this.width / 2,
			8,
			0xFF_FF_FF
		);
	}
}
