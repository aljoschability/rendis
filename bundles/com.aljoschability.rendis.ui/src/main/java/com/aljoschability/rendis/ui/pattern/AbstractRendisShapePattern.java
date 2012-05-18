package com.aljoschability.rendis.ui.pattern;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.pattern.AbstractPattern;
import org.eclipse.graphiti.pattern.config.IPatternConfiguration;

import com.aljoschability.rendis.Part;

public abstract class AbstractRendisShapePattern extends AbstractPattern {
	protected static final String FONT_DEFAULT = "Segoe UI"; //$NON-NLS-1$
	protected static final String FONT_MONOSPACE = "Consolas"; //$NON-NLS-1$

	public AbstractRendisShapePattern(IPatternConfiguration pc) {
		super(pc);
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		PictogramElement cpe = context.getTargetContainer();
		Object cbo = getBusinessObjectForPictogramElement(cpe);

		if (cbo instanceof Part) {
			return canCreateOn((Part) cbo);
		}
		return false;
	}

	protected boolean canCreateOn(Part cbo) {
		return false;
	}

	@Override
	public Object[] create(ICreateContext context) {
		ContainerShape cpe = context.getTargetContainer();

		Part cbo = (Part) getBusinessObjectForPictogramElement(cpe);

		Part bo = create(cbo);
		bo.setId(EcoreUtil.generateUUID());

		addGraphicalRepresentation(context, bo);

		return new Object[] { bo };
	}

	protected Part create(Part cbo) {
		return null;
	}

	@Override
	public boolean canUpdate(IUpdateContext context) {
		PictogramElement pe = context.getPictogramElement();
		Object bo = getBusinessObjectForPictogramElement(pe);

		return isMainBusinessObjectApplicable(bo);
	}

	@Override
	public boolean canAdd(IAddContext context) {
		return isMainBusinessObjectApplicable(context.getNewObject());
	}

	@Override
	protected boolean isPatternControlled(PictogramElement pe) {
		Object bo = getBusinessObjectForPictogramElement(pe);

		return isMainBusinessObjectApplicable(bo);
	}

	@Override
	protected boolean isPatternRoot(PictogramElement pe) {
		Object bo = getBusinessObjectForPictogramElement(pe);

		return isMainBusinessObjectApplicable(bo);
	}

	@Override
	public boolean isMainBusinessObjectApplicable(Object bo) {
		if (bo instanceof Part) {
			return isBusinessElement((Part) bo);
		}
		return false;
	}

	protected abstract boolean isBusinessElement(Part bo);

	protected boolean unequals(String bo, Text pe) {
		if (bo != null) {
			return !bo.equals(pe);
		}

		if (pe != null) {
			return !pe.equals(bo);
		}

		if (pe == null && bo == null) {
			return false;
		}

		return true;
	}
}
