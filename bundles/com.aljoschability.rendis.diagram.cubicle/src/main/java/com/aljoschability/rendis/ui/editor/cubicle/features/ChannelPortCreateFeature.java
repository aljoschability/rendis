package com.aljoschability.rendis.ui.editor.cubicle.features;

import org.eclipse.graphiti.features.IFeatureProvider;

import com.aljoschability.rendis.ChannelNode;
import com.aljoschability.rendis.ChannelPort;
import com.aljoschability.rendis.Messages;
import com.aljoschability.rendis.Part;
import com.aljoschability.rendis.RendisFactory;
import com.aljoschability.rendis.features.AbstractPartCreateFeature;

public class ChannelPortCreateFeature extends AbstractPartCreateFeature {
	public ChannelPortCreateFeature(IFeatureProvider fp) {
		super(fp, Messages.ChannelPort_Create);
	}

	@Override
	protected boolean canCreate(Part container) {
		return container instanceof ChannelNode;
	}

	@Override
	protected Part doCreate(Part container) {
		ChannelPort bo = RendisFactory.eINSTANCE.createChannelPort();

		bo.setNode((ChannelNode) container);

		return bo;
	}
}
