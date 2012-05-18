package com.aljoschability.rendis.ui;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.features.AbstractDrillDownFeature;

import com.aljoschability.rendis.Part;
import com.aljoschability.rendis.ui.editors.RendisDiagramEditor;

public abstract class AbstractOpenDiagramFeature extends AbstractDrillDownFeature {
	private String name;
	private boolean hasDoneChanges;

	public AbstractOpenDiagramFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		if (name == null) {
			name = "Open " + getTypeName() + " Diagram";
		}
		return name;
	}

	@Override
	protected String getDiagramEditorId(Diagram diagram) {
		return RendisDiagramEditor.ID;
	}

	protected abstract String getTypeName();

	@Override
	public boolean hasDoneChanges() {
		return hasDoneChanges;
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		PictogramElement[] pes = context.getPictogramElements();
		if (pes.length == 1) {
			PictogramElement pe = pes[0];
			Object bo = getBusinessObjectForPictogramElement(pe);

			return bo instanceof Part;
		}
		return false;
	}

	@Override
	protected Collection<Diagram> getLinkedDiagrams(PictogramElement pe) {
		Part bo = (Part) getBusinessObjectForPictogramElement(pe);

		// search existing diagram
		Diagram diagram = null;
		for (EObject content : pe.eResource().getContents()) {
			if (content instanceof Diagram) {
				Object diagramBo = getBusinessObjectForPictogramElement((Diagram) content);
				if (bo.equals(diagramBo)) {
					diagram = (Diagram) content;
					break;
				}
			}
		}

		if (diagram == null) {
			String dtid = getDiagramTypeId();
			diagram = Graphiti.getPeService().createDiagram(dtid, bo.getId(), true);
			link(diagram, bo);
			pe.eResource().getContents().add(diagram);
			hasDoneChanges = true;
		}

		return Collections.singleton(diagram);
	}

	protected abstract String getDiagramTypeId();

	@Override
	protected Collection<Diagram> getDiagrams() {
		return Collections.emptySet();
	}
}
