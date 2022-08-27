package com.enderzombi102.endconfig.impl;

import blue.endless.jankson.JsonElement;
import blue.endless.jankson.JsonNull;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.JsonPrimitive;
import com.enderzombi102.endconfig.api.ConfigOptions.*;
import net.minecraft.util.Language;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.BiFunction;
import java.util.function.Function;

import static com.enderzombi102.endconfig.impl.Const.LOGGER;
import static com.enderzombi102.enderlib.SafeUtils.doSafely;
import static com.enderzombi102.enderlib.Strings.snakeToPascal;
import static com.enderzombi102.enderlib.reflection.Annotations.annotation;
import static com.enderzombi102.enderlib.reflection.Types.*;

@ApiStatus.Internal
public final class Util {
	private Util() { }

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
			if ( field.isEnumConstant() && annotation( field, Name.class, Name::value, "" ).equals( value ) )
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

	public static JsonElement serialize( Object data, boolean compact, String path, String modid ) throws IllegalAccessException {
		var object = new JsonObject();
		var globalSyncSetting = annotation( data.getClass(), Sync.class, Sync::value,false );

		for ( var field : data.getClass().getFields() ) {
			// ignore fields marked with @Ignore
			if ( field.isAnnotationPresent( Ignore.class ) )
				continue;
			// if we're not serializing everything
			if ( compact )
				// ignore unsyncable elements
				if ( !annotation( field, Sync.class, Sync::value, globalSyncSetting ) )
					continue;
			var value = field.get( data );

			// region object to json element
			JsonElement element;
			if ( value instanceof String string ) {
				element = JsonPrimitive.of( string );
			} else if ( value instanceof Integer integer ) {
				element = JsonPrimitive.of( integer.longValue() );
			} else if ( value instanceof Long aLong ) {
				element = JsonPrimitive.of( aLong );
			} else if ( value instanceof Double aDouble ) {
				element = JsonPrimitive.of( aDouble );
			} else if ( value instanceof Float aFloat ) {
				element = JsonPrimitive.of( aFloat.doubleValue() );
			} else if ( value instanceof Boolean aBoolean ) {
				element = JsonPrimitive.of( aBoolean );
			} else if ( value instanceof Short aShort ) {
				element = JsonPrimitive.of( aShort.longValue() );
			} else if ( value instanceof Byte aByte ) {
				element = JsonPrimitive.of( aByte.longValue() );
			} else if ( value instanceof Enum<?> anEnum ) {
				// it's an enum
				Function<Enum<?>, String> transform = switch ( annotation(
					field,
					RenamingPolicy.class,
					RenamingPolicy::value,
					"asis"
				) ) {
					case "pascal" -> Util::toPascal; // PascalCase
					case "snake" -> Util::toSnake;   // snake_case
					case "named" -> Util::named;	 // @Name() value
					case "asis" -> Util::asIs;	 	 // Enum::Name() value
					default -> throw new IllegalStateException(
						"Mod %s has set `RenamingPolicy` on field `%s` to an invalid value `%s`!".formatted(
							modid,
							field.getDeclaringClass().getName() + "::" + field.getName(),
							field.getAnnotation( RenamingPolicy.class ).value()
						)
					);
				};

				element = JsonPrimitive.of( transform.apply( anEnum ) );
			} else if ( value == null ) {
				element = JsonNull.INSTANCE;
			} else {
				// it's another object
				element = serialize( value, compact, "%s.%s".formatted( path, field.getName() ), modid );
			}
			// endregion

			object.put( field.getName(), element );
			if ( (! compact) ) {
				// translated comments
				final var key = "endconfig.%s.%s.comment".formatted( modid, path + field.getName() );

				// FIXME: This doesn't work, it is called _before_ translations are loaded from mods
				if ( Language.getInstance().hasTranslation( key ) )
					object.setComment( field.getName(), Language.getInstance().get( key ) );

				// code-driven comments
				final var comment = annotation( field, Comment.class, Comment::value );
				if ( comment != null )
					object.setComment( field.getName(), comment );
			}
		}

		return object;
	}

	@SuppressWarnings("unchecked")
	public static void deserialize( Object dest, JsonObject obj, String modid ) {
		for ( var field : dest.getClass().getDeclaredFields() )
			if ( obj.containsKey( field.getName() ) ) {
				try {
					if ( field.getType().equals( String.class ) ) {
						field.set( dest, obj.get( String.class, field.getName() ) );
					} else if ( INTEGER.contains( field.getType() ) ) {
						field.set( dest, obj.getInt( field.getName(), field.getInt( dest ) ) );
					} else if ( LONG.contains( field.getType() ) ) {
						field.set( dest, obj.getLong( field.getName(), field.getLong( dest ) ) );
					} else if ( DOUBLE.contains( field.getType() ) ) {
						field.set( dest, obj.getDouble( field.getName(), field.getDouble( dest ) ) );
					} else if ( FLOAT.contains( field.getType() ) ) {
						field.set( dest, obj.getFloat( field.getName(), field.getFloat( dest ) ) );
					} else if ( BOOLEAN.contains( field.getType() ) ) {
						field.set( dest, obj.getBoolean( field.getName(), field.getBoolean( dest ) ) );
					} else if ( SHORT.contains( field.getType() ) ) {
						field.set( dest, obj.getShort( field.getName(), field.getShort( dest ) ) );
					} else if ( BYTE.contains( field.getType() ) ) {
						field.set( dest, obj.getByte( field.getName(), field.getByte( dest ) ) );
					} else if ( field.getType().isEnum() ) {
						// it's an enum
						BiFunction< String, Class< Enum<?> >, Enum<?> > transform = switch ( annotation(
							field,
							RenamingPolicy.class,
							RenamingPolicy::value,
							"asis"
						) ) {
							case "pascal" -> Util::fromPascal; // PascalCase
							case "snake" -> Util::fromSnake;   // snake_case
							case "named" -> Util::named;	 // @Name() value
							case "asis" -> Util::asIs;	 	 // Enum::Name() value
							default -> throw new IllegalStateException(
								"Mod %s has set `RenamingPolicy` on field `%s` to an invalid value `%s`!".formatted(
									modid,
									field.getDeclaringClass().getName() + "::" + field.getName(),
									field.getAnnotation( RenamingPolicy.class ).value()
								)
							);
						};
						field.set( dest, transform.apply( obj.get( String.class, field.getName() ), (Class<Enum<?>>) field.getType() ) );
					} else {
						// it's another object
						deserialize( field.get( dest ), obj.getObject( field.getName() ), modid );
					}
				} catch ( IllegalAccessException e ) {
					LOGGER.error( "Failed to deserialize {}'s config sent from the server...", modid, e );
				}
			}
	}
}
