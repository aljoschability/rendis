package com.aljoschability.rendis.ui.editor.patterns;

import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.util.IColorConstant;

import com.aljoschability.rendis.Building;
import com.aljoschability.rendis.Floor;
import com.aljoschability.rendis.Part;
import com.aljoschability.rendis.RendisFactory;
import com.aljoschability.rendis.services.GSRendis;
import com.aljoschability.rendis.ui.pattern.AbstractRendisShapePattern;

public class FloorPattern extends AbstractRendisShapePattern {
	public FloorPattern() {
		super(null);
	}

	@Override
	public PictogramElement add(IAddContext context) {
		ContainerShape cpe = context.getTargetContainer();
		Floor bo = (Floor) context.getNewObject();

		ContainerShape pe = Graphiti.getPeService().createContainerShape(cpe, true);
		link(pe, bo);
		GSRendis.PE.createChopboxAnchor(pe);

		Rectangle ga = Graphiti.getGaService().createRectangle(pe);
		ga.setBackground(manageColor(IColorConstant.DARK_BLUE));
		ga.setForeground(manageColor(IColorConstant.BLACK));
		ga.setX(context.getX());
		ga.setY(context.getY());
		ga.setWidth(context.getWidth());
		ga.setHeight(context.getHeight());

		return pe;
	}

	@Override
	public String getCreateImageId() {
		return Floor.class.getCanonicalName();
	}

	@Override
	public boolean isBusinessElement(Part bo) {
		return bo instanceof Floor;
	}

	@Override
	public String getCreateName() {
		return "Floor";
	}

	@Override
	protected Part create(Part cbo) {
		Floor bo = RendisFactory.eINSTANCE.createFloor();

		bo.setBuilding((Building) cbo);

		return bo;
	}

	@Override
	protected boolean canCreateOn(Part cbo) {
		return cbo instanceof Building;
	}
}
