package com.enderzombi102.endconfig.api;

public interface ChangeListener<T extends Data> {
	void onChange( ConfigHolder<T> config );
}
