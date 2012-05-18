package com.aljoschability.rendis.ui.properties.providers;

import java.text.MessageFormat;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory.Descriptor.Registry;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.aljoschability.rendis.ui.util.RendisDiagramAdaptor;

public class PropertySheetLabelProvider extends LabelProvider {
	private AdapterFactoryLabelProvider aflp;

	public PropertySheetLabelProvider() {
		AdapterFactory adapterFactory = new ComposedAdapterFactory(Registry.INSTANCE);
		aflp = new AdapterFactoryLabelProvider(adapterFactory);
	}

	@Override
	public void dispose() {
		AdapterFactory adapterFactory = aflp.getAdapterFactory();
		if (adapterFactory instanceof IDisposable) {
			((IDisposable) adapterFactory).dispose();
		}
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof IStructuredSelection) {
			if (((IStructuredSelection) element).size() == 1) {
				return aflp.getImage(RendisDiagramAdaptor.get(element));
			}
		}
		return aflp.getImage(element);
	}

	@Override
	public String getText(Object element) {
		if (element instanceof IStructuredSelection) {
			int count = ((IStructuredSelection) element).size();
			if (count == 1) {
				return aflp.getText(RendisDiagramAdaptor.get(element));
			} else {
				String pattern = "{0} elements selected";
				return MessageFormat.format(pattern, count);
			}
		}
		return aflp.getText(element);
	}
}
