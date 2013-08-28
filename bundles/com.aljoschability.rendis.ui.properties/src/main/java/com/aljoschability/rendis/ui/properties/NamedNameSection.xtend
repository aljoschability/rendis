package com.aljoschability.rendis.ui.properties;

import com.aljoschability.eclipse.core.properties.graphiti.GraphitiElementAdapter
import com.aljoschability.eclipse.core.ui.properties.sections.AbstractTextSection
import com.aljoschability.rendis.RendisPackage

class NamedNameSection extends AbstractTextSection {
	new() {
		super(GraphitiElementAdapter::get())
	}

	override protected getFeature() {
		return RendisPackage.Literals::NAMED__NAME
	}

	override protected getLabelText() {
		return "Name"
	}
}
