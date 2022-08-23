package com.enderzombi102.endconfig.impl;

import com.enderzombi102.endconfig.api.ConfigOptions.Name;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.enderzombi102.enderlib.SafeUtils.doSafely;
import static com.enderzombi102.enderlib.Strings.snakeToPascal;

public final class Util {
	private Util() { }

	public static final List<Class<?>> BYTE = List.of( Byte.class, byte.class );
	public static final List<Class<?>> SHORT = List.of( Short.class, short.class );
	public static final List<Class<?>> INTEGER = List.of( Integer.class, int.class );
	public static final List<Class<?>> LONG = List.of( Long.class, long.class );
	public static final List<Class<?>> FLOAT = List.of( Float.class, float.class );
	public static final List<Class<?>> DOUBLE = List.of( Double.class, double.class );
	public static final List<Class<?>> BOOLEAN = List.of( Boolean.class, boolean.class );

	public static <T, A extends Annotation> T annotationOr( AnnotatedElement obj, Class<A> annotation, Function<A, T> getter, T fallback ) {
		return Optional.ofNullable( obj.getAnnotation( annotation ) ).map( getter ).orElse( fallback );
	}

	public static String asIs( Enum<?> anEnum ) {
		return anEnum.name();
	}

	public static String named( Enum<?> value ) {
		return doSafely(
			() -> value.getDeclaringClass()
				.getField( value.name() )
				.getAnnotation( Name.class )
				.value()
		);
	}

	public static String toPascal( Enum<?> value ) {
		return value.name();
	}

	public static String toSnake( Enum<?> value ) {
		return value.name();
	}

	public static Enum<?> asIs( String value, Class<Enum<?>> enumClass ) {
		return (Enum<?>) doSafely( () -> enumClass.getField( value ).get(null) );
	}

	public static Enum<?> named( String value, Class<Enum<?>> enumClass ) {
		for ( var field : enumClass.getFields() )
			if ( field.isEnumConstant() && annotationOr( field, Name.class, Name::value, "" ).equals( value ) )
				return (Enum<?>) doSafely( () -> field.get(null) );

		throw new IllegalStateException("How did we end here?");
	}

	public static Enum<?> fromSnake( String value, Class<Enum<?>> enumClass ) {
		return fromPascal( snakeToPascal( value ), enumClass );
	}

	public static Enum<?> fromPascal( String value, Class<Enum<?>> enumClass ) {
		for ( var field : enumClass.getFields() )
			if ( field.isEnumConstant() && field.getName().equalsIgnoreCase( value ) )
				return (Enum<?>) doSafely( () -> field.get(null) );

		throw new IllegalStateException("How did we end here?");
	}
}
