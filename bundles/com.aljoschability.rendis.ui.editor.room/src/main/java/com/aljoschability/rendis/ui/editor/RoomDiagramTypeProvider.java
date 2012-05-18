package com.aljoschability.rendis.ui.editor;

import org.eclipse.graphiti.features.IFeatureProvider;

import com.aljoschability.rendis.ui.AbstractRendisDiagramTypeProvider;

public class RoomDiagramTypeProvider extends AbstractRendisDiagramTypeProvider {
	@Override
	protected IFeatureProvider createFeatureProvider() {
		return new RoomFeatureProvider(this);
	}
}
