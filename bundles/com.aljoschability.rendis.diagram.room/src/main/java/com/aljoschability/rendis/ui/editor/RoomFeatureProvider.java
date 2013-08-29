package com.aljoschability.rendis.ui.editor;

import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.pattern.DefaultFeatureProviderWithPatterns;

import com.aljoschability.rendis.ui.editor.patterns.RoomPattern;

public class RoomFeatureProvider extends DefaultFeatureProviderWithPatterns {
	public RoomFeatureProvider(IDiagramTypeProvider dtp) {
		super(dtp);

		addPattern(new RoomPattern());
	}
}
