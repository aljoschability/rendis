package com.aljoschability.rendis.ui.properties;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory.Descriptor.Registry;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.gef.EditPart;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.IPropertySourceProvider;
import org.eclipse.ui.views.properties.PropertySheetEntry;
import org.eclipse.ui.views.properties.PropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ISection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import com.aljoschability.rendis.Part;

public class AdvancedSection implements ISection {
	private TabbedPropertySheetPage container;

	private ISelection selection;
	private EObject element;

	private Adapter listener;

	private IPropertySourceProvider provider;
	private PropertySheetPage page;

	private ComposedAdapterFactory adapterFactory;

	public AdvancedSection() {
		adapterFactory = new ComposedAdapterFactory(Registry.INSTANCE);

		// create property source provider
		provider = new AdapterFactoryContentProvider(adapterFactory);

		// create property sheet root entry
		PropertySheetEntry entry = new PropertySheetEntry();
		entry.setPropertySourceProvider(provider);

		page = new PropertySheetPage();
		page.setRootEntry(entry);

		listener = new AdapterImpl() {
			@Override
			public void notifyChanged(Notification msg) {
				if (msg.getEventType() != Notification.REMOVING_ADAPTER && element != null
						&& element.equals(msg.getNotifier())) {
					// TODO: fix advanced property sheet
					// System.out.println("could not refresh property sheet page!");
				}
			}
		};
	}

	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage container) {
		this.container = container;

		FormLayout layout = new FormLayout();
		layout.marginTop = 6;
		layout.marginRight = 6;
		layout.marginLeft = 6;

		Composite section = container.getWidgetFactory().createFlatFormComposite(parent);
		section.setLayout(layout);

		createWidgets(section, container.getWidgetFactory());
		layoutWidgets();
	}

	private void createWidgets(Composite parent, TabbedPropertySheetWidgetFactory factory) {
		page.createControl(parent);
		page.getControl().addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				container.resizeScrolledComposite();
			}
		});

		// resize the page tree to be fully visible on 'start'
		final Tree tree = (Tree) page.getControl();
		tree.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				Rectangle area = tree.getClientArea();
				TreeColumn[] columns = tree.getColumns();
				if (area.width > 0) {
					int w = area.width - 24;
					columns[0].setWidth(w * 40 / 100);
					columns[1].setWidth(w - columns[0].getWidth() - 4);
					tree.removeControlListener(this);
				}
			}
		});
	}

	protected void layoutWidgets() {
		// create empty form layout for parent
		page.getControl().getParent().setLayout(new FormLayout());

		FormData data = new FormData();

		data.left = new FormAttachment(0);
		data.right = new FormAttachment(100);
		data.top = new FormAttachment(0);
		data.bottom = new FormAttachment(100);

		page.getControl().setLayoutData(data);
	}

	@Override
	public void setInput(IWorkbenchPart part, ISelection selection) {
		if (selection.equals(this.selection)) {
			return;
		}

		this.selection = selection;

		removeListener();
		PictogramElement pe = getPE(selection);
		element = getBO(pe);
		addListener();

		if (element != null) {
			page.selectionChanged(part, new StructuredSelection(element));
		} else {
			page.selectionChanged(part, StructuredSelection.EMPTY);
		}
	}

	private static Part getBO(PictogramElement pe) {
		EObject bo = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
		if (bo instanceof Part) {
			return (Part) bo;
		}

		return null;
	}

	private static PictogramElement getPE(Object object) {
		if (object instanceof IStructuredSelection) {
			return getPE(((IStructuredSelection) object).getFirstElement());
		}

		if (object instanceof EditPart) {
			return getPE(((EditPart) object).getModel());
		}

		if (object instanceof PictogramElement) {
			return (PictogramElement) object;
		}

		return null;
	}

	@Override
	public void aboutToBeHidden() {
		removeListener();
	}

	private void removeListener() {
		if (element != null) {
			element.eAdapters().remove(listener);
		}
	}

	@Override
	public void aboutToBeShown() {
		addListener();
	}

	private void addListener() {
		if (element != null) {
			element.eAdapters().add(listener);
		}
	}

	@Override
	public void dispose() {
		page.dispose();
		adapterFactory.dispose();
	}

	@Override
	public int getMinimumHeight() {
		return SWT.DEFAULT;
	}

	@Override
	public void refresh() {
		page.refresh();
	}

	@Override
	public boolean shouldUseExtraSpace() {
		return true;
	}
}
