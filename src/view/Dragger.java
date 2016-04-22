package view;

import javafx.beans.binding.DoubleExpression;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Dragger {

	private Rectangle rect;

	private boolean dragging;
	private double margin = 8;

	private DoubleProperty rectinitX = new SimpleDoubleProperty();
	private DoubleProperty rectinitY = new SimpleDoubleProperty();

	private boolean resizing;

	private DoubleProperty rectX = new SimpleDoubleProperty();
	private DoubleProperty rectY = new SimpleDoubleProperty();


	private Dragger(Rectangle rectangle) {
		rect = rectangle;
		//rect.widthProperty().bind(rectX.subtract(rectinitX));
		//rect.heightProperty().bind(rectY.subtract(rectinitY));
	}

	private void setInitialCoordinates(MouseEvent event) {
		rectinitX.set(event.getX());
		rectinitY.set(event.getY());
	}

	public static void makeDraggable(Rectangle rectangle) {
		final Dragger dragger = new Dragger(rectangle);

		rectangle.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				dragger.mousePressed(event);
			}
		});
		rectangle.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				dragger.mouseDragged(event);
			}
		});
		rectangle.setOnMouseMoved(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				dragger.mouseOver(event);
			}
		});
		rectangle.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				dragger.mouseReleased(event);
			}
		});
	}

	protected void mouseOver(MouseEvent event) {
		setInitialCoordinates(event);
		//if (isInResizeZone(event)) {
			//rect.setCursor(Cursor.S_RESIZE);
		//} else {
			//rect.setCursor(Cursor.OPEN_HAND);
		//}
	}

	protected void mousePressed(MouseEvent event) {
		//if (isInResizeZone(event)) {
			//rect.setCursor(Cursor.S_RESIZE);
			//resizing = true;
		//} else {
			rect.setCursor(Cursor.CLOSED_HAND);
			dragging = true;
		//}
		setInitialCoordinates(event);

	}

	protected void mouseDragged(MouseEvent event) {
		if (dragging) {
			rect.setX(event.getX());
			rect.setY(event.getY());
			return;
		} else if (resizing) {
			rectX.set(event.getX());
			rectY.set(event.getY());
		}
	}

	protected void mouseReleased(MouseEvent event) {
		dragging = false;
		resizing = false;
		rect.setCursor(Cursor.DEFAULT);
	}

}
