package api;

import javafx.geometry.Point2D;

/**
 * Interface for the physics engine
 *
 * @author Tom Wu
 */
public interface IPhysicsEngine {
    /**
     * Update Positions of IEntities in universe based on dt and their Velocities
     *
     * @param universe IEntitySystem containing IEntities with both Positions and Velocities
     * @param dt       duration of update in seconds
     */
    void update (ILevel universe, double dt);

}