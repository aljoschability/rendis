package com.aljoschability.rendis.ui.editor.cubicle.features;

import java.text.MessageFormat;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IFeatureProvider;

import com.aljoschability.rendis.Channel;
import com.aljoschability.rendis.Cubicle;
import com.aljoschability.rendis.Messages;
import com.aljoschability.rendis.Part;
import com.aljoschability.rendis.RendisFactory;
import com.aljoschability.rendis.Wire;
import com.aljoschability.rendis.WirePort;
import com.aljoschability.rendis.features.AbstractPartCreateConnectionFeature;

public class WireCreateFeature extends AbstractPartCreateConnectionFeature {
	private final double crossSection;

	public WireCreateFeature(IFeatureProvider fp, double crossSection) {
		super(fp, null);
		this.crossSection = crossSection;
	}

	@Override
	public String getCreateName() {
		String pattern = Messages.Wire_Create;
		return MessageFormat.format(pattern, crossSection);
	}

	@Override
	public String getCreateImageId() {
		return Wire.class.getCanonicalName();
	}

	@Override
	protected boolean canCreateAt(Part target) {
		return target instanceof WirePort;
	}

	@Override
	protected boolean canStart(Part source) {
		return source instanceof WirePort;
	}

	@Override
	protected Part create(Part source, Part target) {
		WirePort sbo = (WirePort) source;
		WirePort tbo = (WirePort) target;

		Wire bo = RendisFactory.eINSTANCE.createWire();
		bo.setCrossSection(crossSection);
		bo.setSource(sbo);
		sbo.getWires().add(bo);
		bo.setTarget(tbo);
		tbo.getWires().add(bo);

		Cubicle cubicle = getCubicle(sbo, tbo);
		Channel channel = getChannel(sbo, tbo);
		if (cubicle != null) {
			bo.setCubicle(cubicle);
		} else {
			bo.setChannel(channel);
		}

		return bo;
	}

	private Channel getChannel(WirePort source, WirePort target) {
		EObject container = source;
		while (container != null) {
			if (container instanceof Channel) {
				return (Channel) container;
			}
			container = container.eContainer();
		}
		return null;
	}

	private Cubicle getCubicle(WirePort source, WirePort target) {
		EObject container = source;
		while (container != null) {
			if (container instanceof Cubicle) {
				return (Cubicle) container;
			}
			container = container.eContainer();
		}
		return null;
	}
}
