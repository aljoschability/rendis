package com.aljoschability.rendis.ui.editor;

import org.eclipse.graphiti.ui.platform.AbstractImageProvider;

public class CubicleImageProvider extends AbstractImageProvider {
	public static final String LOGO_ABB = "icons/logos/abb.png"; //$NON-NLS-1$

	//	public static final String TEXT = "text"; //$NON-NLS-1$

	@Override
	protected void addAvailableImages() {
		addImageFilePath(LOGO_ABB, LOGO_ABB);
	}
}
