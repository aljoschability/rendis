package com.aljoschability.rendis.ui.outline;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IPostSelectionProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.part.Page;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import com.aljoschability.rendis.Part;
import com.aljoschability.rendis.internal.ui.outline.DiagramEditorOutlineThumbnail;
import com.aljoschability.rendis.ui.CoreColors;
import com.aljoschability.rendis.ui.CoreImages;
import com.aljoschability.rendis.ui.editors.RendisDiagramEditor;
import com.aljoschability.rendis.ui.util.RendisDiagramAdaptor;

public class RendisDiagramOutlinePage extends Page implements IContentOutlinePage, ISelectionListener,
		IPostSelectionProvider, ISelectionChangedListener {
	private final RendisDiagramEditor editor;
	private final Collection<ISelectionChangedListener> selectionListeners;

	private boolean isLinked;
	private List<Part> selection;

	private Action collapseAllAction;
	private Action toggleLinkingAction;
	private Action showTreeAction;
	private Action showThumbnailAction;

	private PageBook book;

	private TreeViewer treeViewer;

	private Canvas thumbnailCanvas;
	private LightweightSystem thumbnailLightweightSystem;
	private DiagramEditorOutlineThumbnail thumbnail;

	public RendisDiagramOutlinePage(RendisDiagramEditor editor) {
		this.editor = editor;

		selectionListeners = new HashSet<ISelectionChangedListener>();

		isLinked = true;

		buildActions();
	}

	private void buildActions() {
		// collapse all
		collapseAllAction = new Action("Collapse All", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				handleCollapseAll();
			}
		};
		collapseAllAction.setToolTipText("Collapse All");
		collapseAllAction.setImageDescriptor(CoreImages.getDescriptor(CoreImages.COLLAPSE_ALL));

		// editor linking
		toggleLinkingAction = new Action("Link with Editor", IAction.AS_CHECK_BOX) {
			@Override
			public void run() {
				handleLinking(isChecked());
			}
		};
		toggleLinkingAction.setToolTipText("Link with Editor");
		toggleLinkingAction.setImageDescriptor(CoreImages.getDescriptor(CoreImages.LINKING));
		toggleLinkingAction.setChecked(true);

		// show tree
		showTreeAction = new Action("Tree", IAction.AS_RADIO_BUTTON) {
			@Override
			public void run() {
				handleShowPage(this);
			}
		};
		showTreeAction.setToolTipText("Show Tree");
		showTreeAction.setImageDescriptor(CoreImages.getDescriptor(CoreImages.OUTLINE_TREE));

		// show thumbnail
		showThumbnailAction = new Action("Thumbnail", IAction.AS_RADIO_BUTTON) {
			@Override
			public void run() {
				handleShowPage(this);
			}
		};
		showThumbnailAction.setToolTipText("Show Thumbnail");
		showThumbnailAction.setImageDescriptor(CoreImages.getDescriptor(CoreImages.OUTLINE_THUMBNAIL));
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		ISelection selection = getSelection();
		if (!StructuredSelection.EMPTY.equals(selection)) {
			final SelectionChangedEvent notification = new SelectionChangedEvent(this, selection);

			for (final ISelectionChangedListener listener : selectionListeners) {
				SafeRunner.run(new SafeRunnable() {
					public void run() {
						listener.selectionChanged(notification);
					}
				});
			}
		}
	}

	@Override
	public ISelection getSelection() {
		if (isLinked && treeViewer != null) {
			return treeViewer.getSelection();
		}
		return StructuredSelection.EMPTY;
	}

	@Override
	public void createControl(Composite parent) {
		// create page book
		book = new PageBook(parent, SWT.BORDER);

		// outline
		treeViewer = new TreeViewer(book, SWT.NONE);
		treeViewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				Object element = ((IStructuredSelection) event.getSelection()).getFirstElement();
				boolean state = treeViewer.getExpandedState(element);
				treeViewer.setExpandedState(element, !state);
			}
		});
		treeViewer.setContentProvider(editor.getOutlinePageContentProvider());
		treeViewer.setLabelProvider(editor.getOutlinePageLabelProvider());
		treeViewer.setInput(editor.getBO());
		treeViewer.setAutoExpandLevel(2);
		treeViewer.addPostSelectionChangedListener(this);

		// overview
		thumbnailCanvas = new Canvas(book, SWT.NONE);
		thumbnailCanvas.setBackground(CoreColors.get(CoreColors.EMPTY));
		thumbnailLightweightSystem = new LightweightSystem(thumbnailCanvas);

		thumbnail = new DiagramEditorOutlineThumbnail(editor.getGraphicalViewer());
		thumbnailLightweightSystem.setContents(thumbnail);

		// defaulting to outline
		book.showPage(treeViewer.getControl());
		showTreeAction.setChecked(true);

	}

	@Override
	public void dispose() {
		if (thumbnail != null) {
			thumbnail.dispose();
			thumbnail = null;
		}

		selectionListeners.clear();

		super.dispose();
	}

	@Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		selectionListeners.add(listener);
	}

	@Override
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		selectionListeners.remove(listener);
	}

	@Override
	public void addPostSelectionChangedListener(ISelectionChangedListener listener) {
		selectionListeners.add(listener);
	}

	@Override
	public void removePostSelectionChangedListener(ISelectionChangedListener listener) {
		selectionListeners.remove(listener);
	}

	@Override
	public void makeContributions(IMenuManager mm, IToolBarManager tbm, IStatusLineManager slm) {
		tbm.add(collapseAllAction);
		tbm.add(toggleLinkingAction);
		tbm.add(new Separator());
		tbm.add(showTreeAction);
		tbm.add(showThumbnailAction);
	}

	public void refresh() {
		if (treeViewer != null && !treeViewer.getControl().isDisposed()) {
			treeViewer.refresh();
		}
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selected) {
		// only change for my editor
		if (editor.equals(part)) {
			List<Part> elements = RendisDiagramAdaptor.getAll(selected);
			if (isLinked && !elements.equals(selection)) {
				if (treeViewer != null && !treeViewer.getControl().isDisposed()) {
					treeViewer.setSelection(new StructuredSelection(elements), true);
				}
			}
			selection = elements;
		}
	}

	@Override
	public void init(IPageSite site) {
		super.init(site);

		// register as selection provider
		site.setSelectionProvider(this);

		// register as post selection listener
		site.getPage().addPostSelectionListener(this);
	}

	@Override
	public void setSelection(ISelection selection) {
		if (isLinked && treeViewer != null) {
			treeViewer.setSelection(selection, true);
		}
	}

	@Override
	public Control getControl() {
		return book;
	}

	@Override
	public void setFocus() {
		book.setFocus();
	}

	public boolean isLinkingEnabled() {
		return isLinked;
	}

	private void handleShowPage(Action action) {
		if (showTreeAction.equals(action)) {
			book.showPage(treeViewer.getControl());
			collapseAllAction.setEnabled(true);
			toggleLinkingAction.setEnabled(true);
		} else if (showThumbnailAction.equals(action)) {
			book.showPage(thumbnailCanvas);
			collapseAllAction.setEnabled(false);
			toggleLinkingAction.setEnabled(false);
		}
	}

	private void handleLinking(boolean isChecked) {
		isLinked = isChecked;

		if (isLinked) {
			if (treeViewer != null && !treeViewer.getControl().isDisposed()) {
				treeViewer.setSelection(new StructuredSelection(selection), true);
			}
		}
	}

	private void handleCollapseAll() {
		if (treeViewer != null && !treeViewer.getControl().isDisposed()) {
			treeViewer.collapseAll();
		}
	}
}
