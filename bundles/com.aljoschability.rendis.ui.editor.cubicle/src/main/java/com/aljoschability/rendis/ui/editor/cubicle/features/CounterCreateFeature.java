package com.aljoschability.rendis.ui.editor.cubicle.features;

import java.text.MessageFormat;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.graphiti.features.IFeatureProvider;

import com.aljoschability.rendis.Counter;
import com.aljoschability.rendis.Cubicle;
import com.aljoschability.rendis.Messages;
import com.aljoschability.rendis.Part;
import com.aljoschability.rendis.RendisFactory;
import com.aljoschability.rendis.WirePort;
import com.aljoschability.rendis.features.AbstractPartCreateFeature;

public class CounterCreateFeature extends AbstractPartCreateFeature {
	private final int numberOfWires;

	public CounterCreateFeature(IFeatureProvider fp, int numberOfWires) {
		super(fp, null);

		this.numberOfWires = numberOfWires;
	}

	@Override
	protected boolean canCreate(Part container) {
		return container instanceof Cubicle;
	}

	@Override
	protected Part doCreate(Part cbo) {
		Counter bo = RendisFactory.eINSTANCE.createCounter();

		for (int i = 0; i < numberOfWires; i++) {
			WirePort incoming = RendisFactory.eINSTANCE.createWirePort();
			incoming.setId(EcoreUtil.generateUUID());
			bo.getIncomings().add(incoming);

			WirePort outgoing = RendisFactory.eINSTANCE.createWirePort();
			outgoing.setId(EcoreUtil.generateUUID());
			bo.getOutgoings().add(outgoing);
		}

		bo.setCubicle((Cubicle) cbo);

		return bo;
	}

	@Override
	public String getCreateName() {
		String pattern = Messages.Counter_CreateMulti;
		if (numberOfWires == 1) {
			pattern = Messages.Counter_CreateSingle;
		}
		return MessageFormat.format(pattern, numberOfWires);
	}
}
