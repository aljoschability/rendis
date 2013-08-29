package com.aljoschability.rendis.ui.editor.cubicle.features;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IReconnectionContext;
import org.eclipse.graphiti.features.context.impl.ReconnectionContext;
import org.eclipse.graphiti.features.impl.DefaultReconnectionFeature;

import com.aljoschability.rendis.Wire;
import com.aljoschability.rendis.WirePort;

public class WireReconnectionFeature extends DefaultReconnectionFeature {
	public WireReconnectionFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canReconnect(IReconnectionContext context) {
		Object bo = getBusinessObjectForPictogramElement(context.getConnection());
		Object nbo = getBusinessObjectForPictogramElement(context.getNewAnchor());

		return bo instanceof Wire && nbo instanceof WirePort;
	}

	@Override
	public void postReconnect(IReconnectionContext context) {
		Wire bo = (Wire) getBusinessObjectForPictogramElement(context.getConnection());
		WirePort port = (WirePort) getBusinessObjectForPictogramElement(context.getNewAnchor());

		if (ReconnectionContext.RECONNECT_SOURCE.equals(context.getReconnectType())) {
			bo.setSource(port);
		} else if (ReconnectionContext.RECONNECT_TARGET.equals(context.getReconnectType())) {
			bo.setTarget(port);
		}
	}
}
