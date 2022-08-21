package com.enderzombi102.endconfig.impl;

import blue.endless.jankson.api.SyntaxError;
import com.enderzombi102.endconfig.api.ChangeListener;
import com.enderzombi102.endconfig.api.ConfigHolder;
import com.enderzombi102.endconfig.api.Data;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.server.network.ServerPlayerEntity;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.enderzombi102.endconfig.impl.Const.CONFIG_SYNC_ID;
import static com.enderzombi102.endconfig.impl.Const.JANKSON;
import static com.enderzombi102.enderlib.ListUtil.append;
import static com.enderzombi102.enderlib.ListUtil.mutableListOf;

public class EndConfigImpl implements ModInitializer {
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
		for ( var holder : CONFIGS.values() ) {
			ServerPlayNetworking.send(
				player,
				CONFIG_SYNC_ID,
				( (ConfigHolderImpl<?>) holder ).packet()
			);
		}
	}

	public static void reloadConfigs() {
		// reload all configs from disk, only called on client
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
