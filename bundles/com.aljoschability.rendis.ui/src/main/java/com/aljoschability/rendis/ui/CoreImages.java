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

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

/**
 * This provides access to core images.
 * 
 * @author Aljoscha Hark <aljoschability@gmail.com>
 */
public final class CoreImages {
	public static String ADD = "icons/add.png"; //$NON-NLS-1$
	public static String REMOVE = "icons/remove.png"; //$NON-NLS-1$
	public static String CONFIGURE = "icons/configure.png"; //$NON-NLS-1$
	public static String FIND = "icons/find.png"; //$NON-NLS-1$
	public static String MOVE_UP = "icons/move-up.png"; //$NON-NLS-1$
	public static String MOVE_DOWN = "icons/move-down.png"; //$NON-NLS-1$

	public static String OUTLINE_THUMBNAIL = "icons/editor/overview.png"; //$NON-NLS-1$
	public static String OUTLINE_TREE = "icons/editor/outline.png"; //$NON-NLS-1$
	public static String COLLAPSE_ALL = "icons/editor/collapseall.png"; //$NON-NLS-1$
	public static String LINKING = "icons/editor/synced.png"; //$NON-NLS-1$

	public static String QUESTION = "icons/state/question.png"; //$NON-NLS-1$
	public static String INFORMATION = "icons/state/information.png"; //$NON-NLS-1$
	public static String WARNING = "icons/state/warning.png"; //$NON-NLS-1$
	public static String ERROR = "icons/state/error.png"; //$NON-NLS-1$

	private static Collection<String> all;

	public CoreImages() {
		// hide constructor
	}

	public static Collection<String> getPaths() {
		if (all == null) {
			all = new ArrayList<String>();

			all.add(ADD);
			all.add(REMOVE);
			all.add(CONFIGURE);
			all.add(FIND);
			all.add(MOVE_UP);
			all.add(MOVE_DOWN);

			all.add(COLLAPSE_ALL);
			all.add(LINKING);
			all.add(OUTLINE_TREE);
			all.add(OUTLINE_THUMBNAIL);

			all.add(QUESTION);
			all.add(INFORMATION);
			all.add(WARNING);
			all.add(ERROR);
		}
		return all;
	}

	public static Image get(String key) {
		return Activator.get().getImage(key);
	}

	public static ImageDescriptor getDescriptor(String key) {
		return Activator.get().getImageDescriptor(key);
	}
}
