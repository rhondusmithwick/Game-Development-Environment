package view;

import api.IEntity;
import api.ILevel;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import model.component.movement.Position;
import model.component.visual.AnimatedSprite;
import model.component.visual.Sprite;

import java.util.HashSet;
import java.util.Set;

public class ViewUtilities {

	// TODO: resource file
	private static final double MARGIN = 8;
	private static final int DEPTH = 70;
	private static final Color HIGHLIGHT_COLOR = Color.YELLOW;
	private static final String SELECT_EFFECT = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,50,0.8), 10, 0, 0, 0)",
			NO_SELECT_EFFECT = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0), 0, 0, 0, 0)";
	private Group root;
	private ILevel universe;
	private boolean resizing = false, dragging = false;
	private IEntity heldDownSprite;
	private Set<IEntity> selectedSprites = new HashSet<IEntity>();
	private double initialMouseX, initialMouseY;
	private long timeMouseClicked, clickThresholdMillis = 400;

	public ViewUtilities(Group root, ILevel universe) {
		this.root = root;
		this.universe = universe;
	}

	private ImageView getImageView(IEntity e) {
		if (e.hasComponent(AnimatedSprite.class)) {
			return e.getComponent(AnimatedSprite.class).getImageView();
		}
		return e.getComponent(Sprite.class).getImageView();
	}

	public void allowDeletion() {
		// root.setOnKeyPressed(event -> {
		// if (event.getCode() == KeyCode.DELETE) {
		// TODO: why doesn't keypress work?!
		root.setOnContextMenuRequested(event -> {
			for (IEntity e : this.selectedSprites) {
				this.universe.removeEntity(e.getID());
			}
			// }
		});
	}

	private void dehighlight(IEntity e) {
//		this.getImageView(e).setEffect(null);
		getImageView(e).setStyle(NO_SELECT_EFFECT);
		selectedSprites.clear(); // de-select
	}

	private void highlight(IEntity e) {
//		DropShadow borderGlow = new DropShadow();
//		borderGlow.setOffsetY(0f);
//		borderGlow.setOffsetX(0f);
//		borderGlow.setColor(HIGHLIGHT_COLOR);
//		borderGlow.setWidth(DEPTH);
//		borderGlow.setHeight(DEPTH);
//		this.getImageView(e).setEffect(borderGlow);
//		this.selectedSprites.add(e);
		getImageView(e).setStyle(SELECT_EFFECT); // TODO: StringConstants SELECT_EFFECT
	}

	private boolean isInBottomResizeRegion(Node node, double y) {
		double height = node.getBoundsInParent().getHeight();
		double innerBottomSide = height - MARGIN;
		double outerBottomSide = height;
		return ((y > innerBottomSide) && (y < outerBottomSide));
	}

	private void changeCursorForResizing(ImageView imageView, double y) {
		if (this.isInBottomResizeRegion(imageView, y)) {
			imageView.setCursor(Cursor.S_RESIZE);
		} else {
			imageView.setCursor(Cursor.DEFAULT);
		}
	}

	private void holdDownSprite(IEntity e, double x, double y) {
		if (this.isInBottomResizeRegion(this.getImageView(e), y)) {
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

	private void releaseSprite() {
		this.dragging = false;
		this.resizing = false;
		this.heldDownSprite = null;
	}

	public void makeSelectable(IEntity e) {
		Sprite path = e.getComponent(Sprite.class);
		ImageView imageView = path.getImageView();

		imageView.setOnMouseEntered(event -> this.changeCursorForResizing(imageView, event.getY()));
		imageView.setOnMousePressed(event -> {
			this.holdDownSprite(e, event.getX(), event.getY());
			timeMouseClicked = System.currentTimeMillis(); // click event tracking
//			System.out.println("pressed");
		});
		// TODO: make sure this is not hacky
		root.setOnMouseReleased(event -> {
			this.releaseSprite();
			long duration = System.currentTimeMillis()-timeMouseClicked;
			if(duration<clickThresholdMillis) {// click event handling
				if (imageView.getStyle().equals(NO_SELECT_EFFECT)) { // not selected
					this.highlight(e);
				} else {
					this.dehighlight(e);
				}
			}
//			System.out.println("released - "+duration);
		});
	}

	public void allowDragging() {
		root.setOnMouseDragged(event -> {
			if (this.heldDownSprite != null) {
				mouseDragged(this.heldDownSprite, event.getX(), event.getY());
			}
		});
	}

	private void mouseDragged(IEntity e, double x, double y) {
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
