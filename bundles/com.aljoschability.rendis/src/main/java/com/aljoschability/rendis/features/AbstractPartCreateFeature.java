package com.aljoschability.rendis.features;

import java.text.MessageFormat;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;

import com.aljoschability.rendis.Part;

public abstract class AbstractPartCreateFeature extends AbstractCreateFeature {
	public AbstractPartCreateFeature(IFeatureProvider fp, String name) {
		super(fp, name, null);
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		ContainerShape cpe = context.getTargetContainer();
		Object cbo = getBusinessObjectForPictogramElement(cpe);

		if (cbo instanceof Part) {
			return canCreate((Part) cbo);
		}
		return false;
	}

	protected abstract boolean canCreate(Part container);

	@Override
	public Object[] create(ICreateContext context) {
		ContainerShape cpe = context.getTargetContainer();
		Part cbo = (Part) getBusinessObjectForPictogramElement(cpe);

		Part bo = doCreate(cbo);
		bo.setId(EcoreUtil.generateUUID());

		addGraphicalRepresentation(context, bo);

		return new Object[] { bo };
	}

	protected abstract Part doCreate(Part container);

	@Override
	public String getCreateDescription() {
		String pattern = "Create {0}";
		return MessageFormat.format(pattern, getCreateName());
	}
}
