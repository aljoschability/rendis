package com.aljoschability.rendis.ui.editor.cubicle;

import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.IResizeShapeContext;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.util.ColorConstant;
import org.eclipse.graphiti.util.IColorConstant;

import com.aljoschability.rendis.CubicleDevice;
import com.aljoschability.rendis.services.GSRendis;
import com.aljoschability.rendis.ui.pattern.AbstractRendisShapePattern;

public abstract class CubicleDevicePattern extends AbstractRendisShapePattern {
	protected static final IColorConstant ABB_COLOR = new ColorConstant(236, 27, 36);
	protected static final IColorConstant SWITCH_COLOR = new ColorConstant(1, 8, 168);

	public CubicleDevicePattern() {
		super(null);
	}

	@Override
	public PictogramElement add(IAddContext context) {
		ContainerShape cpe = context.getTargetContainer();
		CubicleDevice bo = (CubicleDevice) context.getNewObject();

		ContainerShape pe = GSRendis.PE.createContainerShape(cpe, true);
		link(pe, bo);

		doAdd(pe, context);

		return pe;
	}

	protected abstract void doAdd(ContainerShape pe, IAddContext context);

	@Override
	public boolean canResizeShape(IResizeShapeContext context) {
		return false;
	}
}
