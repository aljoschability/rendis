package com.aljoschability.rendis.services;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.context.ICreateConnectionContext;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeService;

import com.aljoschability.rendis.Part;

public final class GSRendis {
	public static IPeService PE = GS.PE;

	public static IGaService GA = GS.GA;

	public GSRendis() {
		// hide constructor
	}

	public static Part getSourceBO(ICreateConnectionContext context) {
		return getBO(context.getSourcePictogramElement());
	}

	public static Part getTargetBO(ICreateConnectionContext context) {
		return getBO(context.getTargetPictogramElement());
	}

	public static Part getBO(PictogramElement pe) {
		EObject bo = GS.getBO(pe);
		if (bo instanceof Part) {
			return (Part) bo;
		}
		return null;
	}

	public static boolean unequals(String bo, Text pe) {
		return GS.unequals(bo, pe);
	}

	public static boolean unequals(int bo, int pe) {
		return GS.unequals(bo, pe);
	}
}
