package com.enderzombi102.diversity.flora;

import com.enderzombi102.diversity.flora.registry.*;
import com.hugman.dawn.api.object.ModData;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.item.group.api.QuiltItemGroup;

import static com.enderzombi102.diversity.flora.util.Const.getId;

public class DiversityFlora implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("Diversity | Flora");
	public static final ModData DATA = new ModData( "diversity_flora" );
	public static final ItemGroup FLORAL_TAB = QuiltItemGroup.createWithIcon(
		getId("floraltab"),
		() -> new ItemStack( ItemRegistry.get("cataplant") )
	);


	@Override
	public void onInitialize(ModContainer container) {
		BlockRegistry.register();
		BlockEntityRegistry.register();
		ItemRegistry.register();
		BiomeRegistry.register();
		EventListeners.register();
		DATA.registerCreators();
		LOGGER.info("Diversity module `Flora` loaded!");
	}
}
