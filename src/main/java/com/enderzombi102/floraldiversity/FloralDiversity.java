package com.enderzombi102.floraldiversity;

import com.enderzombi102.floraldiversity.registry.*;
import com.enderzombi102.floraldiversity.util.Const;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.enderzombi102.floraldiversity.util.Const.MOD_ID;

public class FloralDiversity implements ModInitializer {

	public static final Logger LOGGER = LogManager.getLogger("floraldiversity");

	public static final ItemGroup FLORAL_TAB = FabricItemGroupBuilder.build(
			getID("floraltab"),
			() -> new ItemStack( ItemRegistry.get("cataplant") )
	);


	@Override
	public void onInitialize() {
		BlockRegistry.register();
		BlockEntityRegistry.register();
		ItemRegistry.register();
		BiomeRegistry.register();
		EventListeners.register();
		LOGGER.info( Const.MOD_NAME + " loaded!" );
	}

	public static Identifier getID(String path) {
		return new Identifier(MOD_ID, path);
	}
}
