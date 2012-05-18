package com.aljoschability.rendis.ui.editor;

import org.eclipse.graphiti.features.IFeatureProvider;

import com.aljoschability.rendis.Cubicle;
import com.aljoschability.rendis.ui.AbstractOpenDiagramFeature;

public class OpenCubicleDiagramFeature extends AbstractOpenDiagramFeature {
	public OpenCubicleDiagramFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	protected String getTypeName() {
		return "Cubicle";
	}

	@Override
	protected String getDiagramTypeId() {
		return Cubicle.class.getCanonicalName();
	}
}
