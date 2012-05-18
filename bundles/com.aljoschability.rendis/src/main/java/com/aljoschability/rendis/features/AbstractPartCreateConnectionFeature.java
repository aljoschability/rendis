package com.aljoschability.rendis.features;

import java.text.MessageFormat;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateConnectionContext;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.features.impl.AbstractCreateConnectionFeature;
import org.eclipse.graphiti.mm.pictograms.Connection;

import com.aljoschability.rendis.Part;
import com.aljoschability.rendis.services.GSRendis;

public abstract class AbstractPartCreateConnectionFeature extends AbstractCreateConnectionFeature {
	public AbstractPartCreateConnectionFeature(IFeatureProvider fp, String name) {
		super(fp, name, null);
	}

	@Override
	public Connection create(ICreateConnectionContext context) {
		Part sbo = GSRendis.getSourceBO(context);
		Part tbo = GSRendis.getTargetBO(context);

		Part bo = create(sbo, tbo, context);
		bo.setId(EcoreUtil.generateUUID());

		// add context
		AddConnectionContext addContext = new AddConnectionContext(context.getSourceAnchor(), context.getTargetAnchor());
		addContext.setNewObject(bo);

		return (Connection) getFeatureProvider().addIfPossible(addContext);
	}

	protected Part create(Part sbo, Part tbo, ICreateConnectionContext context) {
		return create(sbo, tbo);
	}

	protected Part create(Part sbo, Part tbo) {
		return null;
	}

	@Override
	public boolean canStartConnection(ICreateConnectionContext context) {
		return canStart(GSRendis.getSourceBO(context), context);
	}

	protected boolean canStart(Part source, ICreateConnectionContext context) {
		return canStart(source);
	}

	protected boolean canStart(Part source) {
		return false;
	}

	@Override
	public boolean canCreate(ICreateConnectionContext context) {
		return canCreate(GSRendis.getSourceBO(context), GSRendis.getTargetBO(context), context);
	}

	protected boolean canCreate(Part sbo, Part tbo, ICreateConnectionContext context) {
		return canCreate(sbo, tbo);
	}

	protected boolean canCreate(Part source, Part target) {
		return canCreateAt(target);
	}

	protected boolean canCreateAt(Part target) {
		return false;
	}

	@Override
	public String getCreateDescription() {
		String pattern = "Create {0}";
		return MessageFormat.format(pattern, getCreateName());
	}
}
