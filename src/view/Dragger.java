package view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WritableDoubleValue;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

public class Dragger {

	private Rectangle rect;

	private boolean dragging;
	private double margin = 8;

	private DoubleProperty rectinitX = new SimpleDoubleProperty();
	private DoubleProperty rectinitY = new SimpleDoubleProperty();

	private boolean resizing;

	private DoubleProperty rectWidth = new SimpleDoubleProperty();
	private DoubleProperty rectHeight = new SimpleDoubleProperty();

	private WritableDoubleValue rectX;

	private WritableDoubleValue rectY;


	private Dragger(Rectangle rectangle) {
		rect = rectangle;
		//rectWidth.set(rect.getWidth());
		//rectHeight.set(rect.getHeight());
		//rect.widthProperty().bind(rectWidth );
		//rect.heightProperty().bind(rectHeight);
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
		if (isInResizeZone(event)) {
			rect.setCursor(Cursor.SE_RESIZE);
		} else {
			rect.setCursor(Cursor.OPEN_HAND);
		}
	}

	protected void mousePressed(MouseEvent event) {
		if (isInResizeZone(event)) {
			rect.setCursor(Cursor.SE_RESIZE);
			resizing = true;
		} else {
			rect.setCursor(Cursor.CLOSED_HAND);
			dragging = true;
		}
		setInitialCoordinates(event);

	}

	private boolean isInResizeZone(MouseEvent event) {
		double innerBottomSide = rect.getY()+rect.getHeight() - margin;
		double outerBottomSide = rect.getY()+rect.getHeight(); 
		double innerRightSide = rect.getX() + rect.getWidth() - margin;
		double outerRightSide = rect.getX() + rect.getWidth();
		return ((event.getY() > innerBottomSide) && (event.getY() < outerBottomSide) &&
				(event.getX() > innerRightSide) && (event.getX() < outerRightSide));
	}

	protected void mouseDragged(MouseEvent event) {
		if (dragging) {
			rect.setX(event.getX());
			rect.setY(event.getY());
			return;
		} else if (resizing) {
			rect.widthProperty().set(event.getX() - rect.getX());
			rect.heightProperty().set(event.getY() - rect.getY());
		}
	}

	protected void mouseReleased(MouseEvent event) {
		dragging = false;
		resizing = false;
		rect.setCursor(Cursor.DEFAULT);
	}

}
