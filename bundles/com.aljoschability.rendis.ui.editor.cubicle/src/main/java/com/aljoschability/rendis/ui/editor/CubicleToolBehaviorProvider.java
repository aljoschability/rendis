package com.aljoschability.rendis.ui.editor;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.ICreateConnectionFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.palette.IPaletteCompartmentEntry;
import org.eclipse.graphiti.palette.impl.PaletteCompartmentEntry;

import com.aljoschability.rendis.CircuitBreaker;
import com.aljoschability.rendis.Part;
import com.aljoschability.rendis.Wire;
import com.aljoschability.rendis.ui.AbstractRendisToolBehaviorProvider;
import com.aljoschability.rendis.ui.editor.cubicle.features.ChannelPortCreateFeature;
import com.aljoschability.rendis.ui.editor.cubicle.features.CircuitBreakerCreateFeature;
import com.aljoschability.rendis.ui.editor.cubicle.features.CounterCreateFeature;
import com.aljoschability.rendis.ui.editor.cubicle.features.ResidualCurrentProtectiveDeviceCreateFeature;
import com.aljoschability.rendis.ui.editor.cubicle.features.WireCreateFeature;

public class CubicleToolBehaviorProvider extends AbstractRendisToolBehaviorProvider {
	public CubicleToolBehaviorProvider(IDiagramTypeProvider dtp) {
		super(dtp);
	}

	@Override
	public String getToolTip(GraphicsAlgorithm ga) {
		PictogramElement pe = ga.getPictogramElement();
		Part bo = getBO(pe);

		if (bo instanceof CircuitBreaker) {
			GraphicsAlgorithm pga = ga.getParentGraphicsAlgorithm();
			int index = pga.getGraphicsAlgorithmChildren().indexOf(ga);

			
//			return "index=" + index;
		}

		return null;
	}

	@Override
	public IPaletteCompartmentEntry[] getPalette() {
		IFeatureProvider fp = getFeatureProvider();
		Collection<IPaletteCompartmentEntry> compartments = new ArrayList<IPaletteCompartmentEntry>();

		// connectors
		PaletteCompartmentEntry connectorsCompartment = new PaletteCompartmentEntry("Connectors", null);
		compartments.add(connectorsCompartment);

		// wire
		Collection<ICreateConnectionFeature> wireFeatures = new ArrayList<>();
		wireFeatures.add(new WireCreateFeature(fp, 0.75));
		wireFeatures.add(new WireCreateFeature(fp, 1.5));
		wireFeatures.add(new WireCreateFeature(fp, 2.5));
		wireFeatures.add(new WireCreateFeature(fp, 4.0));

		connectorsCompartment.addToolEntry(createEntry("Wire", "Create Wire", Wire.class.getCanonicalName(),
				wireFeatures));

		// devices
		PaletteCompartmentEntry devicesEntry = new PaletteCompartmentEntry("Devices", null);
		compartments.add(devicesEntry);

		devicesEntry.addToolEntry(createEntry(new ChannelPortCreateFeature(fp)));

		devicesEntry.addToolEntry(createEntry(new CircuitBreakerCreateFeature(fp, 1)));
		devicesEntry.addToolEntry(createEntry(new CircuitBreakerCreateFeature(fp, 2)));
		devicesEntry.addToolEntry(createEntry(new CircuitBreakerCreateFeature(fp, 3)));
		devicesEntry.addToolEntry(createEntry(new CircuitBreakerCreateFeature(fp, 4)));

		devicesEntry.addToolEntry(createEntry(new CounterCreateFeature(fp, 1)));
		devicesEntry.addToolEntry(createEntry(new CounterCreateFeature(fp, 3)));

		devicesEntry.addToolEntry(createEntry(new ResidualCurrentProtectiveDeviceCreateFeature(fp, 1)));
		devicesEntry.addToolEntry(createEntry(new ResidualCurrentProtectiveDeviceCreateFeature(fp, 3)));

		return compartments.toArray(new IPaletteCompartmentEntry[compartments.size()]);
	}

	@Override
	protected ICustomFeature getDoubleClickFeature(Part bo) {
		return null;
	}
}
