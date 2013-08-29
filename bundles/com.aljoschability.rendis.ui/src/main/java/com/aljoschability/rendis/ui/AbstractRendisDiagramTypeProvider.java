package com.aljoschability.rendis.ui;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.graphiti.dt.AbstractDiagramTypeProvider;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.tb.DefaultToolBehaviorProvider;
import org.eclipse.graphiti.tb.IToolBehaviorProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.aljoschability.rendis.ui.editors.RendisDiagramEditor;

public abstract class AbstractRendisDiagramTypeProvider extends AbstractDiagramTypeProvider {
	private IToolBehaviorProvider[] tbps;

	public AbstractRendisDiagramTypeProvider() {
		setFeatureProvider(createFeatureProvider());
	}

	protected abstract IFeatureProvider createFeatureProvider();

	@Override
	public IToolBehaviorProvider[] getAvailableToolBehaviorProviders() {
		if (tbps == null) {
			tbps = new IToolBehaviorProvider[] { createToolBehaviorProvider() };
		}
		return tbps;
	}

	protected IToolBehaviorProvider createToolBehaviorProvider() {
		return new DefaultToolBehaviorProvider(this);
	}

	public ILabelProvider getOutlinePageLabelProvider() {
		return new AdapterFactoryLabelProvider(getAdapterFactory());
	}

	public ITreeContentProvider getOutlinePageContentProvider() {
		return new AdapterFactoryContentProvider(getAdapterFactory());
	}

	private AdapterFactory getAdapterFactory() {
		return getDiagramEditor().getAdapterFactory();
	}

	@Override
	public RendisDiagramEditor getDiagramEditor() {
		return (RendisDiagramEditor) super.getDiagramBehavior();
	}
}
