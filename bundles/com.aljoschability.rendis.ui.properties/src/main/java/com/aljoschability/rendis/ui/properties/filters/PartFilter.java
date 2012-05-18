package com.aljoschability.rendis.ui.properties.filters;

import com.aljoschability.rendis.Part;

public class PartFilter extends AbstractPartFilter {
	@Override
	protected boolean select(Part bo) {
		return true;
	}
}
