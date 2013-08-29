package com.aljoschability.rendis.ui.editor.patterns;

import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.util.IColorConstant;

import com.aljoschability.rendis.Part;
import com.aljoschability.rendis.RendisFactory;
import com.aljoschability.rendis.Residence;
import com.aljoschability.rendis.Supplier;
import com.aljoschability.rendis.services.GSRendis;
import com.aljoschability.rendis.ui.pattern.AbstractRendisShapePattern;

public class SupplierPattern extends AbstractRendisShapePattern {
	public SupplierPattern() {
		super(null);
	}

	@Override
	public PictogramElement add(IAddContext context) {
		ContainerShape cpe = context.getTargetContainer();
		Supplier bo = (Supplier) context.getNewObject();

		ContainerShape pe = Graphiti.getPeService().createContainerShape(cpe, true);
		link(pe, bo);
		GSRendis.PE.createChopboxAnchor(pe);

		Rectangle ga = Graphiti.getGaService().createRectangle(pe);
		ga.setBackground(manageColor(IColorConstant.GREEN));
		ga.setForeground(manageColor(IColorConstant.BLACK));
		ga.setX(context.getX());
		ga.setY(context.getY());
		ga.setWidth(context.getWidth());
		ga.setHeight(context.getHeight());

		return pe;
	}

	@Override
	public boolean isBusinessElement(Part bo) {
		return bo instanceof Supplier;
	}

	@Override
	public String getCreateName() {
		return "Supplier";
	}

	@Override
	public String getCreateImageId() {
		return Supplier.class.getCanonicalName();
	}

	@Override
	protected Part create(Part cbo) {
		Supplier bo = RendisFactory.eINSTANCE.createSupplier();

		bo.setResidence((Residence) cbo);

		return bo;
	}

	@Override
	protected boolean canCreateOn(Part cbo) {
		return cbo instanceof Residence;
	}
}
