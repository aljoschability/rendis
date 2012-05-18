package com.aljoschability.rendis.ui.editor;

import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.pattern.DefaultFeatureProviderWithPatterns;

import com.aljoschability.rendis.ui.editor.floor.RoomPattern;

public class FloorFeatureProvider extends DefaultFeatureProviderWithPatterns {
	public FloorFeatureProvider(IDiagramTypeProvider dtp) {
		super(dtp);

		addPattern(new RoomPattern());
	}
}
