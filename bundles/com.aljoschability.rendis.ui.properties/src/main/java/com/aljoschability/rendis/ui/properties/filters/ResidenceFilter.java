package com.aljoschability.rendis.ui.properties.filters;

import com.aljoschability.rendis.Part;
import com.aljoschability.rendis.Residence;

public class ResidenceFilter extends AbstractPartFilter {
	@Override
	protected boolean select(Part bo) {
		return bo instanceof Residence;
	}
}
