package com.aljoschability.rendis.internal.ui.outline;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureListener;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.KeyEvent;
import org.eclipse.draw2d.KeyListener;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.UpdateListener;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class DiagramEditorOutlineThumbnail extends Figure implements UpdateListener {
	private class ThumbnailUpdater implements Runnable {
		private static final int MAX_BUFFER_SIZE = 256;

		private int currentHTile;
		private int currentVTile;

		private int hTiles;
		private int vTiles;

		private boolean isActive;

		private boolean isRunning;
		private GC thumbnailGC;
		private Graphics thumbnailGraphics;
		private Dimension tileSize;

		public ThumbnailUpdater() {
			isActive = true;
			isRunning = false;
		}

		/**
		 * Stops the updater and disposes of any resources.
		 */
		public void dispose() {
			setActive(false);
			stop();
			if (thumbnailImage != null) {
				thumbnailImage.dispose();
				thumbnailImage = null;
				thumbnailImageSize = null;
			}
		}

		/**
		 * Returns <code>true</code> if this ThumbnailUpdater is active. An inactive updater has disposed of its
		 * {@link Image}. The updater may be active and not currently running.
		 * 
		 * @return <code>true</code> if this ThumbnailUpdater is active
		 */
		public boolean isActive() {
			return isActive;
		}

		/**
		 * Returns <code>true</code> if this is currently running and updating at least one tile on the thumbnail
		 * {@link Image}.
		 * 
		 * @return <code>true</code> if this is currently running
		 */
		public boolean isRunning() {
			return isRunning;
		}

		private void resetThumbnailImage() {
			if (thumbnailImage != null) {
				thumbnailImage.dispose();
			}

			if (!targetSize.isEmpty()) {
				if (thumbnailImage != null && !thumbnailImage.isDisposed()) {
					thumbnailImage.dispose();
				}
				thumbnailImage = new Image(Display.getDefault(), targetSize.width, targetSize.height);
				thumbnailImageSize = new Dimension(targetSize);
			} else {
				thumbnailImage = null;
				thumbnailImageSize = new Dimension(0, 0);
			}
		}

		/**
		 * Resets the number of vertical and horizontal tiles, as well as the tile size and current tile index.
		 */
		public void resetTileValues() {
			hTiles = (int) Math.ceil((float) getSourceRectangle().width / (float) MAX_BUFFER_SIZE);
			vTiles = (int) Math.ceil((float) getSourceRectangle().height / (float) MAX_BUFFER_SIZE);

			tileSize = new Dimension((int) Math.ceil((float) getSourceRectangle().width / (float) hTiles),
					(int) Math.ceil((float) getSourceRectangle().height / (float) vTiles));

			currentHTile = 0;
			currentVTile = 0;
		}

		/**
		 * Restarts the updater.
		 */
		public void restart() {
			stop();
			start();
		}

		/**
		 * Updates the current tile on the Thumbnail. An area of the source Figure is painted to an {@link Image}. That
		 * Image is then drawn on the Thumbnail. Scaling of the source Image is done inside
		 * {@link GC#drawImage(Image, int, int, int, int, int, int, int, int)} since the source and target sizes are
		 * different. The current tile indexes are incremented and if more updating is necesary, this {@link Runnable}
		 * is called again in a {@link Display#timerExec(int, Runnable)}. If no more updating is required,
		 * {@link #stop()} is called.
		 */
		@Override
		public void run() {
			if (!isActive() || !isRunning()) {
				return;
			}

			int v = currentVTile;
			int sy1 = v * tileSize.height;
			int sy2 = Math.min((v + 1) * tileSize.height, getSourceRectangle().height);

			int h = currentHTile;
			int sx1 = h * tileSize.width;
			int sx2 = Math.min((h + 1) * tileSize.width, getSourceRectangle().width);
			org.eclipse.draw2d.geometry.Point p = getSourceRectangle().getLocation();

			Rectangle rect = new Rectangle(sx1 + p.x, sy1 + p.y, sx2 - sx1, sy2 - sy1);

			// use the complete rectangle, not just a tile
			rect = getSourceRectangle().getCopy();

			thumbnailGraphics.pushState();
			thumbnailGraphics.setClip(rect);
			thumbnailGraphics.fillRectangle(rect);

			try {
				sourceFigure.paint(thumbnailGraphics);
			} catch (SWTException e) {
				// nothing
			} catch (IllegalArgumentException e) {
				// nothing
			}

			thumbnailGraphics.popState();

			if (currentHTile < hTiles - 1) {
				currentHTile++;
			} else {
				currentHTile = 0;
				if (currentVTile < vTiles - 1) {
					currentVTile++;
				} else {
					currentVTile = 0;
				}
			}

			stop();
			repaint();
		}

		/**
		 * Sets the active flag.
		 * 
		 * @param value The active value
		 */
		public void setActive(boolean value) {
			isActive = value;
		}

		/**
		 * Starts this updater. This method initializes all the necessary resources and puts this {@link Runnable} on
		 * the asynch queue. If this updater is not active or is already running, this method just returns.
		 */
		public void start() {
			if (!isActive() || isRunning()) {
				return;
			}

			isRunning = true;
			setDirty(false);
			resetTileValues();

			if (!targetSize.equals(thumbnailImageSize)) {
				resetThumbnailImage();
			}

			if (targetSize.isEmpty()) {
				return;
			}

			thumbnailGC = new GC(thumbnailImage, sourceFigure.isMirrored() ? SWT.RIGHT_TO_LEFT : SWT.NONE);
			thumbnailGraphics = new DiagramEditorOutlineScaledGraphics(new SWTGraphics(thumbnailGC));
			thumbnailGraphics.scale(getScaleX());
			thumbnailGraphics.translate(getSourceRectangle().getLocation().negate());

			Color color = sourceFigure.getForegroundColor();
			if (color != null) {
				thumbnailGraphics.setForegroundColor(color);
			}
			color = sourceFigure.getBackgroundColor();
			if (color != null) {
				thumbnailGraphics.setBackgroundColor(color);
			}
			thumbnailGraphics.setFont(sourceFigure.getFont());

			setScales(targetSize.width / (float) getSourceRectangle().width, targetSize.height
					/ (float) getSourceRectangle().height);

			Display.getCurrent().asyncExec(this);
		}

		/**
		 * Stops this updater. Also disposes of resources (except the thumbnail image which is still needed for
		 * painting).
		 */
		public void stop() {
			isRunning = false;
			if (thumbnailGraphics != null) {
				thumbnailGraphics.dispose();
				thumbnailGraphics = null;
			}
			if (thumbnailGC != null) {
				thumbnailGC.dispose();
				thumbnailGC = null;
			}
		}
	}

	private class ClickScrollerAndDragTransferrer extends MouseMotionListener.Stub implements MouseListener {
		private boolean dragTransfer;

		@Override
		public void mouseDoubleClicked(MouseEvent me) {
			// nothing
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (dragTransfer) {
				syncher.mouseDragged(e);
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (!getClientArea().contains(e.getLocation())) {
				return;
			}

			Dimension center = selector.getBounds().getSize().scale(0.5f);

			int minX = viewport.getHorizontalRangeModel().getMinimum();
			int minY = viewport.getVerticalRangeModel().getMinimum();
			Point location = e.getLocation().getTranslated(getLocation().getNegated()).translate(center.negate())
					.scale(1.0f / getViewportScaleX(), 1.0f / getViewportScaleY()).translate(minX, minY);

			viewport.setViewLocation(location);
			syncher.mousePressed(e);
			dragTransfer = true;
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			syncher.mouseReleased(e);
			dragTransfer = false;
		}
	}

	private class ScrollSynchronizer extends MouseMotionListener.Stub implements MouseListener {
		private Point startLocation;
		private Point viewLocation;

		@Override
		public void mouseDoubleClicked(MouseEvent me) {
			// nothing
		}

		@Override
		public void mouseDragged(MouseEvent me) {
			Dimension d = me.getLocation().getDifference(startLocation);
			d.scale(1.0f / getViewportScaleX(), 1.0f / getViewportScaleY());
			viewport.setViewLocation(viewLocation.getTranslated(d));
			me.consume();
		}

		@Override
		public void mousePressed(MouseEvent me) {
			startLocation = me.getLocation();
			viewLocation = viewport.getViewLocation();
			me.consume();
		}

		@Override
		public void mouseReleased(MouseEvent me) {
			// nothing
		}
	}

	private class SelectorFigure extends Figure {
		{
			Display display = Display.getCurrent();
			PaletteData pData = new PaletteData(0xFF, 0xFF00, 0xFF0000);
			RGB rgb = ColorConstants.menuBackgroundSelected.getRGB();
			int fillColor = pData.getPixel(rgb);
			ImageData iData = new ImageData(1, 1, 24, pData);
			iData.setPixel(0, 0, fillColor);
			iData.setAlpha(0, 0, 55);
			image = new Image(display, iData);
		}
		private Rectangle iBounds = new Rectangle(0, 0, 1, 1);

		private Image image;

		@Override
		public void paintFigure(Graphics g) {
			Rectangle bounds = getBounds().getCopy();

			// Avoid drawing images that are 0 in dimension
			if (bounds.width < 5 || bounds.height < 5) {
				return;
			}

			// Don't paint the selector figure if the entire source is visible.
			Dimension thumbnailSize = new Dimension(getThumbnailImage());
			// expand to compensate for rounding errors in calculating bounds
			Dimension size = getSize().getExpanded(1, 1);
			if (size.contains(thumbnailSize)) {
				return;
			}

			bounds.height--;
			bounds.width--;
			g.drawImage(image, iBounds, bounds);

			g.setForegroundColor(ColorConstants.menuBackgroundSelected);
			g.drawRectangle(bounds);
		}

		protected void dispose() {
			image.dispose();
		}

	}

	private FigureListener figureListener = new FigureListener() {
		@Override
		public void figureMoved(IFigure source) {
			reconfigureSelectorBounds();
		}
	};
	private KeyListener keyListener = new KeyListener.Stub() {
		@Override
		public void keyPressed(KeyEvent ke) {
			int moveX = viewport.getClientArea().width / 4;
			int moveY = viewport.getClientArea().height / 4;
			if (ke.keycode == SWT.HOME || (isMirrored() ? ke.keycode == SWT.ARROW_RIGHT : ke.keycode == SWT.ARROW_LEFT)) {
				viewport.setViewLocation(viewport.getViewLocation().translate(-moveX, 0));
			} else if (ke.keycode == SWT.END
					|| (isMirrored() ? ke.keycode == SWT.ARROW_LEFT : ke.keycode == SWT.ARROW_RIGHT)) {
				viewport.setViewLocation(viewport.getViewLocation().translate(moveX, 0));
			} else if (ke.keycode == SWT.ARROW_UP || ke.keycode == SWT.PAGE_UP) {
				viewport.setViewLocation(viewport.getViewLocation().translate(0, -moveY));
			} else if (ke.keycode == SWT.ARROW_DOWN || ke.keycode == SWT.PAGE_DOWN) {
				viewport.setViewLocation(viewport.getViewLocation().translate(0, moveY));
			}
		}
	};

	private PropertyChangeListener propListener = new PropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			reconfigureSelectorBounds();
		}
	};

	private SelectorFigure selector;

	private ScrollSynchronizer syncher;
	private Viewport viewport;

	private boolean isDirty;
	private float scaleX;
	private float scaleY;

	private IFigure sourceFigure;

	/**
	 * The target size.
	 */
	Dimension targetSize = new Dimension(0, 0);
	private Image thumbnailImage;
	private Dimension thumbnailImageSize;
	private ThumbnailUpdater updater = new ThumbnailUpdater();

	/**
	 * Creates a new Thumbnail with the given IFigure as its source figure.
	 * 
	 * @param fig The source figure
	 */
	public DiagramEditorOutlineThumbnail(GraphicalViewer viewer) {
		this();

		ScalableFreeformRootEditPart rootEdit = (ScalableFreeformRootEditPart) viewer.getRootEditPart();

		setViewport((Viewport) rootEdit.getFigure());
		initialize();
		setSource(rootEdit.getLayer(LayerConstants.PRINTABLE_LAYERS));
	}

	/**
	 * Creates a new Thumbnail. The source Figure must be set separately if you use this constructor.
	 */
	public DiagramEditorOutlineThumbnail() {
		super();
	}

	/**
	 * Sets the Viewport that this ScrollableThumbnail will synch with.
	 * 
	 * @param port The Viewport
	 */
	public void setViewport(Viewport port) {
		port.addPropertyChangeListener(Viewport.PROPERTY_VIEW_LOCATION, propListener);
		port.addFigureListener(figureListener);
		viewport = port;
	}

	private void initialize() {
		selector = new SelectorFigure();
		selector.addMouseListener(syncher = new ScrollSynchronizer());
		selector.addMouseMotionListener(syncher);
		selector.setFocusTraversable(true);
		selector.addKeyListener(keyListener);
		add(selector);
		ClickScrollerAndDragTransferrer transferrer = new ClickScrollerAndDragTransferrer();
		addMouseListener(transferrer);
		addMouseMotionListener(transferrer);
	}

	/**
	 * Sets the source Figure. Also sets the scales and creates the necessary update manager.
	 * 
	 * @param fig The source figure
	 */
	public void setSource(IFigure fig) {
		if (sourceFigure == fig) {
			return;
		}
		if (sourceFigure != null) {
			sourceFigure.getUpdateManager().removeUpdateListener(this);
		}
		sourceFigure = fig;
		if (sourceFigure != null) {
			setScales((float) getSize().width / (float) getSourceRectangle().width, (float) getSize().height
					/ (float) getSourceRectangle().height);
			sourceFigure.getUpdateManager().addUpdateListener(this);
			repaint();
		}
	}

	/**
	 * Paint figure.
	 * 
	 * @param graphics the graphics
	 * 
	 * @see org.eclipse.draw2d.Figure#paintFigure(Graphics)
	 */
	@Override
	protected void paintFigure(Graphics graphics) {
		Image thumbnail = getThumbnailImage();
		if (thumbnail == null) {
			return;
		}
		graphics.drawImage(thumbnail, getClientArea().getLocation());
	}

	/**
	 * Returns the scaled Image of the source Figure. If the Image needs to be updated, the ThumbnailUpdater will
	 * notified.
	 * 
	 * @return The thumbnail image
	 */
	protected Image getThumbnailImage() {
		Dimension oldSize = targetSize;
		targetSize = getPreferredSize();
		targetSize.expand(new Dimension(getInsets().getWidth(), getInsets().getHeight()).negate());
		setScales(targetSize.width / (float) getSourceRectangle().width, targetSize.height
				/ (float) getSourceRectangle().height);
		if (isDirty() && !updater.isRunning()) {
			updater.start();
		} else if (oldSize != null && !targetSize.equals(oldSize)) {
			revalidate();
			updater.restart();
		}

		return thumbnailImage;
	}

	/**
	 * Reconfigures the SelectorFigure's bounds if the scales have changed.
	 * 
	 * @param scaleX The X scale
	 * @param scaleY The Y scale
	 * 
	 * @see org.eclipse.draw2d.parts.Thumbnail#setScales(float, float)
	 */
	protected void setScales(float scaleX, float scaleY) {
		if (scaleX == getScaleX() && scaleY == getScaleY()) {
			return;
		}

		this.scaleX = scaleX;
		this.scaleY = scaleY;

		reconfigureSelectorBounds();
	}

	/**
	 * Returns the scale factor on the X-axis.
	 * 
	 * @return X scale
	 */
	protected float getScaleX() {
		return scaleX;
	}

	/**
	 * Returns the scale factor on the Y-axis.
	 * 
	 * @return Y scale
	 */
	protected float getScaleY() {
		return scaleY;
	}

	private void reconfigureSelectorBounds() {
		Rectangle rect = new Rectangle();
		Point offset = viewport.getViewLocation();
		offset.x -= viewport.getHorizontalRangeModel().getMinimum();
		offset.y -= viewport.getVerticalRangeModel().getMinimum();
		rect.setLocation(offset);
		rect.setSize(viewport.getClientArea().getSize());
		rect.scale(getViewportScaleX(), getViewportScaleY());
		rect.translate(getClientArea().getLocation());
		selector.setBounds(rect);
	}

	private double getViewportScaleX() {
		return (double) targetSize.width / viewport.getContents().getBounds().width;
	}

	private double getViewportScaleY() {
		return (double) targetSize.height / viewport.getContents().getBounds().height;
	}

	@Override
	public Dimension getPreferredSize(int wHint, int hHint) {
		if (prefSize == null) {
			return adjustToAspectRatio(getBounds().getSize(), false);
		}

		Dimension preferredSize = adjustToAspectRatio(prefSize.getCopy(), true);

		if (maxSize == null) {
			return preferredSize;
		}

		Dimension maximumSize = adjustToAspectRatio(maxSize.getCopy(), true);
		if (preferredSize.contains(maximumSize)) {
			return maximumSize;
		} else {
			return preferredSize;
		}
	}

	private Dimension adjustToAspectRatio(Dimension size, boolean adjustToMaxDimension) {
		Dimension sourceSize = getSourceRectangle().getSize();
		Dimension borderSize = new Dimension(getInsets().getWidth(), getInsets().getHeight());
		size.expand(borderSize.getNegated());
		int width, height;
		if (adjustToMaxDimension) {
			width = Math.max(size.width, (int) (size.height * sourceSize.width / (float) sourceSize.height + 0.5));
			height = Math.max(size.height, (int) (size.width * sourceSize.height / (float) sourceSize.width + 0.5));
		} else {
			width = Math.min(size.width, (int) (size.height * sourceSize.width / (float) sourceSize.height + 0.5));
			height = Math.min(size.height, (int) (size.width * sourceSize.height / (float) sourceSize.width + 0.5));
		}
		size.width = width;
		size.height = height;
		return size.expand(borderSize);
	}

	/**
	 * Returns the rectangular region relative to the source figure which will be the basis of the thumbnail. The value
	 * may be returned by reference and should not be modified by the caller.
	 * 
	 * @return the region of the source figure being used for the thumbnail
	 * 
	 * @since 3.1
	 */
	protected Rectangle getSourceRectangle() {
		return sourceFigure.getBounds();
	}

	@Override
	public void notifyPainting(Rectangle damage, @SuppressWarnings("rawtypes") Map dirtyRegions) {
		for (Object object : dirtyRegions.keySet()) {
			IFigure current = (IFigure) object;
			while (current != null) {
				if (current == getSource()) {
					setDirty(true);
					repaint();
					return;
				}
				current = current.getParent();
			}
		}
	}

	/**
	 * Returns the source figure being used to generate a thumbnail.
	 * 
	 * @return the source figure
	 */
	protected IFigure getSource() {
		return sourceFigure;
	}

	/**
	 * Sets the dirty flag.
	 * 
	 * @param value The dirty value
	 */
	public void setDirty(boolean value) {
		isDirty = value;
	}

	public void dispose() {
		viewport.removePropertyChangeListener(Viewport.PROPERTY_VIEW_LOCATION, propListener);
		viewport.removeFigureListener(figureListener);
		if (selector.getParent() == this) { // if this method was called before,
			// the selector might already be
			// removed
			remove(selector);
		}
		disposeSelector();

		sourceFigure.getUpdateManager().removeUpdateListener(this);
		updater.dispose();
	}

	public void disposeSelector() {
		selector.dispose();
	}

	@Override
	public void notifyValidating() {
		// nothing to do
	}

	/**
	 * Returns <code>true</code> if the source figure has changed.
	 * 
	 * @return <code>true</code> if the source figure has changed
	 */
	protected boolean isDirty() {
		return isDirty;
	}
}
