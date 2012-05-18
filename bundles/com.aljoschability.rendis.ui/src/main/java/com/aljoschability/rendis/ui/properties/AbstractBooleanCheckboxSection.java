package com.aljoschability.rendis.ui.properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import com.aljoschability.rendis.ui.CoreImages;

public abstract class AbstractBooleanCheckboxSection extends AbstractFeaturePropertySection<Boolean> {
	private Button button;
	private Label icon;

	@Override
	public void refresh() {
		if (isReady() && hasChanged()) {
			button.setSelection(getValue());
		}
	}

	private boolean isReady() {
		return button != null && !button.isDisposed();
	}

	private boolean hasChanged() {
		boolean oldValue = button.getSelection();
		Boolean newValue = getValue();

		if (newValue == null) {
			return false;
		}

		return !newValue.equals(oldValue);
	}

	@Override
	protected void createWidgets(Composite parent, TabbedPropertySheetWidgetFactory factory) {
		button = factory.createButton(parent, getLabelText(), SWT.CHECK);

		icon = factory.createLabel(parent, EMPTY);
		icon.setImage(CoreImages.get(CoreImages.QUESTION));
		icon.setToolTipText(getHelpText());
	}

	protected abstract String getLabelText();

	protected String getHelpText() {
		return null;
	}

	@Override
	protected void hookWidgetListeners() {
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				set(button.getSelection());
			}
		});
	}

	@Override
	protected void layoutWidgets() {
		// control
		FormData data = new FormData();
		data.left = new FormAttachment(0, LABEL_WIDTH);
		data.right = new FormAttachment(100, -(16 + MARGIN * 3));
		data.top = new FormAttachment(0);
		data.bottom = new FormAttachment(100);
		button.setLayoutData(data);

		// help
		data = new FormData();
		data.left = new FormAttachment(button, MARGIN * 2, SWT.RIGHT);
		data.right = new FormAttachment(100, -MARGIN);
		data.top = new FormAttachment(button, 0, SWT.TOP);
		data.bottom = new FormAttachment(button, 0, SWT.BOTTOM);
		icon.setLayoutData(data);
	}
}
