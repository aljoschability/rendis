/**
 * <copyright>
 *  Copyright 2012 by Aljoschability and others. All rights reserved. This program and its materials are made available
 *  under the terms of the Eclipse Public License v1.0 which is referenced in this distribution.
 * 
 * 	Contributors:
 * 		Aljoscha Hark <aljoschability@gmail.com> - Initial code
 * 
 * </copyright>
 */
package com.aljoschability.rendis.ui;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

import com.aljoschability.rendis.ui.runtime.IActivator;

/**
 * This provides access to core colors.
 * 
 * @author Aljoscha Hark <aljoschability@gmail.com>
 */
public final class CoreColors {
	public static final String EMPTY = "empty"; //$NON-NLS-1$
	public static final String WARNING = "warning"; //$NON-NLS-1$
	public static final String ERROR = "error"; //$NON-NLS-1$

	private static Map<String, RGB> map;

	/**
	 * Delivers the map containing all the keyed {@link RGB color values} for this plug-in.
	 * 
	 * @return Returns the map containing all the {@link RGB color values} and their key.
	 * @nouse This method should only be used by the {@link IActivator activator} of the defining plug-in.
	 */
	public static Map<String, RGB> getMap() {
		if (map == null) {
			map = new LinkedHashMap<String, RGB>();

			map.put(EMPTY, new RGB(255, 255, 255));
			map.put(WARNING, new RGB(244, 242, 193));
			map.put(ERROR, new RGB(205, 172, 172));
		}
		return map;
	}

	/**
	 * Delivers the color that have been registered under the given key.
	 * 
	 * @param key
	 *            The key of the color to deliver.
	 * @return Returns the {@link Color} for the key.
	 */
	public static Color get(String key) {
		return Activator.get().getColor(key);
	}
}
