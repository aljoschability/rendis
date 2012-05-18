package com.aljoschability.rendis.ui.editor;

import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.IMoveAnchorFeature;
import org.eclipse.graphiti.features.IReconnectionFeature;
import org.eclipse.graphiti.features.IUpdateFeature;
import org.eclipse.graphiti.features.context.IMoveAnchorContext;
import org.eclipse.graphiti.features.context.IReconnectionContext;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

import com.aljoschability.rendis.Wire;
import com.aljoschability.rendis.WirePort;
import com.aljoschability.rendis.features.NoMoveAnchorFeature;
import com.aljoschability.rendis.ui.AbstractRendisFeatureProvider;
import com.aljoschability.rendis.ui.editor.cubicle.ChannelPortPattern;
import com.aljoschability.rendis.ui.editor.cubicle.CircuitBreakerPattern;
import com.aljoschability.rendis.ui.editor.cubicle.CounterPattern;
import com.aljoschability.rendis.ui.editor.cubicle.ResidualCurrentProtectiveDevicePattern;
import com.aljoschability.rendis.ui.editor.cubicle.StairwaySwitchTimerPattern;
import com.aljoschability.rendis.ui.editor.cubicle.TransformatorPattern;
import com.aljoschability.rendis.ui.editor.cubicle.WirePattern;
import com.aljoschability.rendis.ui.editor.cubicle.features.WireReconnectionFeature;
import com.aljoschability.rendis.ui.editor.cubicle.features.WireUpdateFeature;

public class CubicleFeatureProvider extends AbstractRendisFeatureProvider {
	public CubicleFeatureProvider(IDiagramTypeProvider dtp) {
		super(dtp);

		addConnectionPattern(new WirePattern());

		addPattern(new CircuitBreakerPattern());
		addPattern(new ChannelPortPattern());
		addPattern(new CounterPattern());
		addPattern(new ResidualCurrentProtectiveDevicePattern());
		addPattern(new StairwaySwitchTimerPattern());
		addPattern(new TransformatorPattern());
	}

	@Override
	public IMoveAnchorFeature getMoveAnchorFeature(IMoveAnchorContext context) {
		Anchor pe = context.getAnchor();
		Object bo = getBusinessObjectForPictogramElement(pe);

		if (bo instanceof WirePort) {
			return new NoMoveAnchorFeature(this);
		}

		return super.getMoveAnchorFeature(context);
	}

	@Override
	public IReconnectionFeature getReconnectionFeature(IReconnectionContext context) {
		Connection pe = context.getConnection();
		Object bo = getBusinessObjectForPictogramElement(pe);

		if (bo instanceof Wire) {
			return new WireReconnectionFeature(this);
		}

		return super.getReconnectionFeature(context);
	}

	@Override
	public IUpdateFeature getUpdateFeature(IUpdateContext context) {
		PictogramElement pe = context.getPictogramElement();
		Object bo = getBusinessObjectForPictogramElement(pe);

		if (bo instanceof Wire) {
			return new WireUpdateFeature(this);
		}

		return super.getUpdateFeature(context);
	}
}
