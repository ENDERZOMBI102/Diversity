package com.enderzombi102.endconfig;

import blue.endless.jankson.Comment;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Contains all the annotations for fields.
 */
public @interface ConfigOptions {

	/**
	 * Makes so this field is synced to the connected clients
	 */
	@Target( ElementType.FIELD )
	@Retention( RetentionPolicy.RUNTIME )
	@interface Sync { }

	/**
	 *
	 */
	@Target( ElementType.FIELD )
	@Retention( RetentionPolicy.RUNTIME )
	@interface Tooltip {
		int value() default 1;
	}

	/**
	 *
	 */
	@Target( ElementType.FIELD )
	@Retention( RetentionPolicy.RUNTIME )
	@interface Range {
		double min();
		double max();
	}

	/**
	 * Will make a dropdown
	 */
	@Target({ ElementType.FIELD, ElementType.METHOD })
	@Retention( RetentionPolicy.RUNTIME )
	@interface Options {
		String[] value();
	}

	/**
	 *
	 */
	@Target( ElementType.FIELD )
	@Retention( RetentionPolicy.RUNTIME )
	@interface RenamingPolicy {
		// custom = static methods "from(String) -> String" and "to(String) -> String" in enum class
		@Options({ "camel", "pascal", "custom" })
		String value();

		String to() default "";  // to config name
		String from() default "";  // from config name
	}

	/**
	 *
	 */
	@Target( ElementType.FIELD )
	@Retention( RetentionPolicy.RUNTIME )
	@interface Ignore { }
}
