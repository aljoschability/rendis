package com.aljoschability.rendis.ui.properties.filters;

import com.aljoschability.rendis.ChannelPort;
import com.aljoschability.rendis.Part;

public class ChannelPortFilter extends AbstractPartFilter {
	@Override
	protected boolean select(Part bo) {
		return bo instanceof ChannelPort;
	}
}
