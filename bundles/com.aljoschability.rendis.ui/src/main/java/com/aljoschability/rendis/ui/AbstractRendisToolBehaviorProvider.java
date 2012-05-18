package com.aljoschability.rendis.ui;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.ICreateConnectionFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.context.IDoubleClickContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.palette.IToolEntry;
import org.eclipse.graphiti.palette.impl.ConnectionCreationToolEntry;
import org.eclipse.graphiti.palette.impl.ObjectCreationToolEntry;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.tb.DefaultToolBehaviorProvider;

import com.aljoschability.rendis.Part;

public abstract class AbstractRendisToolBehaviorProvider extends DefaultToolBehaviorProvider {
	public AbstractRendisToolBehaviorProvider(IDiagramTypeProvider dtp) {
		super(dtp);
	}

	@Override
	public ICustomFeature getDoubleClickFeature(IDoubleClickContext context) {
		PictogramElement pe = context.getInnerPictogramElement();
		Part bo = getBO(pe);

		if (bo != null) {
			return getDoubleClickFeature(bo);
		}
		return null;
	}

	protected Part getBO(PictogramElement pe) {
		EObject bo = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
		if (bo instanceof Part) {
			return (Part) bo;
		}
		return null;
	}

	protected abstract ICustomFeature getDoubleClickFeature(Part bo);

	protected static IToolEntry createEntry(ICreateConnectionFeature feature) {
		return createEntry(Collections.singleton(feature), feature);
	}

	protected static IToolEntry createEntry(Collection<ICreateConnectionFeature> features,
			ICreateConnectionFeature feature) {
		String name = feature.getCreateName();
		String description = feature.getCreateDescription();
		String imageId = feature.getCreateImageId();

		return createEntry(name, description, imageId, features);
	}

	protected static IToolEntry createEntry(String name, String description, String imageId,
			Collection<ICreateConnectionFeature> features) {
		ConnectionCreationToolEntry tool = new ConnectionCreationToolEntry(name, description, imageId, null);
		for (ICreateConnectionFeature feature : features) {
			tool.addCreateConnectionFeature(feature);
		}

		return tool;
	}

	protected static IToolEntry createEntry(ICreateFeature feature) {
		return new ObjectCreationToolEntry(feature.getCreateName(), feature.getCreateDescription(),
				feature.getCreateImageId(), feature.getCreateLargeImageId(), feature);
	}
}
