package com.aljoschability.rendis.ui.editor.cubicle.features;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.impl.AbstractUpdateFeature;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

import com.aljoschability.rendis.Wire;
import com.aljoschability.rendis.services.GSRendis;

public class WireUpdateFeature extends AbstractUpdateFeature {
	public WireUpdateFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canUpdate(IUpdateContext context) {
		PictogramElement pe = context.getPictogramElement();
		Object bo = getBusinessObjectForPictogramElement(pe);

		return bo instanceof Wire;
	}

	@Override
	public IReason updateNeeded(IUpdateContext context) {
		PictogramElement pe = context.getPictogramElement();
		Wire bo = (Wire) getBusinessObjectForPictogramElement(pe);

		// wire thickness
		int boWidth = (int) (bo.getCrossSection() * 3);
		int peWidth = pe.getGraphicsAlgorithm().getLineWidth();
		if (GSRendis.unequals(boWidth, peWidth)) {
			return Reason.createTrueReason("The wire thickness is out of date.");
		}

		return Reason.createFalseReason();
	}

	@Override
	public boolean update(IUpdateContext context) {
		PictogramElement pe = context.getPictogramElement();
		Wire bo = (Wire) getBusinessObjectForPictogramElement(pe);

		// wire thickness
		int boWidth = (int) (bo.getCrossSection() * 3);
		int peWidth = pe.getGraphicsAlgorithm().getLineWidth();
		if (GSRendis.unequals(boWidth, peWidth)) {
			pe.getGraphicsAlgorithm().setLineWidth(boWidth);
		}

		return true;
	}

}
