package com.enderzombi102.endconfig.impl;

import blue.endless.jankson.api.SyntaxError;
import com.enderzombi102.endconfig.api.ChangeListener;
import com.enderzombi102.endconfig.api.ConfigHolder;
import com.enderzombi102.endconfig.api.Data;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.network.ServerPlayerEntity;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.enderzombi102.endconfig.impl.Const.*;
import static com.enderzombi102.enderlib.ListUtil.append;
import static com.enderzombi102.enderlib.ListUtil.mutableListOf;

public class EndConfigImpl implements ClientModInitializer {
	static final BiMap<String, ConfigHolder<?>> CONFIGS = HashBiMap.create();
	static final Map<String, List<ChangeListener<?>>> LISTENERS = new HashMap<>();

	public static <T extends Data> void register( String modid, Path path, Class<T> dataClass ) {
		LOGGER.info( "Registered new config for `{}` at `{}`", modid, path.relativize( QuiltLoader.getGameDir() ) );
		var holder = new ConfigHolderImpl<>( modid, path, dataClass );
		CONFIGS.put( modid, holder );
		// if it's also a listener for itself, add it
		if ( ChangeListener.class.isAssignableFrom( dataClass ) )
			registerChangeListener( modid, (ChangeListener<?>) holder.get() );
	}

	@SuppressWarnings("unchecked")
	public static <T extends Data> ConfigHolderImpl<T> get( String modid ) {
		if (! CONFIGS.containsKey( modid ) )
			throw new IllegalStateException( "Config for mod " + modid + " was not registered!" );

		return (ConfigHolderImpl<T>) CONFIGS.get( modid );
	}

	public static void save( String modid ) {
		var holder = get( modid );
		var path = holder.path();

		// TODO: Serialize
	}

	public static <T extends Data> void registerChangeListener( String modid, ChangeListener<T> listener ) {
		LOGGER.info( "Registered new change listener for {}'s config", modid );
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
		LOGGER.info( "Sent {} configs to {}", CONFIGS.size(), player.getName() );
	}

	public static void reloadConfigs() {
		// reload all configs from disk, only called on client
	}

	@Override
	@Environment( EnvType.CLIENT )
	public void onInitializeClient( ModContainer mod ) {
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
