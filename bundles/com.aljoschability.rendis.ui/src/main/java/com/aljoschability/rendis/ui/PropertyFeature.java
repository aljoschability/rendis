package com.aljoschability.rendis.ui;

import org.eclipse.emf.common.command.Command;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

public class PropertyFeature extends AbstractCustomFeature {
	public static final String COMMAND_KEY = "command"; //$NON-NLS-1$

	public PropertyFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public void execute(ICustomContext context) {
		PictogramElement pe = context.getPictogramElements()[0];

		Command command = (Command) context.getProperty(COMMAND_KEY);

		command.execute();

		updatePictogramElement(pe);
	}

	@Override
	public String getName() {
		return "Property Change";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		Object command = context.getProperty(COMMAND_KEY);
		int peCount = context.getPictogramElements().length;

		return peCount == 1 && command instanceof Command && ((Command) command).canExecute();
	}
}
