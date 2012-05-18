package com.aljoschability.rendis.ui.runtime;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;

public abstract class ImagesImpl {
	protected static final String CONTAINER = "icons"; //$NON-NLS-1$
	protected static final String EXTENSION = "png"; //$NON-NLS-1$

	protected static void initialize(Map<EClass, String> map, EPackage element) {
		initialize(map, element, null);
	}

	private static void initialize(Map<EClass, String> map, EPackage element, String container) {
		initialize(map, element.getEClassifiers(), container);

		// sub packages
		for (EPackage child : element.getESubpackages()) {
			initialize(map, child, child.getName());
		}
	}

	private static void initialize(Map<EClass, String> map, Collection<EClassifier> elements, String container) {
		for (EClassifier element : elements) {
			if (element instanceof EClass && !((EClass) element).isAbstract()) {
				initialize(map, (EClass) element, container);
			}
		}
	}

	private static void initialize(Map<EClass, String> map, EClass element, String container) {
		map.put(element, getPath(element, container));
	}

	private static String getPath(EClass element, String container) {
		StringBuilder path = new StringBuilder();

		// main folder
		path.append(CONTAINER);
		path.append('/');

		// sub-folder
		if (container != null) {
			path.append(container);
			path.append('/');
		}

		path.append(element.getName());

		path.append('.');
		path.append(EXTENSION);

		return path.toString();
	}
}
