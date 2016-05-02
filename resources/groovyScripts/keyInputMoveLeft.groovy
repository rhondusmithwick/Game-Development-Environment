package groovyScripts

import model.component.movement.Position
import model.component.movement.Velocity

/**
 * Created by carolyn on 4/13/16.
 * @author Carolyn Yao, Anirudh Jonnavithula
 */
velocity = universe.getEntitiesWithName(entityName).get(0).getComponent(Velocity.class);
position = universe.getEntitiesWithName(entityName).get(0).getComponent(Position.class);
//position.setX(position.getX()-30);
velocity.setVXY(-100, velocity.getVY());
//System.out.println("colliding: " + universe.getEntitiesWithName("Anolyn").get(0).getComponent(Collision.class).getCollidingIDs());