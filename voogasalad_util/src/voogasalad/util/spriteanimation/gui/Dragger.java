package voogasalad.util.spriteanimation.gui;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

/**
 * @author Bruna Liborio
 */
public class Dragger {

    private final DoubleProperty rectinitX = new SimpleDoubleProperty();
    private final DoubleProperty rectinitY = new SimpleDoubleProperty();
    private Rectangle rect;
    private boolean dragging;
    private boolean resizing;

    private Dragger(Rectangle rectangle) {
        rect = rectangle;
    }

    public static void makeDraggable(Rectangle rectangle) {
        final Dragger dragger = new Dragger(rectangle);

        rectangle.setOnMousePressed(dragger::mousePressed);
        rectangle.setOnMouseDragged(dragger::mouseDragged);
        rectangle.setOnMouseMoved(dragger::mouseOver);
        rectangle.setOnMouseReleased(dragger::mouseReleased);

    }

    private void setInitialCoordinates(MouseEvent event) {
        rectinitX.set(event.getX());
        rectinitY.set(event.getY());
    }

    private void mousePressed(MouseEvent event) {
        if (isInResizeZone(event)) {
            rect.setCursor(Cursor.SE_RESIZE);
            resizing = true;
        } else {
            rect.setCursor(Cursor.CLOSED_HAND);
            dragging = true;
        }
        setInitialCoordinates(event);
    }

    private void mouseDragged(MouseEvent event) {
        if (dragging) {
            rect.setX(event.getX());
            rect.setY(event.getY());
        } else if (resizing) {
            rect.widthProperty().set(event.getX() - rect.getX());
            rect.heightProperty().set(event.getY() - rect.getY());
        }
    }

    private void mouseOver(MouseEvent event) {
        if (isInResizeZone(event)) {
            rect.setCursor(Cursor.SE_RESIZE);
        } else {
            rect.setCursor(Cursor.OPEN_HAND);
        }
    }

    private void mouseReleased(MouseEvent event) {
        dragging = false;
        resizing = false;
        rect.setCursor(Cursor.DEFAULT);
    }

    private boolean isInResizeZone(MouseEvent event) {
        double margin = 8;
        double innerBottomSide = rect.getY() + rect.getHeight() - margin;
        double outerBottomSide = rect.getY() + rect.getHeight();
        double innerRightSide = rect.getX() + rect.getWidth() - margin;
        double outerRightSide = rect.getX() + rect.getWidth();
        return ((event.getY() > innerBottomSide) && (event.getY() < outerBottomSide) &&
                (event.getX() > innerRightSide) && (event.getX() < outerRightSide));
    }

}