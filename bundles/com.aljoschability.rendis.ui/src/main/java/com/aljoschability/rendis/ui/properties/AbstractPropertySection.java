package com.aljoschability.rendis.ui.properties;

import java.util.Collection;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gef.EditPart;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.impl.CustomContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.platform.IDiagramEditor;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.IContributedContentsView;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.views.properties.tabbed.ISection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import com.aljoschability.rendis.Part;
import com.aljoschability.rendis.ui.CoreColors;
import com.aljoschability.rendis.ui.CoreImages;
import com.aljoschability.rendis.ui.PropertyFeature;

public abstract class AbstractPropertySection implements ISection {
	protected static final String EMPTY = ""; //$NON-NLS-1$
	protected static final int LABEL_WIDTH = 120;
	protected static final int MARGIN = 6;

	private Part bo;

	private TransactionalEditingDomain editingDomain;

	private IDiagramEditor editor;

	private Adapter listener;

	private TabbedPropertySheetPage page;
	private PictogramElement pe;
	private ISelection selection;

	public AbstractPropertySection() {
		listener = new AdapterImpl() {
			@Override
			public void notifyChanged(Notification msg) {
				if (shouldRefresh(msg)) {
					refresh();
				}
			}
		};
	}

	@Override
	public void setInput(IWorkbenchPart part, ISelection selection) {
		if (selection.equals(this.selection)) {
			return;
		}

		removeAdapters();

		this.selection = selection;

		pe = getPE(selection);
		bo = getBO(pe);
		editingDomain = TransactionUtil.getEditingDomain(bo);
		editor = getEditor(part);

		Assert.isNotNull(pe);
		Assert.isNotNull(bo);
		Assert.isNotNull(editor);

		addAdapters();
	}

	@Override
	public void aboutToBeHidden() {
		removeAdapters();
	}

