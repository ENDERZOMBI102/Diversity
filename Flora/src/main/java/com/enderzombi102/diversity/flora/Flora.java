package com.enderzombi102.diversity.flora;

import com.enderzombi102.diversity.flora.config.Config;
import com.enderzombi102.diversity.flora.registry.*;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.item.group.api.QuiltItemGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.enderzombi102.diversity.flora.util.Const.getId;

public class Flora implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("Diversity | Flora");
	public static final ItemGroup FLORAL_TAB = QuiltItemGroup.createWithIcon(
		getId("floraltab"),
		() -> new ItemStack( ItemRegistry.get("cataplant") )
	);

	@Override
	public void onInitialize(ModContainer container) {
		Config.save();
		BlockRegistry.register();
		BlockEntityRegistry.register();
		ItemRegistry.register();
		BiomeRegistry.register();
		EventListeners.register();
		LOGGER.info("Diversity module `Flora` loaded!");
	}
}
