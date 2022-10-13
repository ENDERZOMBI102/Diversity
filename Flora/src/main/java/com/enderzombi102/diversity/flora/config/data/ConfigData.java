package com.enderzombi102.diversity.flora.config.data;

import com.enderzombi102.endconfig.api.ConfigOptions.Range;
import com.enderzombi102.endconfig.api.Data;

public class ConfigData implements Data {
	@Range( min = 1, max = 20 )
	public int cataplantPower = 2;
}