	private final void removeAdapters() {
		if (pe != null) {
			pe.eAdapters().remove(listener);
		}

		if (bo != null) {
			bo.eAdapters().remove(listener);
		}
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

	private static Part getBO(PictogramElement pe) {
		EObject bo = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
		if (bo instanceof Part) {
			return (Part) bo;
		}

		return null;
	}

	private static IDiagramEditor getEditor(IWorkbenchPart part) {
		if (part instanceof IDiagramEditor) {
			return (IDiagramEditor) part;
		}

		// contributed contents view
		IContributedContentsView view = (IContributedContentsView) part.getAdapter(IContributedContentsView.class);
		if (view != null) {
			part = view.getContributingPart();

			if (part instanceof IDiagramEditor) {
				return (DiagramEditor) part;
			}
		}

		// catalog editor
		if (part instanceof MultiPageEditorPart) {
			Object page = ((MultiPageEditorPart) part).getSelectedPage();
			if (page instanceof IWorkbenchPart) {
				return getEditor((IWorkbenchPart) page);
			}
		}
		return null;
	}

	@Override
	public void aboutToBeShown() {
		addAdapters();
	}

	private final void addAdapters() {
		pe.eAdapters().add(listener);
		bo.eAdapters().add(listener);
	}

	@Override
	public final void createControls(Composite parent, TabbedPropertySheetPage page) {
		this.page = page;

		FormLayout layout = new FormLayout();
		layout.marginTop = MARGIN;
		layout.marginRight = MARGIN;
		layout.marginLeft = MARGIN;

		Composite section = page.getWidgetFactory().createFlatFormComposite(parent);
		section.setLayout(layout);

		createWidgets(section, page.getWidgetFactory());
		layoutWidgets();
		hookWidgetListeners();
	}

	protected abstract void createWidgets(Composite parent, TabbedPropertySheetWidgetFactory factory);

	protected void layoutWidgets() {
		// nothing here
	}

	protected void hookWidgetListeners() {
		// nothing here
	}

	@Override
	public void dispose() {
		// nothing here
	}

	@Override
	public int getMinimumHeight() {
		return SWT.DEFAULT;
	}

	@Override
	public void refresh() {
		// nothing here
	}

	@Override
	public boolean shouldUseExtraSpace() {
		return false;
	}

	protected final void add(EStructuralFeature feature, Collection<Object> values) {
		execute(AddCommand.create(getEditingDomain(), getBO(), feature, values));
	}

	protected final void add(EStructuralFeature feature, Object value) {
		execute(AddCommand.create(getEditingDomain(), getBO(), feature, value));
	}

	protected final void remove(EStructuralFeature feature, Collection<Object> values) {
		execute(RemoveCommand.create(getEditingDomain(), getBO(), feature, values));
	}

	protected final void remove(EStructuralFeature feature, Object value) {
		execute(RemoveCommand.create(getEditingDomain(), getBO(), feature, value));
	}

	protected final void set(EStructuralFeature feature, Object value) {
		execute(SetCommand.create(getEditingDomain(), getBO(), feature, value));
	}

	/**
	 * Executes the command on the command stack of the editing domain.
	 * 
	 * @param command The command to execute.
	 */
	protected final void execute(final Command command) {
		IFeatureProvider fp = getFeatureProvider();

		// create wrapping command
		Command realCommand = new RecordingCommand(getEditingDomain()) {
			@Override
			protected void doExecute() {
				command.execute();
				postExecute();
			}
		};

		// create context
		ICustomContext context = new CustomContext(new PictogramElement[] { pe });
		context.putProperty(PropertyFeature.COMMAND_KEY, realCommand);

		// create feature
		PropertyFeature feature = new PropertyFeature(fp);

		// execute the feature
		getFeatureProvider().getDiagramTypeProvider().getDiagramBehavior().executeFeature(feature, context);
	}

	protected IFeatureProvider getFeatureProvider() {
		return getEditor().getDiagramTypeProvider().getFeatureProvider();
	}

	protected final IDiagramEditor getEditor() {
		return editor;
	}

	protected final TransactionalEditingDomain getEditingDomain() {
		return editingDomain;
	}

	protected final Object getValue(EStructuralFeature feature) {
		Assert.isNotNull(feature);

		return getBO().eGet(feature);
	}

	/**
	 * Decides whether to refresh the controls after a model change.
	 * 
	 * @param msg The received notification.
	 * @return Returns <code>true</code> when {@link #refresh()} should be called.
	 */
	protected boolean shouldRefresh(Notification msg) {
		return msg.getEventType() != Notification.REMOVING_ADAPTER && getBO() != null;
	}

	/**
	 * Getter of the currently selected element.
	 * 
	 * @return Returns the currently selected element.
	 */
	protected final Part getBO() {
		return bo;
	}

	protected final void decorate(IStatus state, Control control, Label info) {
		Image image = null;
		String text = null;
		Color color = null;
		if (state != null) {
			text = state.getMessage();
			switch (state.getSeverity()) {
			case IStatus.ERROR:
				color = CoreColors.get(CoreColors.ERROR);
				image = CoreImages.get(CoreImages.ERROR);
				break;
			case IStatus.WARNING:
				color = CoreColors.get(CoreColors.WARNING);
				image = CoreImages.get(CoreImages.WARNING);
				break;
			case IStatus.INFO:
				image = CoreImages.get(CoreImages.QUESTION);
			default:
				break;
			}
		}

		if (control != null && !control.isDisposed()) {
			control.setBackground(color);
		}

		if (info != null && !info.isDisposed()) {
			info.setImage(image);
			info.setToolTipText(text);
		}
	}

	protected final void decorate(State state, Label info) {
		Image image = null;
		String text = null;
		if (state != null) {
			text = state.getMessage();
			switch (state.getType()) {
			case ERROR:
				image = CoreImages.get(CoreImages.ERROR);
				break;
			case WARNING:
				image = CoreImages.get(CoreImages.WARNING);
				break;
			case INFO:
				image = CoreImages.get(CoreImages.QUESTION);
			default:
				break;
			}
		}

		if (info != null && !info.isDisposed()) {
			info.setImage(image);
			info.setToolTipText(text);
		}
	}

	protected final TabbedPropertySheetPage getPage() {
		return page;
	}

	protected final PictogramElement getPE() {
		return pe;
	}

	/**
	 * Method that will be called after a command has successfully been executed.
	 * 
	 * @see #execute(Command)
	 */
	protected void postExecute() {
		// nothing additional by default
	}

	/**
	 * Refreshes the label of the property sheet and the title of the diagram editor.
	 */
	protected final void refreshTitle() {
		if (page != null) {
			page.labelProviderChanged(null);
		}

		if (editor != null) {
			editor.refreshTitle();
			editor.refreshTitleToolTip();
		}
	}
}
