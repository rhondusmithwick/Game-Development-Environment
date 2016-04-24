//package view;
//
//import javafx.event.EventHandler;
//import javafx.scene.Cursor;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.shape.Shape;
//
//public class Dragger {
//
//	private Shape shape;
//
//	private boolean dragging;
//	private double clickX;
//	private double clickY;
//
//	private Dragger(Shape rectangle) {
//		shape = rectangle;
//	}
//
//	private void setInitialCoordinates(MouseEvent event) {
//		clickX = event.getX();
//		clickY = event.getY();
//	}
//
//	public static void makeDraggable(Shape rectangle) {
//		final Dragger dragger = new Dragger(rectangle);
//
//		rectangle.setOnMousePressed(new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(MouseEvent event) {
//				dragger.mousePressed(event);
//			}
//		});
//		rectangle.setOnMouseDragged(new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(MouseEvent event) {
//				dragger.mouseDragged(event);
//			}
//		});
//		rectangle.setOnMouseMoved(new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(MouseEvent event) {
//				dragger.mouseOver(event);
//			}
//		});
//		rectangle.setOnMouseReleased(new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(MouseEvent event) {
//				dragger.mouseReleased(event);
//			}
//		});
//	}
//
//	protected void mouseOver(MouseEvent event) {
//			setInitialCoordinates(event);
//			shape.setCursor(Cursor.MOVE);
//	}
//
//	protected void mousePressed(MouseEvent event) {
//		setInitialCoordinates(event);
//		dragging = true;
//	}
//
//
//	protected void mouseDragged(MouseEvent event) {
//		double mouseX = event.getX() + shape.getBoundsInParent().getMinX();
//		double mouseY = event.getY() + shape.getBoundsInParent().getMinY();
//		if (dragging) {
//			double translateX = mouseX - clickX;
//			double translateY = mouseY - clickY;
//			shape.setTranslateX(translateX);
//			shape.setTranslateY(translateY);
//			return;
//		}
//	}
//
//	protected void mouseReleased(MouseEvent event) {
//		dragging = false;
//		shape.setCursor(Cursor.DEFAULT);
//	}
//
//}
