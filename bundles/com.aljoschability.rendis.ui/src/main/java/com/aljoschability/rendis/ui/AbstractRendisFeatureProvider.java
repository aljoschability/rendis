package com.aljoschability.rendis.ui;

import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.pattern.DefaultFeatureProviderWithPatterns;

public abstract class AbstractRendisFeatureProvider extends DefaultFeatureProviderWithPatterns {
	public AbstractRendisFeatureProvider(IDiagramTypeProvider dtp) {
		super(dtp);
	}
}
