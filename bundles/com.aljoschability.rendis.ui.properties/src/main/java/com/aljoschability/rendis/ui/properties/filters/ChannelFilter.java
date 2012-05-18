package com.aljoschability.rendis.ui.properties.filters;

import com.aljoschability.rendis.Channel;
import com.aljoschability.rendis.Part;

public class ChannelFilter extends AbstractPartFilter {
	@Override
	protected boolean select(Part bo) {
		return bo instanceof Channel;
	}
}
