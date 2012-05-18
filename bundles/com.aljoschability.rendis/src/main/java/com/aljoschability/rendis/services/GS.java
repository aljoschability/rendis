package com.aljoschability.rendis.services;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.context.ICreateConnectionContext;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeService;

import com.aljoschability.rendis.Part;
import com.aljoschability.rendis.Residence;

public final class GS {
	public static IPeService PE = Graphiti.getPeService();

	public static IGaService GA = Graphiti.getGaService();

	public GS() {
		// hide constructor
	}

	public static Part getBO(PictogramElement pe) {
		Object bo = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
		if (bo instanceof Part) {
			return (Part) bo;
		}
		return null;
	}

	public static Part getSourceBO(ICreateConnectionContext context) {
		return getBO(context.getSourcePictogramElement());
	}

	public static Part getTargetBO(ICreateConnectionContext context) {
		return getBO(context.getTargetPictogramElement());
	}

	public static Residence getResidence(EObject element) {
		EObject container = element;
		while (container != null) {
			if (container instanceof Residence) {
				return (Residence) container;
			}
			container = container.eContainer();
		}
		return null;
	}

	public static boolean unequals(String bo, Text pe) {
		Assert.isNotNull(bo);
		Assert.isNotNull(pe);

		return !bo.equals(pe.getValue());
	}

	public static boolean unequals(int bo, int pe) {
		return bo != pe;
	}
	//
	// public static int getHeight(Font font, String text) {
	// return getSize(font, text).getHeight();
	// }
	//
	// public static int getHeight(Text text) {
	// return getSize(text).getHeight();
	// }
	//
	// public static int getWidth(Text text) {
	// return getSize(text).getWidth();
	// }
	//
	// public static Size getSize(Text text) {
	// Font font = text.getFont();
	// if (font == null && text.getStyle() != null) {
	// font = text.getStyle().getFont();
	// }
	//
	// return getSize(font, text.getValue());
	// }
	//
	// public static int getWidth(Font font, String text) {
	// return getSize(font, text).getWidth();
	// }
	//
	// public static Size getSize(Font font, String value) {
	// return getSize(font, value, 0, 0);
	// }
	//
	// public static Size getSize(Font font, String value, int paddingWidth, int paddingHeight) {
	// if (font == null || value == null || value.isEmpty()) {
	// return new Size(0, 0);
	// }
	//
	// CalculatorRunnable runnable = new CalculatorRunnable(value, font);
	// DisplayUtil.sync(runnable);
	//
	// return new Size(runnable.getWidth() + paddingWidth * 2, runnable.getHeight() + paddingHeight * 2);
	// }
}
