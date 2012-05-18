package com.aljoschability.rendis.ui.editor.features;

import org.eclipse.graphiti.features.IFeatureProvider;

import com.aljoschability.rendis.Channel;
import com.aljoschability.rendis.ChannelPort;
import com.aljoschability.rendis.Part;
import com.aljoschability.rendis.RendisFactory;
import com.aljoschability.rendis.features.AbstractPartCreateConnectionFeature;
import com.aljoschability.rendis.util.RendisUtil;

public class ChannelCreateFeature extends AbstractPartCreateConnectionFeature {
	public ChannelCreateFeature(IFeatureProvider fp) {
		super(fp, "Channel");
	}

	@Override
	public String getCreateName() {
		return "Channel";
	}

	@Override
	public String getCreateImageId() {
		return Channel.class.getCanonicalName();
	}

	@Override
	protected boolean canStart(Part source) {
		return source instanceof ChannelPort;
	}

	@Override
	protected boolean canCreateAt(Part target) {
		return target instanceof ChannelPort;
	}

	@Override
	protected Part create(Part source, Part target) {
		ChannelPort sbo = (ChannelPort) source;
		ChannelPort tbo = (ChannelPort) target;

		Channel bo = RendisFactory.eINSTANCE.createChannel();
		bo.setSource(sbo);
		bo.setTarget(tbo);

		bo.setResidence(RendisUtil.getResidence(sbo));

		return bo;
	}
}
