package com.aljoschability.rendis.util;

import org.eclipse.emf.ecore.EObject;

import com.aljoschability.rendis.Part;
import com.aljoschability.rendis.Residence;

public final class RendisUtil {
	public static Residence getResidence(Part bo) {
		EObject container = bo;
		while (container != null) {
			if (container instanceof Residence) {
				return (Residence) container;
			}
			container = container.eContainer();
		}
		return null;
	}
}
