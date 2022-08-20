package com.enderzombi102.diversity.core.config.api;

public interface ChangeListener<T extends Data> {
	void onChange( ConfigHolder<T> config );
}
