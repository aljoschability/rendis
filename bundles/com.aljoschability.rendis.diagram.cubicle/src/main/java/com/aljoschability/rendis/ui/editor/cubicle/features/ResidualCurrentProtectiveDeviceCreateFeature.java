package com.aljoschability.rendis.ui.editor.cubicle.features;

import java.text.MessageFormat;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.graphiti.features.IFeatureProvider;

import com.aljoschability.rendis.Cubicle;
import com.aljoschability.rendis.Messages;
import com.aljoschability.rendis.Part;
import com.aljoschability.rendis.RendisFactory;
import com.aljoschability.rendis.ResidualCurrentProtectiveDevice;
import com.aljoschability.rendis.WirePort;
import com.aljoschability.rendis.features.AbstractPartCreateFeature;

public class ResidualCurrentProtectiveDeviceCreateFeature extends AbstractPartCreateFeature {
	private final int numberOfWires;

	public ResidualCurrentProtectiveDeviceCreateFeature(IFeatureProvider fp, int numberOfWires) {
		super(fp, null);

		this.numberOfWires = numberOfWires;
	}

	@Override
	public String getCreateName() {
		String pattern = Messages.ResidualCurrentProtectiveDevice_CreateMulti;
		if (numberOfWires == 1) {
			pattern = Messages.ResidualCurrentProtectiveDevice_CreateSingle;
		}
		return MessageFormat.format(pattern, numberOfWires);
	}

	@Override
	protected boolean canCreate(Part container) {
		return container instanceof Cubicle;
	}

	@Override
	protected Part doCreate(Part cbo) {
		ResidualCurrentProtectiveDevice bo = RendisFactory.eINSTANCE.createResidualCurrentProtectiveDevice();

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

}
