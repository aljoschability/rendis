package com.aljoschability.rendis.ui.editor;

import org.eclipse.graphiti.features.IFeatureProvider;

import com.aljoschability.rendis.ui.AbstractRendisDiagramTypeProvider;

public class FloorDiagramTypeProvider extends AbstractRendisDiagramTypeProvider {
	@Override
	protected IFeatureProvider createFeatureProvider() {
		return new FloorFeatureProvider(this);
	}
}
