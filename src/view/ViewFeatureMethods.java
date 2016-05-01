package view;

import api.IEntity;
import api.IEntitySystem;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

/**
 * @author Bruna
 *
 */
public class ViewFeatureMethods {


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
