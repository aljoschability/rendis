package com.aljoschability.rendis.ui.editor.cubicle;

import org.eclipse.graphiti.features.context.IAddConnectionContext;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.util.IColorConstant;

import com.aljoschability.rendis.Part;
import com.aljoschability.rendis.Wire;
import com.aljoschability.rendis.services.GSRendis;
import com.aljoschability.rendis.ui.pattern.AbstractRendisConnectionPattern;

public class WirePattern extends AbstractRendisConnectionPattern {
	@Override
	protected boolean isBusinessElement(Part bo) {
		return bo instanceof Wire;
	}

	@Override
	protected Connection add(IAddConnectionContext context) {
		Wire bo = (Wire) context.getNewObject();
		Connection pe = GSRendis.PE.createFreeFormConnection(getDiagram());

		pe.setStart(context.getSourceAnchor());
		pe.setEnd(context.getTargetAnchor());

		Polyline line = GSRendis.GA.createPolyline(pe);
		line.setForeground(manageColor(IColorConstant.BLACK));
		line.setLineWidth((int) (bo.getCrossSection() * 3));

		return pe;
	}
}
