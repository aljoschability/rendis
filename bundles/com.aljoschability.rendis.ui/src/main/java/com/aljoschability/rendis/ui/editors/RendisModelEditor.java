package com.aljoschability.rendis.ui.editors;

import java.io.IOException;
import java.util.Collections;
import java.util.EventObject;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory.Descriptor.Registry;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.edit.ui.view.ExtendedPropertySheetPage;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheetPage;

public class RendisModelEditor extends EditorPart implements CommandStackListener {
	private TreeViewer viewer;
	private AdapterFactoryEditingDomain editingDomain;
	private PropertySheetPage propertySheetPage;

	@Override
	public void dispose() {
		getCommandStack().removeCommandStackListener(this);

		((ComposedAdapterFactory) getAdapterFactory()).dispose();

		if (propertySheetPage != null) {
			propertySheetPage.dispose();
		}

		super.dispose();
	}

	@Override
	public boolean isDirty() {
		return getCommandStack().isSaveNeeded();
	}

	private BasicCommandStack getCommandStack() {
		return (BasicCommandStack) getEditingDomain().getCommandStack();
	}

	@Override
	public void createPartControl(Composite parent) {
		Composite page = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().applyTo(page);

		Tree tree = new Tree(page, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(tree);

		viewer = new TreeViewer(tree);
		viewer.setContentProvider(new AdapterFactoryContentProvider(getAdapterFactory()));
		viewer.setLabelProvider(new AdapterFactoryLabelProvider(getAdapterFactory()));
		viewer.setInput(getResourceSet());

		getSite().setSelectionProvider(viewer);
	}

	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class required) {
		if (IPropertySheetPage.class.equals(required)) {
			return getPropertySheetPage();
		}

		return super.getAdapter(required);
	}

	private IPropertySheetPage getPropertySheetPage() {
		if (propertySheetPage == null) {
			propertySheetPage = new ExtendedPropertySheetPage(getEditingDomain()) {
				@Override
				public void setSelectionToViewer(List<?> selection) {
					// RendisModelEditor.this.setSelectionToViewer(selection);
					RendisModelEditor.this.setFocus();
				}

				@Override
				public void setActionBars(IActionBars actionBars) {
					super.setActionBars(actionBars);
					// getActionBarContributor().shareGlobalActions(this, actionBars);
				}
			};
			propertySheetPage.setPropertySourceProvider(new AdapterFactoryContentProvider(getAdapterFactory()));
		}

		return propertySheetPage;
	}

	private AdapterFactory getAdapterFactory() {
		return getEditingDomain().getAdapterFactory();
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		if (input instanceof IFileEditorInput) {
			IFile file = ((IFileEditorInput) input).getFile();
			String path = file.getFullPath().toString();
			URI uri = URI.createPlatformResourceURI(path, true);

			try {
				getResourceSet().getResource(uri, true);

				setSite(site);
				setInput(input);
			} catch (Exception exception) {
				throw new PartInitException("Invalid input.");
			}
		} else {
			throw new PartInitException("Invalid input.");
		}
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		try {
			for (Resource resource : getResourceSet().getResources()) {
				resource.save(Collections.emptyMap());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private ResourceSet getResourceSet() {
		return getEditingDomain().getResourceSet();
	}

	private AdapterFactoryEditingDomain getEditingDomain() {
		if (editingDomain == null) {
			AdapterFactory adapterFactory = new ComposedAdapterFactory(Registry.INSTANCE);
			CommandStack commandStack = new BasicCommandStack();
			commandStack.addCommandStackListener(this);

			editingDomain = new AdapterFactoryEditingDomain(adapterFactory, commandStack);
		}
		return editingDomain;
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void commandStackChanged(EventObject event) {
		if (propertySheetPage != null && !propertySheetPage.getControl().isDisposed()) {
			propertySheetPage.refresh();
		}
	}
}
