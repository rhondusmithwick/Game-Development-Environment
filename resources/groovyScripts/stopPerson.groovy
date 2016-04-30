package groovyScripts

import model.component.movement.Position

/**
 * Created by ajonnav on 4/27/16.
 * @author Anirudh Jonnavithula
 */

import model.component.movement.Velocity;
import model.component.character.Health;

velocity = universe.getEntitiesWithName("Anolyn").get(0).getComponent(Velocity.class);
position = universe.getEntitiesWithName("Anolyn").get(0)getComponent(Position.class);
velocity.setVXY(0, velocity.getVY());