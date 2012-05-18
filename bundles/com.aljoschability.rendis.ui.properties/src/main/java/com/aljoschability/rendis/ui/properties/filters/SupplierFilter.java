package com.aljoschability.rendis.ui.properties.filters;

import com.aljoschability.rendis.Part;
import com.aljoschability.rendis.Supplier;

public class SupplierFilter extends AbstractPartFilter {
	@Override
	protected boolean select(Part bo) {
		return bo instanceof Supplier;
	}
}
