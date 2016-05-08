package view.editor.environmenteditor;

import api.IEntity;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import model.component.movement.Position;
import model.component.visual.Sprite;
import view.utilities.SpriteUtilities;

/**
 * @author Tom
 * @author Bruna
 */

public class DragAndResize {

	private static final double MARGIN = 8;

	private boolean resizingBottom = false;
	private boolean resizingRight = false;
	private boolean dragging = false;
	private IEntity heldDownSprite;
	private double initialMouseX, initialMouseY;

	public DragAndResize() {

	}

	private boolean isInRightResizeRegion(Node node, double x) {
		double width = node.getBoundsInParent().getWidth();
		double innerRightSide = width - MARGIN;
		return ((x > innerRightSide) && (x < width));
	}

	private boolean isInBottomResizeRegion(Node node, double y) {
		double height = node.getBoundsInParent().getHeight();
		double innerBottomSide = height - MARGIN;
		return ((y > innerBottomSide) && (y < height));
	}

	private void updateCursor(ImageView imageView, double y, double x) {
		if (this.isInBottomResizeRegion(imageView, y)) {
			imageView.setCursor(Cursor.S_RESIZE);
		} else if (this.isInRightResizeRegion(imageView, x)) {
			imageView.setCursor(Cursor.E_RESIZE);
		} else {
			imageView.setCursor(Cursor.DEFAULT);
		}
	}

	public void makeEntityDragAndResize(IEntity e) {
		ImageView imageView = SpriteUtilities.getImageView(e);
		imageView.setOnMouseEntered(event -> updateCursor(imageView, event.getY(), event.getX()));
		imageView.setOnMousePressed(event -> mousePressed(e, event.getX(), event.getY()));
	}

	public void makeRootDragAndResize(Group root) {
		root.setOnMouseReleased(event -> mouseReleased());
		root.setOnMouseDragged(event -> mouseDragged(this.heldDownSprite, event.getX(), event.getY()));
	}

	private void mousePressed(IEntity e, double x, double y) {
		if (isInBottomResizeRegion(SpriteUtilities.getImageView(e), y)) {
			resizingBottom = true;
			resizingRight = false;
			dragging = false;
		} else if (isInRightResizeRegion(SpriteUtilities.getImageView(e), x)) {
			resizingBottom = false;
			resizingRight = true;
			dragging = false;
		} else {
			dragging = true;
			resizingRight = false;
			resizingBottom = false;
		}
		heldDownSprite = e;
		initialMouseX = x;
		initialMouseY = y;
	}

	private void mouseReleased() {
		dragging = false;
		resizingBottom = false;
		resizingRight = false;
		heldDownSprite = null;
	}

	private void mouseDragged(IEntity e, double x, double y) {
		if (e != null) {
			Sprite path = SpriteUtilities.getSpriteComponent(e);
			Position position = e.getComponent(Position.class);
			if (dragging) {
				double translateX = x - initialMouseX;
				double translateY = y - initialMouseY;
				position.setX(translateX);
				position.setY(translateY);
			} else if (resizingRight) {
				path.setImageWidth(x - position.getX());
			} else if (resizingBottom) {
				path.setImageHeight(y - position.getY());
			}
		}
	}
}
