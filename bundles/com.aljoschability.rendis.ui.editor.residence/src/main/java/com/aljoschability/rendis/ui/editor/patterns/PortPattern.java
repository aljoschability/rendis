package com.aljoschability.rendis.ui.editor.patterns;

import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.mm.algorithms.Ellipse;
import org.eclipse.graphiti.mm.pictograms.BoxRelativeAnchor;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.util.IColorConstant;

import com.aljoschability.rendis.ChannelNode;
import com.aljoschability.rendis.ChannelPort;
import com.aljoschability.rendis.Part;
import com.aljoschability.rendis.RendisFactory;
import com.aljoschability.rendis.services.GSRendis;
import com.aljoschability.rendis.ui.pattern.AbstractRendisShapePattern;

public class PortPattern extends AbstractRendisShapePattern {
	public PortPattern() {
		super(null);
	}

	@Override
	public String getCreateImageId() {
		return ChannelPort.class.getCanonicalName();
	}

	@Override
	public PictogramElement add(IAddContext context) {
		ContainerShape cpe = context.getTargetContainer();
		ChannelPort bo = (ChannelPort) context.getNewObject();

		BoxRelativeAnchor pe = GSRendis.PE.createBoxRelativeAnchor(cpe);
		link(pe, bo);

		Ellipse ga = GSRendis.GA.createEllipse(pe);
		ga.setBackground(manageColor(IColorConstant.WHITE));
		ga.setForeground(manageColor(IColorConstant.BLACK));
		// ga.setX(context.getX());
		// ga.setY(context.getY());
		ga.setWidth(10);
		ga.setHeight(10);

		return pe;
	}

	@Override
	public boolean isBusinessElement(Part bo) {
		return bo instanceof ChannelPort;
	}

	@Override
	public String getCreateName() {
		return "Channel Port";
	}

	@Override
	protected Part create(Part cbo) {
		ChannelPort bo = RendisFactory.eINSTANCE.createChannelPort();

		bo.setNode((ChannelNode) cbo);

		return bo;
	}

	@Override
	protected boolean canCreateOn(Part cbo) {
		return cbo instanceof ChannelNode;
	}
}
