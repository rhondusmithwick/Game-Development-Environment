package groovyScripts

import model.component.movement.Position

/**
 * Created by carolyn on 4/13/16.
 * @author Carolyn Yao, Anirudh Jonnavithula
 */

import model.component.movement.Velocity;
import model.component.character.Health
import model.component.physics.Collision;


velocity = universe.getEntity(entityID).getComponent(Velocity.class);
position = universe.getEntity(entityID).getComponent(Position.class);
//position.setX(position.getX()-30);
velocity.setVXY(-100, velocity.getVY());
//System.out.println("colliding: " + universe.getEntitiesWithName("Anolyn").get(0).getComponent(Collision.class).getCollidingIDs());