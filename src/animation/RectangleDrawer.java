package animation;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by rhondusmithwick on 4/24/16.
 *
 * @author Rhondu Smithwick
 */
public class RectangleDrawer {

    private final DoubleProperty rectinitX = new SimpleDoubleProperty(this, "rectinitX", 0.0);
    private final DoubleProperty rectinitY = new SimpleDoubleProperty(this, "rectinitY", 0.0);
    private final DoubleProperty rectX = new SimpleDoubleProperty(this, "rectX", 0.0);
    private final DoubleProperty rectY = new SimpleDoubleProperty(this, "rectY", 0.0);
    private Rectangle rectangle = new Rectangle();

    public void reset() {
        rectangle = new Rectangle();
        rectinitX.set(0.0);
        rectinitY.set(0.0);
        rectX.set(0.0);
        rectY.set(0.0);
        rectangle.widthProperty().bind(rectX.subtract(rectinitX));
        rectangle.heightProperty().bind(rectY.subtract(rectinitY));
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.RED);
    }

    public void handleMousePressed(MouseEvent event) {
        boolean primary = event.getButton() == MouseButton.PRIMARY;
        boolean secondary = event.getButton() == MouseButton.SECONDARY;
        if (primary || secondary) {
            rectangle.setX(event.getX());
            rectangle.setY(event.getY());
        }
        if (primary) {
            rectinitX.set(event.getX());
            rectinitY.set(event.getY());
        }
    }

    public void handleMouseDragged(MouseEvent event) {
        rectX.set(event.getX());
        rectY.set(event.getY());
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}
