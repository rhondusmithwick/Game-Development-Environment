package groovyScripts

/**
 * Created by ajonnav on 4/16/16.
 * @author Anirudh Jonnavithula
 */

import model.component.movement.Velocity;
import model.component.character.Health;
import model.physics.PhysicsEngine;

velocity = universe.getEntityWithID(id).get(0)getComponent(Velocity.class);
velocity.setVXY(velocity.getVX(), velocity.getVY()-10);