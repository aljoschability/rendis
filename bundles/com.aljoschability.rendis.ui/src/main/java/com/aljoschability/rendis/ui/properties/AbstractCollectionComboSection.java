package com.aljoschability.rendis.ui.properties;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import com.aljoschability.rendis.ui.CoreImages;

public abstract class AbstractCollectionComboSection<T> extends AbstractFeaturePropertySection<T> {
	private final Map<Integer, T> map;

	private Label label;
	private Composite composite;
	private Combo combo;
	private Button button;
	private Label icon;

	public AbstractCollectionComboSection() {
		map = new LinkedHashMap<Integer, T>();
	}

	@Override
	public void refresh() {
		if (isReady()) {
			map.clear();
			int index = 0;
			List<T> items = getItems();
			String[] itemTexts = new String[items.size()];
			for (T object : items) {
				map.put(index, object);
				itemTexts[index] = getText(object);
				index++;
			}
			combo.setItems(itemTexts);

			if (hasChanged()) {
				Object value = getValue();
				IStatus state = validate(value);
				if (getBO() != null) {
					combo.select(getIndex(value));
				} else {
					combo.select(-1);
				}

				decorate(state, combo, icon);
			}
		}
	}

	@Override
	protected void createWidgets(Composite parent, TabbedPropertySheetWidgetFactory factory) {
		label = factory.createLabel(parent, getLabelText() + ':', SWT.TRAIL);

		composite = factory.createFlatFormComposite(parent);

		combo = new Combo(composite, SWT.BORDER | SWT.READ_ONLY);
		factory.adapt(combo);

		if (shouldShowButton()) {
			button = factory.createButton(composite, "Find", SWT.PUSH);
			button.setImage(CoreImages.get(CoreImages.FIND));
		}

		icon = factory.createLabel(parent, EMPTY);
		icon.setImage(CoreImages.get(CoreImages.QUESTION));
	}

	@Override
	protected abstract EStructuralFeature getFeature();

	protected abstract List<T> getItems();

	protected abstract String getLabelText();

	protected String getText(T element) {
		return String.valueOf(element);
	}

	protected void handleButtonClicked() {
		// nothing by default
	}

	@Override
	protected void hookWidgetListeners() {
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				T value = map.get(combo.getSelectionIndex());
				IStatus state = validate(value);
				if (state == null || state.isOK()) {
					set(value);
				}
				decorate(state, combo, icon);
			}
		});

		if (shouldShowButton()) {
			button.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					handleButtonClicked();
				}
			});
		}
	}

	@Override
	protected void layoutWidgets() {
		GridLayoutFactory.fillDefaults().numColumns(shouldShowButton() ? 2 : 1).applyTo(composite);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(combo);
		if (shouldShowButton()) {
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).applyTo(button);
		}

		// control
		FormData data = new FormData();
		data.left = new FormAttachment(0, LABEL_WIDTH);
		data.right = new FormAttachment(100, -(16 + MARGIN * 3));
		data.top = new FormAttachment(0);
		data.bottom = new FormAttachment(100);
		composite.setLayoutData(data);

		// help
		data = new FormData();
		data.left = new FormAttachment(composite, MARGIN * 2, SWT.RIGHT);
		data.right = new FormAttachment(100, -MARGIN);
		data.top = new FormAttachment(composite, 0, SWT.TOP);
		data.bottom = new FormAttachment(composite, 0, SWT.BOTTOM);
		icon.setLayoutData(data);

		// label
		data = new FormData();
		data.left = new FormAttachment(0);
		data.right = new FormAttachment(composite, -MARGIN);
		data.top = new FormAttachment(composite, 2, SWT.TOP);
		data.bottom = new FormAttachment(composite, 0, SWT.BOTTOM);
		label.setLayoutData(data);
	}

	protected boolean shouldShowButton() {
		return false;
	}

	protected IStatus validate(Object value) {
		return null;
	}

	private int getIndex(Object element) {
		boolean isNull = element == null;
		for (Integer key : map.keySet()) {
			T value = map.get(key);
			if (isNull) {
				if (value == null) {
					return key;
				}
			} else {
				if (element.equals(value)) {
					return key;
				}
			}
		}
		return -1;
	}

	private boolean hasChanged() {
		T oldValue = map.get(combo.getSelectionIndex());
		T newValue = getValue();

		if (oldValue != null && oldValue.equals(newValue)) {
			return false;
		}

		if (newValue != null && newValue.equals(oldValue)) {
			return false;
		}

		if (newValue == null && oldValue == null) {
			return false;
		}

		return true;
	}

	private boolean isReady() {
		return combo != null && !combo.isDisposed();
	}
}
