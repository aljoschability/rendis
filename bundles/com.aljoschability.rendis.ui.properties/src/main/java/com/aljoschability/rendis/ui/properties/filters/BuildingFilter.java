package com.aljoschability.rendis.ui.properties.filters;

import com.aljoschability.rendis.Building;
import com.aljoschability.rendis.Part;

public class BuildingFilter extends AbstractPartFilter {
	@Override
	protected boolean select(Part bo) {
		return bo instanceof Building;
	}
}
