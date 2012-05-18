package com.aljoschability.rendis.ui.editor;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.tb.IToolBehaviorProvider;

import com.aljoschability.rendis.ui.AbstractRendisDiagramTypeProvider;

public class CubicleDiagramTypeProvider extends AbstractRendisDiagramTypeProvider {
	@Override
	protected IFeatureProvider createFeatureProvider() {
		return new CubicleFeatureProvider(this);
	}
	
	@Override
	protected IToolBehaviorProvider createToolBehaviorProvider() {
		return new CubicleToolBehaviorProvider(this);
	}
}
