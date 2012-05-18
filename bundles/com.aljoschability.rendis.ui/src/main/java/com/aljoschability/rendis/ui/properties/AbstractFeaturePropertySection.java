package com.aljoschability.rendis.ui.properties;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * This is the abstract base class for all {@link org.eclipse.emf.ecore.EStructuralFeature feature} based sections.
 * 
 * @author Aljoscha Hark
 * 
 * @param <T> The {@link org.eclipse.emf.ecore.ETypedElement#getEType() EClassifier} of the
 *            {@link org.eclipse.emf.ecore.EStructuralFeature EStructuralFeature} for this section.
 */
public abstract class AbstractFeaturePropertySection<T> extends AbstractPropertySection {
	protected void add(Collection<? extends T> values) {
		add(getFeature(), values);
	}

	protected void add(T value) {
		add(getFeature(), value);
	}

	protected abstract EStructuralFeature getFeature();

	@SuppressWarnings("unchecked")
	protected T getValue() {
		return (T) getValue(getFeature());
	}

	protected void remove(Collection<? extends T> values) {
		remove(getFeature(), values);
	}

	protected void remove(Object value) {
		remove(getFeature(), value);
	}

	protected void set(T value) {
		set(getFeature(), value);
	}

	@Override
	protected boolean shouldRefresh(Notification msg) {
		return super.shouldRefresh(msg) && getBO().equals(msg.getNotifier()) && getFeature() != null
				&& getFeature().equals(msg.getFeature());
	}
}
