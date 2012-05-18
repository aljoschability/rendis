package com.aljoschability.rendis.ui.editor;

import org.eclipse.graphiti.features.IFeatureProvider;

import com.aljoschability.rendis.ui.AbstractRendisDiagramTypeProvider;

public class ResidenceDiagramTypeProvider extends AbstractRendisDiagramTypeProvider {
	@Override
	protected IFeatureProvider createFeatureProvider() {
		return new ResidenceFeatureProvider(this);
	}
}
