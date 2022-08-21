package com.enderzombi102.endconfig;

public interface ChangeListener<T extends Data> {
	void onChange( ConfigHolder<T> config );
}
