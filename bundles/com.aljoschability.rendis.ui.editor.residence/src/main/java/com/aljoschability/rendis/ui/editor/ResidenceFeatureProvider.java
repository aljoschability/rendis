package com.aljoschability.rendis.ui.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.IMoveAnchorFeature;
import org.eclipse.graphiti.features.IReconnectionFeature;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.IMoveAnchorContext;
import org.eclipse.graphiti.features.context.IReconnectionContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

import com.aljoschability.rendis.Channel;
import com.aljoschability.rendis.ChannelPort;
import com.aljoschability.rendis.Cubicle;
import com.aljoschability.rendis.Floor;
import com.aljoschability.rendis.ui.AbstractRendisFeatureProvider;
import com.aljoschability.rendis.ui.editor.features.ChannelReconnectionFeature;
import com.aljoschability.rendis.ui.editor.patterns.BoundingBoxMoveAnchorFeature;
import com.aljoschability.rendis.ui.editor.patterns.BuildingPattern;
import com.aljoschability.rendis.ui.editor.patterns.ChannelPattern;
import com.aljoschability.rendis.ui.editor.patterns.CubiclePattern;
import com.aljoschability.rendis.ui.editor.patterns.FloorPattern;
import com.aljoschability.rendis.ui.editor.patterns.PortPattern;
import com.aljoschability.rendis.ui.editor.patterns.RoomPattern;
import com.aljoschability.rendis.ui.editor.patterns.SupplierPattern;

public class ResidenceFeatureProvider extends AbstractRendisFeatureProvider {
	public ResidenceFeatureProvider(IDiagramTypeProvider dtp) {
		super(dtp);

		addPattern(new BuildingPattern());
		addPattern(new SupplierPattern());
		addPattern(new FloorPattern());
		addPattern(new RoomPattern());
		addPattern(new PortPattern());
		addPattern(new CubiclePattern());

		addConnectionPattern(new ChannelPattern());
	}

	@Override
	public IReconnectionFeature getReconnectionFeature(IReconnectionContext context) {
		Connection pe = context.getConnection();
		Object bo = getBusinessObjectForPictogramElement(pe);

		if (bo instanceof Channel) {
			return new ChannelReconnectionFeature(this);
		}

		return super.getReconnectionFeature(context);
	}

	@Override
	public IMoveAnchorFeature getMoveAnchorFeature(IMoveAnchorContext context) {
		Anchor pe = context.getAnchor();
		Object bo = getBusinessObjectForPictogramElement(pe);

		if (bo instanceof ChannelPort) {
			return new BoundingBoxMoveAnchorFeature(this);
		}

		return super.getMoveAnchorFeature(context);
	}

	@Override
	public ICustomFeature[] getCustomFeatures(ICustomContext context) {
		List<ICustomFeature> features = new ArrayList<ICustomFeature>();

		PictogramElement[] pes = context.getPictogramElements();
		if (pes.length == 1) {
			PictogramElement pe = pes[0];
			Object bo = getBusinessObjectForPictogramElement(pe);

			if (bo instanceof Floor) {
				features.add(new OpenFloorDiagramFeature(this));
			} else if (bo instanceof Cubicle) {
				features.add(new OpenCubicleDiagramFeature(this));
			}
		}

		return features.toArray(new ICustomFeature[features.size()]);
	}
}
