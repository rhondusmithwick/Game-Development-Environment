package api;

import javafx.animation.Timeline;

import java.util.List;

/**
 * Created by rhondusmithwick on 3/31/16.
 *
 * @author Rhondu Smithwick
 */
public interface ISystemManager {
    /**
     * This will pause the game loop.
     */
    void pauseLoop();

    /**
     * This will build the game's loop.
     *
     * @return the game's loop
     */
    Timeline buildLoop();

    /**
     * This will step the game's loop.
     */
    void step();

    /**
     * Get all systems.
     *
     * @return a list of the systems
     */
    List<ISystem> getSystems();

}
