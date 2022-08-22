package com.enderzombi102.endconfig.impl;

import com.enderzombi102.endconfig.api.ConfigOptions.Name;
import com.enderzombi102.enderlib.reflection.Invokers;

import static com.enderzombi102.enderlib.SafeUtils.doSafely;

public final class Util {
	private Util() { }

	public static String toPascal( Enum<?> value ) {
		return value.name();
	}

	public static String toSnake( Enum<?> value ) {
		return value.name();
	}


	public static String custom( Enum<?> value ) {
		return doSafely( () -> Invokers.invokeStatic( value.getDeclaringClass(), "to", String.class, value ) );
	}

	public static String named( Enum<?> value ) {
		return doSafely(
			() -> value.getDeclaringClass()
				.getField( value.name() )
				.getAnnotation( Name.class )
				.value()
		);
	}
}
