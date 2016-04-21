package view;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Dragger {

	private Rectangle rect;

	private boolean dragging;
	private double clickX;
	private double clickY;


	private Dragger(Rectangle rectangle) {
		rect = rectangle;
	}

	private void setInitialCoordinates(MouseEvent event) {
		clickX = event.getX();
		clickY = event.getY();
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
			rect.setCursor(Cursor.OPEN_HAND);
	}

	protected void mousePressed(MouseEvent event) {
		rect.setCursor(Cursor.CLOSED_HAND);
		setInitialCoordinates(event);
		dragging = true;
    	
	}


	protected void mouseDragged(MouseEvent event) {
		if (dragging) {
			rect.setX(event.getX());
			rect.setY(event.getY());
			return;
		}
	}

	protected void mouseReleased(MouseEvent event) {
		dragging = false;
		rect.setCursor(Cursor.DEFAULT);
	}

}
