package com.enderzombi102.diversity.flora.util;

import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.QuiltLoader;

public final class Const {
	private Const() {}

	public static final String ID = "diversity-flora";
	public static final String NAME;
	public static final String VERSION;

	public static Identifier getId( String path ) {
		return new Identifier(ID, path );
	}

	static {
		final var meta = QuiltLoader.getModContainer(ID).orElseThrow().metadata();
		NAME = meta.name();
		VERSION = meta.version().raw();
	}
}
