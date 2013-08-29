package com.aljoschability.rendis.ui.editor.features;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IReconnectionContext;
import org.eclipse.graphiti.features.context.impl.ReconnectionContext;
import org.eclipse.graphiti.features.impl.DefaultReconnectionFeature;

import com.aljoschability.rendis.Channel;
import com.aljoschability.rendis.ChannelPort;

public class ChannelReconnectionFeature extends DefaultReconnectionFeature {
	public ChannelReconnectionFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canReconnect(IReconnectionContext context) {
		Object bo = getBusinessObjectForPictogramElement(context.getConnection());
		Object nbo = getBusinessObjectForPictogramElement(context.getNewAnchor());

		return bo instanceof Channel && nbo instanceof ChannelPort;
	}

	@Override
	public void postReconnect(IReconnectionContext context) {
		Channel bo = (Channel) getBusinessObjectForPictogramElement(context.getConnection());
		ChannelPort port = (ChannelPort) getBusinessObjectForPictogramElement(context.getNewAnchor());

		if (ReconnectionContext.RECONNECT_SOURCE.equals(context.getReconnectType())) {
			bo.setSource(port);
		} else if (ReconnectionContext.RECONNECT_TARGET.equals(context.getReconnectType())) {
			bo.setTarget(port);
		}
	}
}
