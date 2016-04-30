package view;

import api.IEntity;
import api.ILevel;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import model.component.movement.Position;
import model.component.visual.AnimatedSprite;
import model.component.visual.Sprite;

public class DragAndResizeDynamic {

	private static final double MARGIN = 8;

	private Group root;
	private boolean resizing = false;
	private boolean dragging = false;
	private IEntity heldDownSprite;
	private double initialMouseX, initialMouseY;

	public DragAndResizeDynamic() {
		
	}

	private ImageView getImageView(IEntity e) {
		if (e.hasComponent(AnimatedSprite.class)) {
			return e.getComponent(AnimatedSprite.class).getImageView();
		}
		return e.getComponent(Sprite.class).getImageView();
	}

	private boolean isInBottomResizeRegion(Node node, double y) {
		double height = node.getBoundsInParent().getHeight();
		double innerBottomSide = height - MARGIN;
		double outerBottomSide = height;
		return ((y > innerBottomSide) && (y < outerBottomSide));
	}

	private void updateCursor(ImageView imageView, double y) {
		if (this.isInBottomResizeRegion(imageView, y)) {
			imageView.setCursor(Cursor.S_RESIZE);
		} else {
			imageView.setCursor(Cursor.DEFAULT);
		}
	}

	public void makeEntityDragAndResize(IEntity e) {
		Sprite sprite = e.getComponent(Sprite.class);
		ImageView imageView = sprite.getImageView();
		imageView.setOnMouseEntered(event -> updateCursor(imageView, event.getY()));
		imageView.setOnMousePressed(event -> mousePressed(e, event.getX(), 
				event.getY()));
	}

	public void makeRootDragAndResize(Group root) {
		this.root = root;
		this.root.setOnMouseReleased(event -> mouseReleased());
		this.root.setOnMouseDragged(event -> mouseDragged(this.heldDownSprite, event.getX(), event.getY()));
	}
	
	private void mousePressed(IEntity e, double x, double y) {
		if (this.isInBottomResizeRegion(getImageView(e), y)) {
			this.resizing = true;
			this.dragging = false;
		} else {
			this.dragging = true;
			this.resizing = false;
		}
		this.heldDownSprite = e;
		this.initialMouseX = x;
		this.initialMouseY = y;
	}
	
	private void mouseReleased() {
		this.dragging = false;
		this.resizing = false;
		this.heldDownSprite = null;
	}

	private void mouseDragged(IEntity e, double x, double y) {
		if (e != null){
		Sprite path = e.getComponent(Sprite.class);
		Position position = e.getComponent(Position.class);
		if (dragging) {
			double translateX = x - initialMouseX;
			double translateY = y - initialMouseY;
			position.setX(translateX);
			position.setY(translateY);
		} else if (resizing) {
			path.setImageHeight(y - position.getY());
		}
		}
	}

}
