package view.editor.environmenteditor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import api.IEntity;
import api.IEntitySystem;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import model.component.movement.Orientation;
import model.component.movement.Position;
import model.component.physics.Collision;
import view.utilities.SpriteUtilities;

/**
 * @author Bruna
 */
public class EnvironmentHelperMethods {
	
    private static final String SELECT_EFFECT = "-fx-effect: dropshadow(three-pass-box, rgba(22, 0, 255, 0.8), 10, 0, 0, 0)",
            NO_SELECT_EFFECT = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0), 0, 0, 0, 0)";
    private static Set<IEntity> selectedSprites = new HashSet<>();
	
    public Set<IEntity> getSelected () {
        return selectedSprites;
    }

    public static void dehighlight (IEntity e) {
        selectedSprites.remove(e);
        SpriteUtilities.getImageView(e).setStyle(NO_SELECT_EFFECT);
    }

    public static void highlight (IEntity e) {
        selectedSprites.add(e);
        SpriteUtilities.getImageView(e).setStyle(SELECT_EFFECT);
    }

    public static void toggleHighlight (IEntity entity) {
        if (!selectedSprites.contains(entity)) {
            System.out.println("highlight");
            highlight(entity);
        } else {
            System.out.println("dehighlight");
            dehighlight(entity);
        }
    }
    
    public static ImageView getUpdatedImageView (IEntity e) {
        Position pos = e.getComponent(Position.class);
        ImageView imageView = SpriteUtilities.getImageView(e);
        imageView.setId(e.getID());
        imageView.setTranslateX(pos.getX());
        imageView.setTranslateY(pos.getY());
        if (e.hasComponent(Orientation.class)) {
            Orientation o = e.getComponent(Orientation.class);
            if(o.getOrientationString().equals("west")) {
                imageView.setScaleX(-1);
            }
        }
        return imageView;
    }

    public static Collection<Shape> getCollisionShapes (IEntity e) {
        List<Collision> collisions = e.getComponentList(Collision.class);
        Collection<Shape> shapes = new ArrayList<>();
        if (collisions.isEmpty()) {
            return shapes;
        }
        Collection<Bounds> bounds = collisions.stream().map(c -> c.getMask()).collect(Collectors.toCollection(ArrayList::new));
        for (Bounds b : bounds) {
            if (b == null) {
                continue;
            }
            Shape r = new Rectangle(b.getMinX(), b.getMinY(), b.getWidth(), b.getHeight());
            r.setFill(Color.TRANSPARENT);
            r.setStroke(Color.RED);
            r.setStrokeWidth(2);
            shapes.add(r);
        }
        return shapes;
    }

    public static void startTimeline (double delay, EventHandler<ActionEvent> step) {
        KeyFrame frame = new KeyFrame(Duration.millis(delay), step);
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    public static IEntitySystem sendToFront (IEntity e, IEntitySystem iEntitySystem) {
        if (iEntitySystem.removeEntity(e.getID()) != null) {
            iEntitySystem.addEntity(e);
        }
        return iEntitySystem;
    }

    public static IEntitySystem sendToBack (IEntity e, IEntitySystem iEntitySystem) {
        if (iEntitySystem.removeEntity(e.getID()) != null) {
            iEntitySystem.getAllEntities().add(0, e);
        }
        return iEntitySystem;
    }

    public static IEntitySystem sendForward (IEntity e, IEntitySystem iEntitySystem) {
        int index = iEntitySystem.getAllEntities().indexOf(e) + 1;
        if (iEntitySystem.removeEntity(e.getID()) != null) {
            if (index < iEntitySystem.getAllEntities().size()) {
                iEntitySystem.getAllEntities().add(index, e);
            } else {
                iEntitySystem.getAllEntities().add(e);
            }
        }
        return iEntitySystem;
    }

    public static IEntitySystem removeFromDisplay (IEntity entity, IEntitySystem iEntitySystem) {
        iEntitySystem.removeEntity(entity.getID());
        return iEntitySystem;
    }

    public static IEntitySystem sendBackward (IEntity e, IEntitySystem iEntitySystem) {
        int index = iEntitySystem.getAllEntities().indexOf(e) - 1;
        if (iEntitySystem.removeEntity(e.getID()) != null) {
            if (index >= 0) {
                iEntitySystem.getAllEntities().add(index, e);
            } else {
                iEntitySystem.getAllEntities().add(0, e);
            }
        }
        return iEntitySystem;
    }

}
