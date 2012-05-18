package com.aljoschability.rendis.ui.properties;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public abstract class AbstractStringTextSection extends AbstractFeaturePropertySection<String> {
	private Label label;
	private Text text;
	private Label icon;

	@Override
	public void refresh() {
		if (isReady() && hasChanged()) {
			String value = getValue();
			if (value != null) {
				text.setText(value);
			}
		}
	}

	private boolean isReady() {
		return text != null && !text.isDisposed();
	}

	private boolean hasChanged() {
		String oldText = text.getText();
		String newText = getValue();

		if (oldText.equals(newText)) {
			return false;
		}

		if (newText != null && newText.equals(oldText)) {
			return false;
		}

		if (newText == null && EMPTY.equals(oldText)) {
			return false;
		}

		return true;
	}

	@Override
	protected void createWidgets(Composite parent, TabbedPropertySheetWidgetFactory factory) {
		String labelText = getLabelText() + ':';
		label = factory.createLabel(parent, labelText, SWT.TRAIL);

		text = factory.createText(parent, EMPTY, SWT.BORDER | SWT.SINGLE);

		icon = factory.createLabel(parent, EMPTY);
	}

	protected abstract String getLabelText();

	@Override
	protected void hookWidgetListeners() {
		// select text on label click
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				text.setFocus();
				text.selectAll();
			}
		});

		// execute command when focus lost
		text.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				doExecute();
			}
		});

		// execute command on [ENTER] key
		text.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (SWT.CR == e.character) {
					doExecute();
				}
			}
		});

		// validate on modify
		text.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				String value = getValue(text.getText());
				IStatus state = validate(value);

				decorate(state, text, icon);
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
		text.setLayoutData(data);

		// help
		data = new FormData();
		data.left = new FormAttachment(text, MARGIN * 2, SWT.RIGHT);
		data.right = new FormAttachment(100, -MARGIN);
		data.top = new FormAttachment(text, 0, SWT.TOP);
		data.bottom = new FormAttachment(text, 0, SWT.BOTTOM);
		icon.setLayoutData(data);

		// label
		data = new FormData();
		data.left = new FormAttachment(0);
		data.right = new FormAttachment(text, -MARGIN);
		data.top = new FormAttachment(text, 2, SWT.TOP);
		data.bottom = new FormAttachment(text, 0, SWT.BOTTOM);
		label.setLayoutData(data);
	}

	private void doExecute() {
		String value = getValue(text.getText());
		IStatus state = validate(value);

		if (state == null || state.getSeverity() < IStatus.ERROR) {
			boolean abort = false;
			Object oldValue = getValue();
			if (oldValue != null) {
				abort = oldValue.equals(value);
			}
			if (!abort && value != null) {
				abort = value.equals(oldValue);
			}

			if (!abort) {
				Point selection = text.getSelection();
				set(value);
				text.setSelection(selection);
			}
		}
		decorate(state, text, icon);
	}

	protected String getValue(String value) {
		return value;
	}

	protected IStatus validate(String value) {
		return null;
	}
}
