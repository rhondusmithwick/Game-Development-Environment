package api;

import java.util.List;
import javafx.animation.Timeline;


/**
 * Created by rhondusmithwick on 3/31/16.
 *
 * @author Rhondu Smithwick
 */
public interface ISystemManager {
    /**
     * This will pause the game loop.
     */
    void pauseLoop ();

    /**
     * This will build the game's loop.
     *
     * @return the game's loop
     */
    Timeline buildLoop ();

    /**
     * This will step the game's loop.
     */
    void step ();

    /**
     * Get all systems.
     *
     * @return a list of the systems
     */
    @Deprecated
    List<ISystem> getSystems ();

    IEntitySystem getEntitySystem ();

    IEventSystem getEventSystem ();
}
