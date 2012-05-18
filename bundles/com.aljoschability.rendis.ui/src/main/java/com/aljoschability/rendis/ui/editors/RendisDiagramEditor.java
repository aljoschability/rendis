package com.aljoschability.rendis.ui.editors;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import com.aljoschability.rendis.Part;
import com.aljoschability.rendis.ui.AbstractRendisDiagramTypeProvider;
import com.aljoschability.rendis.ui.outline.RendisDiagramOutlinePage;

public class RendisDiagramEditor extends DiagramEditor {
	public static final String ID = "com.aljoschability.rendis.ui.editor.diagram"; //$NON-NLS-1$

	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class required) {
		if (IContentOutlinePage.class.equals(required)) {
			return getContentOutlinePage();
		}
		return super.getAdapter(required);
	}

	protected IContentOutlinePage getContentOutlinePage() {
		return new RendisDiagramOutlinePage(this);
	}

	public ILabelProvider getOutlinePageLabelProvider() {
		return getDiagramTypeProvider().getOutlinePageLabelProvider();
	}

	public ITreeContentProvider getOutlinePageContentProvider() {
		return getDiagramTypeProvider().getOutlinePageContentProvider();
	}

	@Override
	public AbstractRendisDiagramTypeProvider getDiagramTypeProvider() {
		return (AbstractRendisDiagramTypeProvider) super.getDiagramTypeProvider();
	}

	public AdapterFactory getAdapterFactory() {
		TransactionalEditingDomain editingDomain = getEditingDomain();
		if (editingDomain instanceof AdapterFactoryEditingDomain) {
			return ((AdapterFactoryEditingDomain) editingDomain).getAdapterFactory();
		}
		return null;
	}

	@Override
	public String getContributorId() {
		return "com.aljoschability.rendis.contributor"; //$NON-NLS-1$
	}

	public Part getBO() {
		Diagram pe = getDiagramTypeProvider().getDiagram();
		EObject bo = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
		if (bo instanceof Part) {
			return (Part) bo;
		}
		return null;
	}
}
