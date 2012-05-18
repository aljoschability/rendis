package com.aljoschability.rendis.ui.properties.filters;

import com.aljoschability.rendis.Floor;
import com.aljoschability.rendis.Part;

public class FloorFilter extends AbstractPartFilter {
	@Override
	protected boolean select(Part bo) {
		return bo instanceof Floor;
	}
}
