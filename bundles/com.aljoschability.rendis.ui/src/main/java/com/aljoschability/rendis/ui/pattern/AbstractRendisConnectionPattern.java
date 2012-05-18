package com.aljoschability.rendis.ui.pattern;

import org.eclipse.graphiti.features.context.IAddConnectionContext;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.pattern.AbstractConnectionPattern;

import com.aljoschability.rendis.Part;

public abstract class AbstractRendisConnectionPattern extends AbstractConnectionPattern {
	@Override
	public PictogramElement add(IAddContext context) {
		if (context instanceof IAddConnectionContext) {
			Connection pe = add((IAddConnectionContext) context);
			link(pe, context.getNewObject());
			return pe;
		}
		return super.add(context);
	}

	protected abstract Connection add(IAddConnectionContext context);

	@Override
	public boolean canAdd(IAddContext context) {
		return isMainBusinessObjectApplicable(context.getNewObject());
	}

	protected boolean isMainBusinessObjectApplicable(Object bo) {
		if (bo instanceof Part) {
			return isBusinessElement((Part) bo);
		}
		return false;
	}

	protected abstract boolean isBusinessElement(Part bo);
}
