package com.aljoschability.rendis.ui;

import com.aljoschability.rendis.ui.runtime.ActivatorImpl;
import com.aljoschability.rendis.ui.runtime.IActivator;

public class Activator extends ActivatorImpl {
	private static IActivator instance;

	public static IActivator get() {
		return instance;
	}

	@Override
	protected void dispose() {
		Activator.instance = null;
	}

	@Override
	protected void initialize() {
		Activator.instance = this;

		// images
		for (String key : CoreImages.getPaths()) {
			addImage(key);
		}

		// colors
		for (String key : CoreColors.getMap().keySet()) {
			addColor(key, CoreColors.getMap().get(key));
		}
	}
}
