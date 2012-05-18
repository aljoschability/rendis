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
package com.aljoschability.rendis.ui.runtime;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;


/**
 * This interface provides access to important methods for a plug-in. Extend {@link ActivatorImpl} for a fully featured
 * implementation.
 * 
 * @see ActivatorImpl
 * @author Aljoscha Hark <aljoschability@gmail.com>
 */
public interface IActivator {
	/**
	 * Delivers the symbolic name/ID of this plug-in.
	 * 
	 * @return Returns the symbolic name of the plug-in.
	 */
	String getSymbolicName();

	/**
	 * Delivers the {@link PreferenceStore preference store} instance of this plug-in.
	 * 
	 * @return Returns the {@link IPreferenceStore} of this plug-in.
	 */
	IPreferenceStore getPreferenceStore();

	/**
	 * Delivers the {@link IDialogSettings dialog settings} instance of this plug-in.
	 * 
	 * @return Returns the {@link IDialogSettings} of this plug-in.
	 */
	IDialogSettings getDialogSettings();

	/**
	 * Delivers the {@link ImageDescriptor image descriptor} registered in the image registry of this plug-in.
	 * 
	 * @param key The key under which the image descriptor has been registered.
	 * @return Returns the {@link ImageDescriptor} registered under the key.
	 */
	ImageDescriptor getImageDescriptor(String key);

	/**
	 * Delivers the {@link Image image} registered in the image registry of this plug-in.
	 * 
	 * @param key The key under which the image has been registered.
	 * @return Returns the {@link Image} registered under the key.
	 */
	Image getImage(String key);

	/**
	 * Delivers the {@link Color color} registered under the given <code>key</code> in the color registry of this
	 * plug-in.
	 * 
	 * @param key The key under which the color has been registered.
	 * @return Returns the {@link Color} registered under the key.
	 */
	Color getColor(String key);

	/**
	 * Logs the given text as information in the {@link org.eclipse.core.runtime.ILog status log} for this plug-in.
	 * 
	 * @param text The text to log.
	 */
	void info(String text);

	/**
	 * Logs the given text as warning in the {@link org.eclipse.core.runtime.ILog status log} for this plug-in.
	 * 
	 * @param text The text to log.
	 */
	void warn(String text);

	/**
	 * Logs the given exception as warning in the {@link org.eclipse.core.runtime.ILog status log} for this plug-in.
	 * 
	 * @param cause The exception to log.
	 */
	void warn(Throwable cause);

	/**
	 * Logs the given text and the exception as warning in the {@link org.eclipse.core.runtime.ILog status log} for this
	 * plug-in.
	 * 
	 * @param text The text to log.
	 * @param cause The exception to log.
	 */
	void warn(String text, Throwable cause);

	/**
	 * Logs the given text as error in the {@link org.eclipse.core.runtime.ILog status log} for this plug-in.
	 * 
	 * @param text The text to log.
	 */
	void error(String text);

	/**
	 * Logs the given exception as error in the {@link org.eclipse.core.runtime.ILog status log} for this plug-in.
	 * 
	 * @param cause The exception to log.
	 */
	void error(Throwable cause);

	/**
	 * Logs the given text and the exception as error in the {@link org.eclipse.core.runtime.ILog status log} for this
	 * plug-in.
	 * 
	 * @param text The text to log.
	 * @param cause The exception to log.
	 */
	void error(String text, Throwable cause);

	/**
	 * Delivers the current active {@link Display display}.
	 * 
	 * @return Returns the display.
	 */
	Display getDisplay();
}
