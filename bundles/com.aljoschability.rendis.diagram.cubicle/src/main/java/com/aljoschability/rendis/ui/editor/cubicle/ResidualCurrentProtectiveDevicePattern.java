package com.aljoschability.rendis.ui.editor.cubicle;

import org.eclipse.core.runtime.Assert;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.algorithms.Ellipse;
import org.eclipse.graphiti.mm.algorithms.Image;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.BoxRelativeAnchor;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.util.ColorConstant;
import org.eclipse.graphiti.util.IColorConstant;

import com.aljoschability.rendis.Part;
import com.aljoschability.rendis.ResidualCurrentProtectiveDevice;
import com.aljoschability.rendis.WirePort;
import com.aljoschability.rendis.services.GSRendis;
import com.aljoschability.rendis.ui.editor.CubicleImageProvider;

public class ResidualCurrentProtectiveDevicePattern extends CubicleDevicePattern {
	protected static final IColorConstant TEST_COLOR = new ColorConstant(134, 178, 129);

	@Override
	public IReason updateNeeded(IUpdateContext context) {
		PictogramElement pe = context.getPictogramElement();
		ResidualCurrentProtectiveDevice bo = (ResidualCurrentProtectiveDevice) getBusinessObjectForPictogramElement(pe);

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
		ResidualCurrentProtectiveDevice bo = (ResidualCurrentProtectiveDevice) getBusinessObjectForPictogramElement(pe);

		// rated current
		String boCurrent = getRatedCurrent(bo);
		Text peCurrent = getRatedCurrent(pe);
		if (GSRendis.unequals(boCurrent, peCurrent)) {
			peCurrent.setValue(boCurrent);
		}

		return true;
	}

	protected void doAdd(ContainerShape pe, IAddContext context) {
		ResidualCurrentProtectiveDevice bo = (ResidualCurrentProtectiveDevice) context.getNewObject();
		Assert.isTrue(bo.getIncomings().size() == bo.getOutgoings().size());

		int numberOfPorts = bo.getIncomings().size();
		int width = 35 * (numberOfPorts + 1) + 1;
		int height = 176;

		// main rectangle
		Rectangle main = GSRendis.GA.createRectangle(pe);
		main.setBackground(manageColor(IColorConstant.WHITE));
		main.setForeground(manageColor(IColorConstant.GRAY));
		main.setTransparency(0.5);
		main.setX(context.getX());
		main.setY(context.getY());
		main.setWidth(width);
		main.setHeight(height);

		// outgoing ports
		int index = 0;
		for (WirePort outgoing : bo.getOutgoings()) {
			BoxRelativeAnchor anchor = GSRendis.PE.createBoxRelativeAnchor(pe);
			link(anchor, outgoing);

			Ellipse port = GSRendis.GA.createEllipse(anchor);
			port.setBackground(manageColor(IColorConstant.WHITE));
			port.setForeground(manageColor(IColorConstant.GRAY));

			port.setX(10 + index * 35);
			port.setY(10);
			port.setWidth(15);
			port.setHeight(15);

			index++;
		}

		// visible rectangle
		Rectangle vbox = GSRendis.GA.createRectangle(main);
		vbox.setBackground(manageColor(IColorConstant.WHITE));
		vbox.setForeground(manageColor(IColorConstant.BLACK));
		vbox.setX(0);
		vbox.setY(42);
		vbox.setWidth(width);
		vbox.setHeight(91);

		// logo
		Rectangle logoStripe = GSRendis.GA.createRectangle(vbox);
		logoStripe.setBackground(manageColor(ABB_COLOR));
		logoStripe.setForeground(manageColor(ABB_COLOR));
		logoStripe.setX(1);
		logoStripe.setY(1);
		logoStripe.setWidth(width - 2);
		logoStripe.setHeight(16);

		Image logo = GSRendis.GA.createImage(logoStripe, CubicleImageProvider.LOGO_ABB);
		logo.setX(3);
		logo.setY(3);
		logo.setHeight(10);
		logo.setWidth(25);

		// rated current text
		Text ratedCurrentText = GSRendis.GA.createText(vbox);
		ratedCurrentText.setForeground(manageColor(IColorConstant.BLACK));
		ratedCurrentText.setValue(getRatedCurrent(bo));
		ratedCurrentText.setX(6);
		ratedCurrentText.setY(20);
		ratedCurrentText.setWidth(40);
		ratedCurrentText.setHeight(20);

		// test button
		Ellipse testButton = GSRendis.GA.createEllipse(vbox);
		testButton.setBackground(manageColor(IColorConstant.WHITE));
		testButton.setForeground(manageColor(TEST_COLOR));
		testButton.setX(width - 60);
		testButton.setY(25);
		testButton.setWidth(15);
		testButton.setHeight(10);

		// test led
		Rectangle ledDing = GSRendis.GA.createRectangle(vbox);
		ledDing.setBackground(manageColor(IColorConstant.DARK_GREEN));
		ledDing.setForeground(manageColor(IColorConstant.BLACK));
		ledDing.setX(width - 25);
		ledDing.setY(25);
		ledDing.setWidth(15);
		ledDing.setHeight(7);

		// switch
		Rectangle switchPart1 = GSRendis.GA.createRectangle(vbox);
		switchPart1.setBackground(manageColor(SWITCH_COLOR));
		switchPart1.setForeground(manageColor(IColorConstant.BLACK));
		switchPart1.setX(width - 34);
		switchPart1.setY(48);
		switchPart1.setWidth(25);
		switchPart1.setHeight(18);

		Rectangle switchPart2 = GSRendis.GA.createRectangle(vbox);
		switchPart2.setBackground(manageColor(SWITCH_COLOR));
		switchPart2.setForeground(manageColor(IColorConstant.BLACK));
		switchPart2.setX(width - 39);
		switchPart2.setY(64);
		switchPart2.setWidth(35);
		switchPart2.setHeight(11);

		// bottom ports
		index = 0;
		for (WirePort incoming : bo.getIncomings()) {
			BoxRelativeAnchor anchor = GSRendis.PE.createBoxRelativeAnchor(pe);
			link(anchor, incoming);

			Ellipse ga = GSRendis.GA.createEllipse(anchor);
			ga.setBackground(manageColor(IColorConstant.WHITE));
			ga.setForeground(manageColor(IColorConstant.GRAY));

			ga.setX(10 + index * 35);
			ga.setY(150);
			ga.setWidth(15);
			ga.setHeight(15);

			index++;
		}
	}

	private static String getRatedCurrent(ResidualCurrentProtectiveDevice bo) {
		return bo.getRatedCurrent() + "A";
	}

	private static Text getRatedCurrent(PictogramElement pe) {
		return (Text) pe.getGraphicsAlgorithm().getGraphicsAlgorithmChildren().get(0).getGraphicsAlgorithmChildren()
				.get(1);
	}

	@Override
	public String getCreateName() {
		return "RCD";
	}

	@Override
	protected boolean isBusinessElement(Part bo) {
		return bo instanceof ResidualCurrentProtectiveDevice;
	}
}
