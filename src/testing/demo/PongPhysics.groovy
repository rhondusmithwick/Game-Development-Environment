package testing.demo

import api.IEntity
import api.IEntitySystem
import api.IGameScript
import api.ISystemManager
import model.component.movement.Velocity
import model.component.physics.Collision

/**
 * Created by Tom on 4/29/2016.
 */
class PongPhysics implements IGameScript {

    private IEntitySystem universe;
    private double ballSpeed;

    @Override
    void init(GroovyShell shell, ISystemManager game) {
        universe = game.getLevel().getEntitySystem();
        List<IEntity> balls = universe.getEntitiesWithName("Ball");
        if(!balls.isEmpty() && balls.get(0).hasComponent(Velocity.class)) {
            ballSpeed = balls.get(0).getComponent(Velocity.class).getSpeed();
        }
    }

    @Override
    void update(double dt) {
        List<IEntity> entities = universe.getEntitiesWithName("Ball");
        for(IEntity e : entities) {
            if(e.hasComponents(Collision.class, Velocity.class)) {
                Collision collision = e.getComponent(Collision.class);
                String id = collision.getMaskID();
                String collidingIDs = collision.getCollidingIDs();
                if (id.equals("Ball") && !collidingIDs.isEmpty()) {
                    changeVelocity(e.getComponent(Velocity.class));
                }
            }
        }
    }

    void changeVelocity(Velocity v) {
        double r = Math.random() - 0.5;
        v.setDirection(v.getDirection()+r);
        v.setSpeed(1.05*ballSpeed);
    }

}
