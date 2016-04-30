package groovyScripts

import model.component.movement.Position

/**
 * Created by carolyn on 4/13/16.
 * @author Carolyn Yao, Anirudh Jonnavithula
 */

import model.component.movement.Velocity;
import model.component.character.Health;

velocity = universe.getEntity(EntityID).getComponent(Velocity.class);
position = universe.getEntity(EntityID).getComponent(Position.class);
velocity.setVXY(100, velocity.getVY());
