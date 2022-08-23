package com.enderzombi102.endconfig.impl;

import com.enderzombi102.endconfig.api.ConfigOptions.Name;
import com.enderzombi102.enderlib.reflection.Invokers;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Optional;
import java.util.function.Function;

import static com.enderzombi102.enderlib.SafeUtils.doSafely;

public final class Util {
	private Util() { }

	public static String toPascal( Enum<?> value ) {
		return value.name();
	}

	public static String toSnake( Enum<?> value ) {
		return value.name();
	}

	public static String named( Enum<?> value ) {
		return doSafely(
			() -> value.getDeclaringClass()
				.getField( value.name() )
				.getAnnotation( Name.class )
				.value()
		);
	}

	public static String asIs( Enum<?> anEnum ) {
		return anEnum.name();
	}

	public static <T, A extends Annotation> T annotationOr( AnnotatedElement obj, Class<A> annotation, Function<A, T> getter, T fallback ) {
		return Optional.ofNullable( obj.getAnnotation( annotation ) ).map( getter ).orElse( fallback );
	}

	/**
	 * Fill current object fields with new object values, ignoring new NULLs.
	 * Old values are overwritten.
	 *
	 * @param dest Destination object.
	 * @param src Object with new values.
	 * @author <a href="https://stackoverflow.com/users/1112963/zon">Zon on StackOverflow</a>
	 */
	public static void merge( T dest, T src ) {
		assert dest.getClass().isInstance( src );

		for ( var field : dest.getClass().getDeclaredFields() )
			for ( var newField : src.getClass().getDeclaredFields() )
				if ( field.getName().equals( newField.getName() ) )
					try {
						field.set( dest, newField.get(src) );

					} catch ( IllegalAccessException ignore ) { }
	}
}
