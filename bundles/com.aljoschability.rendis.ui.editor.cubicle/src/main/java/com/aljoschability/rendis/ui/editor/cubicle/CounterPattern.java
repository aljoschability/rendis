package com.aljoschability.rendis.ui.editor.cubicle;

import org.eclipse.core.runtime.Assert;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.algorithms.Ellipse;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.styles.Font;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.pictograms.BoxRelativeAnchor;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.util.IColorConstant;

import com.aljoschability.rendis.Counter;
import com.aljoschability.rendis.Part;
import com.aljoschability.rendis.WirePort;
import com.aljoschability.rendis.services.GSRendis;

public class CounterPattern extends CubicleDevicePattern {
	@Override
	public IReason updateNeeded(IUpdateContext context) {
		PictogramElement pe = context.getPictogramElement();
		Counter bo = (Counter) getBusinessObjectForPictogramElement(pe);

		// rated current
		String boCurrent = getRatedCurrent(bo);
		Text peCurrent = getRatedCurrent(pe);
		if (GSRendis.unequals(boCurrent, peCurrent)) {
			return Reason.createTrueReason("The rated current is out of date.");
		}

		return super.updateNeeded(context);
	}

	@Override
	public boolean update(IUpdateContext context) {
		PictogramElement pe = context.getPictogramElement();
		Counter bo = (Counter) getBusinessObjectForPictogramElement(pe);

		// rated current
		String boCurrent = getRatedCurrent(bo);
		Text peCurrent = getRatedCurrent(pe);
		if (GSRendis.unequals(boCurrent, peCurrent)) {
			peCurrent.setValue(boCurrent);
		}

		return true;
	}

