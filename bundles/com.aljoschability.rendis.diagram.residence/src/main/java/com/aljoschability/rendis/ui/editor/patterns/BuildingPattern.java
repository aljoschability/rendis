package com.aljoschability.rendis.ui.editor.patterns;

import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.util.IColorConstant;

import com.aljoschability.rendis.Building;
import com.aljoschability.rendis.Part;
import com.aljoschability.rendis.RendisFactory;
import com.aljoschability.rendis.Residence;
import com.aljoschability.rendis.services.GSRendis;
import com.aljoschability.rendis.ui.pattern.AbstractRendisShapePattern;

public class BuildingPattern extends AbstractRendisShapePattern {
	public BuildingPattern() {
		super(null);
	}

	@Override
	public String getCreateImageId() {
		return Building.class.getCanonicalName();
	}

	@Override
	public PictogramElement add(IAddContext context) {
		ContainerShape cpe = context.getTargetContainer();
		Building bo = (Building) context.getNewObject();

		ContainerShape pe = GSRendis.PE.createContainerShape(cpe, true);
		link(pe, bo);

		Rectangle ga = GSRendis.GA.createRectangle(pe);
		ga.setBackground(manageColor(IColorConstant.RED));
		ga.setForeground(manageColor(IColorConstant.BLACK));
		ga.setX(context.getX());
		ga.setY(context.getY());
		ga.setWidth(context.getWidth());
		ga.setHeight(context.getHeight());

		return pe;
	}

	@Override
	public boolean isBusinessElement(Part bo) {
		return bo instanceof Building;
	}

	@Override
	public String getCreateName() {
		return "Building";
	}

	@Override
	protected boolean canCreateOn(Part cbo) {
		return cbo instanceof Residence;
	}

	@Override
	protected Part create(Part cbo) {
		Building bo = RendisFactory.eINSTANCE.createBuilding();
		bo.setResidence((Residence) cbo);

		return bo;
	}
}
