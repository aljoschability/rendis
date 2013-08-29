package com.aljoschability.rendis.ui.editor.floor;

import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.util.IColorConstant;

import com.aljoschability.rendis.Building;
import com.aljoschability.rendis.Floor;
import com.aljoschability.rendis.Part;
import com.aljoschability.rendis.RendisFactory;
import com.aljoschability.rendis.Room;
import com.aljoschability.rendis.ui.pattern.AbstractRendisShapePattern;

public class RoomPattern extends AbstractRendisShapePattern {
	public RoomPattern() {
		super(null);
	}

	@Override
	public PictogramElement add(IAddContext context) {
		ContainerShape cpe = context.getTargetContainer();
		Room bo = (Room) context.getNewObject();

		ContainerShape pe = Graphiti.getPeService().createContainerShape(cpe, true);
		link(pe, bo);

		Rectangle ga = Graphiti.getGaService().createRectangle(pe);
		ga.setBackground(manageColor(IColorConstant.DARK_ORANGE));
		ga.setForeground(manageColor(IColorConstant.BLACK));
		ga.setX(context.getX());
		ga.setY(context.getY());
		ga.setWidth(context.getWidth());
		ga.setHeight(context.getHeight());

		return pe;
	}

	@Override
	public String getCreateName() {
		return "Room";
	}

	@Override
	protected Part create(Part cbo) {
		Room bo = RendisFactory.eINSTANCE.createRoom();

		if (cbo instanceof Building) {
			bo.setBuilding((Building) cbo);
		} else if (cbo instanceof Floor) {
			bo.setFloor((Floor) cbo);
		}

		return bo;
	}

	@Override
	protected boolean canCreateOn(Part cbo) {
		return cbo instanceof Building || cbo instanceof Floor;
	}

	@Override
	protected boolean isBusinessElement(Part bo) {
		return bo instanceof Room;
	}
}
