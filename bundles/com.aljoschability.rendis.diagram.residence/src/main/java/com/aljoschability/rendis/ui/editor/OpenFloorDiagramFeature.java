package com.aljoschability.rendis.ui.editor;

import org.eclipse.graphiti.features.IFeatureProvider;

import com.aljoschability.rendis.Floor;
import com.aljoschability.rendis.ui.AbstractOpenDiagramFeature;

public class OpenFloorDiagramFeature extends AbstractOpenDiagramFeature {
	public OpenFloorDiagramFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	protected String getTypeName() {
		return "Floor";
	}

	@Override
	protected String getDiagramTypeId() {
		return Floor.class.getCanonicalName();
	}
}