	protected void doAdd(ContainerShape pe, IAddContext context) {
		Counter bo = (Counter) context.getNewObject();

		Assert.isTrue(bo.getIncomings().size() == bo.getOutgoings().size());

		int connectors = bo.getIncomings().size();
		int height = 176;

		if (connectors == 1) {
			int width = 36;

			// main rectangle
			Rectangle main = GSRendis.GA.createRectangle(pe);
			main.setBackground(manageColor(IColorConstant.WHITE));
			main.setForeground(manageColor(IColorConstant.GRAY));
			main.setTransparency(0.5);
			main.setX(context.getX());
			main.setY(context.getY());
			main.setWidth(width);
			main.setHeight(height);

			// out port
			WirePort outgoing = bo.getOutgoings().get(0);
			BoxRelativeAnchor outAnchor = GSRendis.PE.createBoxRelativeAnchor(pe);
			link(outAnchor, outgoing);

			Ellipse outGa = GSRendis.GA.createEllipse(outAnchor);
			outGa.setBackground(manageColor(IColorConstant.WHITE));
			outGa.setForeground(manageColor(IColorConstant.GRAY));

			outGa.setX(10);
			outGa.setY(10);
			outGa.setWidth(15);
			outGa.setHeight(15);

			// visible rectangle
			Rectangle vbox = GSRendis.GA.createRectangle(main);
			vbox.setBackground(manageColor(IColorConstant.WHITE));
			vbox.setForeground(manageColor(IColorConstant.BLACK));
			vbox.setX(0);
			vbox.setY(42);
			vbox.setWidth(width);
			vbox.setHeight(91);

			// counter box
			Rectangle counterBox = GSRendis.GA.createRectangle(vbox);
			counterBox.setBackground(manageColor(IColorConstant.GRAY));
			counterBox.setForeground(manageColor(IColorConstant.BLACK));
			counterBox.setX(8);
			counterBox.setY(8);
			counterBox.setWidth(19);
			counterBox.setHeight(55);

			// counter text
			Text counterText = GSRendis.GA.createText(counterBox);
			counterText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
			counterText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
			counterText.setForeground(manageColor(IColorConstant.BLACK));
			counterText.setValue("00000,0");
			Font font = manageFont(FONT_MONOSPACE, 10);
			counterText.setFont(font);
			counterText.setAngle(270);
			counterText.setX(2);
			counterText.setY(3);
			counterText.setWidth(15);
			counterText.setHeight(49);

			// rated current text
			Text ratedCurrentText = GSRendis.GA.createText(vbox);
			ratedCurrentText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
			ratedCurrentText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
			ratedCurrentText.setForeground(manageColor(IColorConstant.BLACK));
			ratedCurrentText.setValue(getRatedCurrent(bo));
			ratedCurrentText.setX(4);
			ratedCurrentText.setY(66);
			ratedCurrentText.setWidth(width - 8);
			ratedCurrentText.setHeight(20);

			// in port
			WirePort incoming = bo.getIncomings().get(0);
			BoxRelativeAnchor inAnchor = GSRendis.PE.createBoxRelativeAnchor(pe);
			link(inAnchor, incoming);

			Ellipse inGa = GSRendis.GA.createEllipse(inAnchor);
			inGa.setBackground(manageColor(IColorConstant.WHITE));
			inGa.setForeground(manageColor(IColorConstant.GRAY));

			inGa.setX(10);
			inGa.setY(150);
			inGa.setWidth(15);
			inGa.setHeight(15);
		} else {
			int width = 35 * (connectors * 2 + 1) + 1;

			// main rectangle
			Rectangle main = GSRendis.GA.createRectangle(pe);
			main.setBackground(manageColor(IColorConstant.WHITE));
			main.setForeground(manageColor(IColorConstant.GRAY));
			main.setTransparency(0.5);
			main.setX(context.getX());
			main.setY(context.getY());
			main.setWidth(width);
			main.setHeight(height);

			// visible rectangle
			Rectangle vbox = GSRendis.GA.createRectangle(main);
			vbox.setBackground(manageColor(IColorConstant.WHITE));
			vbox.setForeground(manageColor(IColorConstant.BLACK));
			vbox.setX(0);
			vbox.setY(42);
			vbox.setWidth(width);
			vbox.setHeight(91);

			// counter box
			Rectangle counterBox = GSRendis.GA.createRectangle(vbox);
			counterBox.setBackground(manageColor(IColorConstant.GRAY));
			counterBox.setForeground(manageColor(IColorConstant.BLACK));
			counterBox.setX(24);
			counterBox.setY(30);
			counterBox.setWidth(123);
			counterBox.setHeight(31);

			// counter text
			Text counterText = GSRendis.GA.createText(counterBox);
			counterText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
			counterText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
			counterText.setForeground(manageColor(IColorConstant.BLACK));
			counterText.setValue("000000,0");
			Font font = manageFont(FONT_MONOSPACE, 12);
			counterText.setFont(font);
			counterText.setX(2);
			counterText.setY(2);
			counterText.setWidth(123);
			counterText.setHeight(27);

			// rated current text
			Text ratedCurrentText = GSRendis.GA.createText(vbox);
			ratedCurrentText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
			ratedCurrentText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
			ratedCurrentText.setForeground(manageColor(IColorConstant.BLACK));
			ratedCurrentText.setValue(getRatedCurrent(bo));
			ratedCurrentText.setX(width - 60);
			ratedCurrentText.setY(24);
			ratedCurrentText.setWidth(40);
			ratedCurrentText.setHeight(31);

			// in ports
			int index = 0;
			for (WirePort incoming : bo.getIncomings()) {
				BoxRelativeAnchor anchor = GSRendis.PE.createBoxRelativeAnchor(pe);
				link(anchor, incoming);

				Ellipse ga = GSRendis.GA.createEllipse(anchor);
				ga.setBackground(manageColor(IColorConstant.WHITE));
				ga.setForeground(manageColor(IColorConstant.GRAY));

				ga.setX(10 + index * 35 * 2);
				ga.setY(150);
				ga.setWidth(15);
				ga.setHeight(15);

				index++;
			}

			// out ports
			index = 0;
			for (WirePort outgoing : bo.getOutgoings()) {
				BoxRelativeAnchor anchor = GSRendis.PE.createBoxRelativeAnchor(pe);
				link(anchor, outgoing);

				Ellipse ga = GSRendis.GA.createEllipse(anchor);
				ga.setBackground(manageColor(IColorConstant.WHITE));
				ga.setForeground(manageColor(IColorConstant.GRAY));

				ga.setX(10 + 35 + index * 35 * 2);
				ga.setY(150);
				ga.setWidth(15);
				ga.setHeight(15);

				index++;
			}
		}
	}

	private static String getRatedCurrent(Counter bo) {
		return bo.getRatedCurrent() + "A";
	}

	private static Text getRatedCurrent(PictogramElement pe) {
		return (Text) pe.getGraphicsAlgorithm().getGraphicsAlgorithmChildren().get(0).getGraphicsAlgorithmChildren()
				.get(1);
	}

	@Override
	protected boolean isBusinessElement(Part bo) {
		return bo instanceof Counter;
	}
}
