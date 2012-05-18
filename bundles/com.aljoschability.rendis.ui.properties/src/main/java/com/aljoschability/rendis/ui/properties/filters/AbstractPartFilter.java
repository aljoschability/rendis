package com.aljoschability.rendis.ui.properties.filters;

import org.eclipse.gef.EditPart;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.jface.viewers.IFilter;

import com.aljoschability.rendis.Part;

public abstract class AbstractPartFilter implements IFilter {
	@Override
	public boolean select(Object element) {
		if (element instanceof EditPart) {
			return select((EditPart) element);
		}

		if (element instanceof PictogramElement) {
			return select((PictogramElement) element);
		}

		if (element instanceof Part) {
			return select((Part) element);
		}

		return false;
	}

	protected boolean select(EditPart ep) {
		return select(ep.getModel());
	}

	protected boolean select(PictogramElement pe) {
		return select(Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe));
	}

	protected abstract boolean select(Part bo);
}
