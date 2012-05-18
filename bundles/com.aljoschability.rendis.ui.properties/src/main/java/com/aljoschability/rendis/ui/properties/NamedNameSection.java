package com.aljoschability.rendis.ui.properties;

import org.eclipse.emf.ecore.EStructuralFeature;

import com.aljoschability.rendis.RendisPackage;

public class NamedNameSection extends AbstractStringTextSection {
	@Override
	protected EStructuralFeature getFeature() {
		return RendisPackage.Literals.NAMED__NAME;
	}
	@Override
	protected void postExecute() {
//		getPE();
		// TODO Auto-generated method stub
		super.postExecute();
	}

	@Override
	protected String getLabelText() {
		return "Name";
	}
}
