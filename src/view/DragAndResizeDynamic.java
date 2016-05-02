package view;

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

public class DragAndResizeDynamic {

    private static final double MARGIN = 20;

    private boolean resizing = false;
    private boolean resizingRight = false;
    private boolean dragging = false;
    private IEntity heldDownSprite;
    private double initialMouseX, initialMouseY;

    public DragAndResizeDynamic () {

    }

//    private ImageView getImageView (IEntity e) {
//        if (e.hasComponent(AnimatedSprite.class)) {
//            return e.getComponent(AnimatedSprite.class).getImageView();
//        }
//        return e.getComponent(Sprite.class).getImageView();
//    }

    private boolean isInRightResizeRegion (Node node, double x) {
        double width = node.getBoundsInParent().getWidth();
        double innerRightSide = width - MARGIN;
        return ((x > innerRightSide) && (x < width));
    }

    private boolean isInBottomResizeRegion (Node node, double y) {
        double height = node.getBoundsInParent().getHeight();
        double innerBottomSide = height - MARGIN;
        return ((y > innerBottomSide) && (y < height));
    }

    private void updateCursor (ImageView imageView, double y, double x) {
        if (this.isInBottomResizeRegion(imageView, y)) {
            imageView.setCursor(Cursor.S_RESIZE);
        } else if (this.isInRightResizeRegion(imageView, x)) {
            imageView.setCursor(Cursor.E_RESIZE);
        } else {
            imageView.setCursor(Cursor.DEFAULT);
        }
    }

    public void makeEntityDragAndResize (IEntity e) {
//        Sprite sprite = e.getComponent(Sprite.class);
//        ImageView imageView = sprite.getImageView();
        ImageView imageView = SpriteUtilities.getImageView(e);
        imageView.setOnMouseEntered(event -> updateCursor(imageView, event.getY(), event.getX()));
        imageView.setOnMousePressed(event -> mousePressed(e, event.getX(),
                event.getY()));
    }

    public void makeRootDragAndResize (Group root) {
        Group root1 = root;
        root1.setOnMouseReleased(event -> mouseReleased());
        root1.setOnMouseDragged(event -> mouseDragged(this.heldDownSprite, event.getX(), event.getY()));
    }

    private void mousePressed (IEntity e, double x, double y) {
        boolean south = this.isInBottomResizeRegion(SpriteUtilities.getImageView(e), y);
        boolean east = this.isInRightResizeRegion(SpriteUtilities.getImageView(e), x);
        if (this.isInBottomResizeRegion(SpriteUtilities.getImageView(e), y)
                || this.isInRightResizeRegion(SpriteUtilities.getImageView(e), x)) {
            this.resizing = true;
            this.dragging = false;
            if (east) {
                resizingRight = true;
            } else if (south) {
                resizingRight = false;
            }
        } else {
            this.dragging = true;
            this.resizing = false;
        }
        this.heldDownSprite = e;
        this.initialMouseX = x;
        this.initialMouseY = y;
    }

    private void mouseReleased () {
        this.dragging = false;
        this.resizing = false;
        this.heldDownSprite = null;
    }

    private void mouseDragged (IEntity e, double x, double y) {
        if (e != null) {
            Sprite path = SpriteUtilities.getSpriteComponent(e); //e.getComponent(Sprite.class);
            Position position = e.getComponent(Position.class);
            if (dragging) {
                double translateX = x - initialMouseX;
                double translateY = y - initialMouseY;
                position.setX(translateX);
                position.setY(translateY);
            } else if (resizing) {
                if (!resizingRight) {
                    path.setImageHeight(y - position.getY());
                } else {
                    path.setImageWidth(x - position.getX());
                }
            }
        }
    }

}
