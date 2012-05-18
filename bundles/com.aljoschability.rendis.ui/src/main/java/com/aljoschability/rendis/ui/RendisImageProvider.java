package com.aljoschability.rendis.ui;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.graphiti.ui.platform.AbstractImageProvider;

import com.aljoschability.rendis.Building;
import com.aljoschability.rendis.Channel;
import com.aljoschability.rendis.ChannelPort;
import com.aljoschability.rendis.Floor;
import com.aljoschability.rendis.Room;
import com.aljoschability.rendis.Supplier;

public class RendisImageProvider extends AbstractImageProvider {
	private static final String PREFIX = "icons/provider/"; //$NON-NLS-1$
	private static final String SUFFIX = ".png"; //$NON-NLS-1$

	//	public static final String TEXT = "text"; //$NON-NLS-1$

	@Override
	protected void addAvailableImages() {
		Collection<Class<?>> types = new HashSet<>();

		types.add(Building.class);
		types.add(Supplier.class);
		types.add(Channel.class);

		types.add(Floor.class);
		types.add(Room.class);
		types.add(ChannelPort.class);

		for (Class<?> type : types) {
			addImageFilePath(type.getCanonicalName(), PREFIX + type.getSimpleName() + SUFFIX);
		}
	}
}
