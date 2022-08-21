package com.enderzombi102.endconfig.impl;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.api.SyntaxError;
import com.enderzombi102.endconfig.api.ChangeListener;
import com.enderzombi102.endconfig.api.ConfigHolder;
import com.enderzombi102.endconfig.api.Data;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.enderzombi102.enderlib.ListUtil.append;
import static com.enderzombi102.enderlib.ListUtil.mutableListOf;

public class EndConfigImpl implements ModInitializer {
	public static final Identifier CONFIG_SYNC_ID = new Identifier( "endconfig", "config_sync" );
	static final Jankson JANKSON = Jankson.builder().build();
	static final BiMap<String, ConfigHolder<?>> CONFIGS = HashBiMap.create();
	static final Map<String, List<ChangeListener<?>>> LISTENERS = new HashMap<>();

	public static <T extends Data> void register( String modid, Path path, T config ) {
		CONFIGS.put( modid, new ConfigHolderImpl<>( modid, path, config ) );
		// if it's also a listener for itself, add it
		if ( config instanceof ChangeListener<?> listener )
			registerChangeListener( modid, listener );
	}

	@SuppressWarnings("unchecked")
	public static <T extends Data> ConfigHolderImpl<T> get( String modid ) {
		if (! CONFIGS.containsKey( modid ) )
			throw new IllegalStateException( "Config for mod " + modid + " was not registered!" );

		return (ConfigHolderImpl<T>) CONFIGS.get( modid );
	}

	public static void save( String modid ) {
		var config = get( modid );
		// TODO: Serialize
	}

	public static <T extends Data> void registerChangeListener( String modid, ChangeListener<T> listener ) {
		LISTENERS.compute(
			modid,
			( key, value ) -> value == null ?
				mutableListOf( listener ) :
				append( value, listener )
		);
	}

	public static void sendConfigs( ServerPlayerEntity player ) {
	}

	public static void reloadConfigs() {
	}

	@Override
	public void onInitialize( ModContainer mod ) {
		ClientPlayNetworking.registerGlobalReceiver( CONFIG_SYNC_ID, ( client, networkHandler, data, sender) -> {
			var modid = data.readString();
			var config = data.readString();

			try {
				get( modid ).update( JANKSON.load( config ) );
			} catch ( SyntaxError e ) {
				throw new RuntimeException( e );
			}
		});
	}
}
