package testing.demo

import api.*
import model.component.movement.Velocity
import model.component.physics.Collision
/**
 * Created by Tom on 4/29/2016.
 */
public class PongPhysics implements IGameScript {

//    private final String restrictPaddleXScript = Pong.PATH + "RestrictPaddleX.groovy";
    private IEntitySystem universe;
    private IEventSystem eventSystem;
    private double ballSpeed;

    @Override
    void init(GroovyShell shell, ISystemManager game) {
        universe = game.getLevel().getEntitySystem();
        List<IEntity> balls = universe.getEntitiesWithName("Ball");
        if (!balls.isEmpty() && balls.get(0).hasComponent(Velocity.class)) {
            ballSpeed = balls.get(0).getComponent(Velocity.class).getSpeed();
        }

//        eventSystem = game.getLevel().getEventSystem();
//        restrictPaddleX("LeftPaddle");
//        restrictPaddleX("RightPaddle");
    }

//    private void restrictPaddleX(String name) {
//        IEntity paddle = universe.getEntitiesWithName(name).get(0);
//        String id = paddle.getID();
//        eventSystem.registerEvent(new PropertyTrigger(id, Velocity.class, "YVelocity"), new Action(restrictPaddleXScript));
//    }

    @Override
    void update(double dt) {
        List<IEntity> balls = universe.getEntitiesWithName("Ball");
        for (IEntity e : balls) {
            if (e.hasComponents(Collision.class, Velocity.class)) {
                Collision collision = e.getComponent(Collision.class);
                String collidingIDs = collision.getCollidingIDs();
                if (!collidingIDs.isEmpty()) {
                    changeVelocity(e.getComponent(Velocity.class));
//                    println("\n\nhit\n\n");
                }
            }
        }

        List<IEntity> entities = universe.getAllEntities();
        for(IEntity e : entities) {
            if(e.getName().contains("Paddle")) {
                println(e.getName());
                Velocity v = e.getComponent(Velocity.class);
                v.setVX(0);
            }
        }
    }

    void changeVelocity(Velocity v) {
        double r = Math.random() - 0.5;
        v.setDirection(v.getDirection() + r * 0.2);
        v.setSpeed(1.2 * ballSpeed);
    }

}
