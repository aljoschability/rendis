package com.aljoschability.rendis.ui.editor.patterns;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IMoveAnchorContext;
import org.eclipse.graphiti.features.impl.DefaultMoveAnchorFeature;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.BoxRelativeAnchor;

public class BoundingBoxMoveAnchorFeature extends DefaultMoveAnchorFeature {
	public BoundingBoxMoveAnchorFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canMoveAnchor(IMoveAnchorContext context) {
		Anchor pe = context.getAnchor();

		return pe instanceof BoxRelativeAnchor;
	}

	@Override
	public void postMoveAnchor(IMoveAnchorContext context) {
		// TODO: we could constraint the anchor movement
		// BoxRelativeAnchor pe = (BoxRelativeAnchor) context.getAnchor();
		// System.out.println("w=" + pe.getRelativeWidth() + ", h=" + pe.getRelativeHeight());
	}
}
