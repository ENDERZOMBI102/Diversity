package com.enderzombi102.diversity.core.config.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public @interface ConfigOptions {
	byte SIDE_SERVER = 0b01;
	byte SIDE_CLIENT = 0b10;

	@Target( ElementType.FIELD )
	@Retention( RetentionPolicy.RUNTIME )
	@interface Sync {
		byte value();
	}

	@Target( ElementType.FIELD )
	@Retention( RetentionPolicy.RUNTIME )
	@interface Description {
		String value();
	}

	@Target( ElementType.FIELD )
	@Retention( RetentionPolicy.RUNTIME )
	@interface Range {
		double min();
		double max();
	}

	@Target({ ElementType.FIELD, ElementType.METHOD })
	@Retention( RetentionPolicy.RUNTIME )
	@interface Options {
		String[] value();
	}

	@Target( ElementType.FIELD )
	@Retention( RetentionPolicy.RUNTIME )
	@interface RenamingPolicy {
		// custom = static methods "from(String) -> String" and "to(String) -> String" in enum class
		@Options({ "camel", "pascal", "custom" })
		String value();

		String to() default "";  // to config name
		String from() default "";  // from config name
	}
}
