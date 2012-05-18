package com.aljoschability.rendis.ui.editor.cubicle;

import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.util.IColorConstant;

import com.aljoschability.rendis.Part;
import com.aljoschability.rendis.Transformator;
import com.aljoschability.rendis.services.GSRendis;

public class TransformatorPattern extends CubicleDevicePattern {
	@Override
	public String getCreateName() {
		return "Transformator";
	}

	protected void doAdd(ContainerShape pe, IAddContext context) {
		Rectangle ga = GSRendis.GA.createRectangle(pe);
		ga.setBackground(manageColor(IColorConstant.YELLOW));
		ga.setForeground(manageColor(IColorConstant.BLACK));
		ga.setX(context.getX());
		ga.setY(context.getY());
		ga.setWidth(context.getWidth());
		ga.setHeight(context.getHeight());
	}

	@Override
	protected boolean isBusinessElement(Part bo) {
		return bo instanceof Transformator;
	}
}
