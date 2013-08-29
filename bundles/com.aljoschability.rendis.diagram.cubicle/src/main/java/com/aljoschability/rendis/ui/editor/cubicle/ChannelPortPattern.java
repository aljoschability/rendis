package com.aljoschability.rendis.ui.editor.cubicle;

import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.algorithms.Ellipse;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.util.IColorConstant;

import com.aljoschability.rendis.ChannelPort;
import com.aljoschability.rendis.Part;
import com.aljoschability.rendis.services.GSRendis;
import com.aljoschability.rendis.ui.pattern.AbstractRendisShapePattern;

public class ChannelPortPattern extends AbstractRendisShapePattern {
	public ChannelPortPattern() {
		super(null);
	}

	@Override
	public IReason updateNeeded(IUpdateContext context) {
		PictogramElement pe = context.getPictogramElement();
		ChannelPort bo = (ChannelPort) getBusinessObjectForPictogramElement(pe);

		// text
		String boText = getText(bo);
		Text peText = getText(pe);
		if (GSRendis.unequals(boText, peText)) {
			return Reason.createTrueReason("The text is out of date.");
		}

		return super.updateNeeded(context);
	}

	@Override
	public boolean update(IUpdateContext context) {
		PictogramElement pe = context.getPictogramElement();
		ChannelPort bo = (ChannelPort) getBusinessObjectForPictogramElement(pe);

		// text
		String boText = getText(bo);
		Text peText = getText(pe);
		if (GSRendis.unequals(boText, peText)) {
			peText.setValue(boText);
		}

		return true;
	}

	@Override
	public PictogramElement add(IAddContext context) {
		ContainerShape cpe = context.getTargetContainer();
		ChannelPort bo = (ChannelPort) context.getNewObject();

		int size = 70 + 1;

		ContainerShape pe = GSRendis.PE.createContainerShape(cpe, true);
		link(pe, bo);

		Ellipse ga = GSRendis.GA.createEllipse(pe);
		ga.setBackground(manageColor(IColorConstant.WHITE));
		ga.setForeground(manageColor(IColorConstant.BLACK));
		ga.setX(context.getX());
		ga.setY(context.getY());
		ga.setWidth(size);
		ga.setHeight(size);

		Text text = GSRendis.GA.createText(ga);
		text.setForeground(manageColor(IColorConstant.BLACK));
		text.setFont(manageFont(FONT_DEFAULT, 10));
		text.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		text.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		text.setX(4);
		text.setY(4);
		text.setWidth(size - 8);
		text.setHeight(size - 8);

		text.setValue(getText(bo));

		return pe;
	}

	private static String getText(ChannelPort bo) {
		return bo.getName();
	}

	private static Text getText(PictogramElement pe) {
		return (Text) pe.getGraphicsAlgorithm().getGraphicsAlgorithmChildren().get(0);
	}

	@Override
	protected boolean isBusinessElement(Part bo) {
		return bo instanceof ChannelPort;
	}
}
