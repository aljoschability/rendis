package com.aljoschability.rendis.internal.ui.outline;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.ScaledGraphics;
import org.eclipse.swt.graphics.Path;

/**
 * This class is a subclass of {@link ScaledGraphics}, which only purpose is to fix bugs in ScaledGraphics or implement
 * previously not implemented methods. This class does not add any specific coding.
 */
public class DiagramEditorOutlineScaledGraphics extends ScaledGraphics {
	private final Graphics graphics;

	public DiagramEditorOutlineScaledGraphics(Graphics graphics) {
		super(graphics);
		this.graphics = graphics;
	}

	@Override
	public void clipPath(Path path) {
		graphics.clipPath(path);
	}

	@Override
	public void drawPath(Path path) {
		graphics.drawPath(path);
	}

	@Override
	public void fillGradient(int x, int y, int w, int h, boolean vertical) {
		graphics.fillGradient(x, y, w, h, vertical);
	}

	@Override
	public void fillPath(Path path) {
		graphics.fillPath(path);
	}

	@Override
	public void rotate(float degrees) {
		graphics.rotate(degrees);
	}

	@Override
	public void setClip(Path path) {
		graphics.setClip(path);
	}

	@Override
	public void translate(float dx, float dy) {
		graphics.translate(Math.round(dx), Math.round(dy));
	}
}
