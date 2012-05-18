package com.aljoschability.rendis.ui.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.aljoschability.rendis.Part;

public final class RendisDiagramAdaptor {
	private RendisDiagramAdaptor() {
		// hide constructor
	}

	public static List<Part> getAll(ISelection selection) {
		List<Part> elements = new ArrayList<Part>();

		if (selection instanceof IStructuredSelection) {
			for (Object part : ((IStructuredSelection) selection).toArray()) {
				Part bo = get(part);
				if (bo != null) {
					elements.add(bo);
				}
			}
		}

		return elements;
	}

	public static Part get(Object element) {
		if (element instanceof IStructuredSelection) {
			return get(((IStructuredSelection) element).getFirstElement());
		}
		if (element instanceof EditPart) {
			return get((EditPart) element);
		}
		if (element instanceof PictogramElement) {
			return get((PictogramElement) element);
		}
		if (element instanceof Part) {
			return (Part) element;
		}
		return null;
	}

	public static Part get(EditPart element) {
		return get(element.getModel());
	}

	public static Part get(PictogramElement pe) {
		EObject bo = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
		if (bo instanceof Part) {
			return (Part) bo;
		}
		return null;
	}
}
