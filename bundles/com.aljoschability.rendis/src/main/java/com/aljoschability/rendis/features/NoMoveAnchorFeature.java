package com.aljoschability.rendis.features;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IMoveAnchorContext;
import org.eclipse.graphiti.features.impl.DefaultMoveAnchorFeature;

public class NoMoveAnchorFeature extends DefaultMoveAnchorFeature {
	public NoMoveAnchorFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canMoveAnchor(IMoveAnchorContext context) {
		return false;
	}
}
