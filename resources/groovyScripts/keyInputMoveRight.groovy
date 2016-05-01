package groovyScripts

import model.component.movement.Position
import model.component.movement.Velocity

/**
 * Created by carolyn on 4/13/16.
 * @author Carolyn Yao, Anirudh Jonnavithula
 */
velocity = universe.getEntity(entityID).getComponent(Velocity.class);
position = universe.getEntity(entityID).getComponent(Position.class);
velocity.setVXY(100, velocity.getVY());
