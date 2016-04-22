package view;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import model.component.movement.Position;
import model.component.visual.ImagePath;

public class DragAndResize {

	private double margin = 8;

	private boolean resizing;
	private boolean dragging;
	private double parentMinY;
	private double parentHeight;
	private double clickX;
	private double clickY;
	private double minH;

	private Node node;
	private Rectangle shape;
	private Position position;
	private final ImageView image;
	private final ImagePath component;

	private DragAndResize(ImagePath component, Position aPos) {
		this.component = component;
		this.image = component.getImageView();
		this.node = component.getImageView();
		this.position = aPos;
		this.shape = new Rectangle();
		minH = image.minHeight(image.getFitWidth());
	}

	public DragAndResize(Rectangle shape) {
		this.component = new ImagePath();
		this.node = shape;
		this.image = new ImageView();
		this.position = new Position();
		this.shape = shape;
		minH = shape.minHeight(0);
	}

	private void setInitialCoordinates(MouseEvent event) {
		parentMinY = node.getBoundsInParent().getMinY();
		parentHeight = node.getBoundsInParent().getHeight();
		clickX = event.getX();
		clickY = event.getY();
	}

	private void resizeHeight(double height) {
		if (minH > height) {
			return;
		}
		component.setImageHeight(height);
		shape.setHeight(height);
	}

	public static void makeResizable(ImagePath component, Position aPos) {
		final DragAndResize resizer = new DragAndResize(component, aPos);
		ImageView anImage = component.getImageView();
		set(anImage,resizer);
	}

	public static void makeResizable(Rectangle aShape){
		final DragAndResize resizer = new DragAndResize(aShape);
		set(aShape,resizer);
	}

	public static void set(Node node, DragAndResize resizer){
		node.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				resizer.mousePressed(event);
			}
		});
		node.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				resizer.mouseDragged(event);
			}
		});
		node.setOnMouseMoved(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				resizer.mouseOver(event);
			}
		});
		node.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				resizer.mouseReleased(event);
			}
		});
	}

	protected void mouseOver(MouseEvent event) {
		setInitialCoordinates(event);
		if (isInResizeZone(event) || resizing) {
			node.setCursor(Cursor.S_RESIZE);
		} else {
			node.setCursor(Cursor.DEFAULT);
		}
	}

	protected void mousePressed(MouseEvent event) {
		setInitialCoordinates(event);
		dragging = !isInResizeZone(event);
		resizing = isInResizeZone(event);
	}

	protected boolean isInResizeZone(MouseEvent event) {
		return isInBottomResize(event);
	}

	private boolean isInBottomResize(MouseEvent event) {
		double innerBottomSide = parentHeight - margin;
		double outerBottomSide = parentHeight;
		return ((event.getY() > innerBottomSide) && (event.getY() < outerBottomSide));
	}

	protected void mouseDragged(MouseEvent event) {
		double mouseX = event.getX() + node.getBoundsInParent().getMinX();
		double mouseY = event.getY() + node.getBoundsInParent().getMinY();
		if (dragging) {
			double translateX = mouseX - clickX;
			double translateY = mouseY - clickY;
			position.setX(translateX);
			node.setTranslateX(translateX);
			position.setY(translateY);
			node.setTranslateY(translateY);
			return;
		} else {
			if (isInBottomResize(event) || resizing) {
				double newHeight = mouseY - parentMinY;
				resizeHeight(newHeight);
			}
		}
	}

	protected void mouseReleased(MouseEvent event) {
		resizing = false;
		dragging = false;
		node.setCursor(Cursor.DEFAULT);
	}

}