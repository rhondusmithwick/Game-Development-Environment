package groovyScripts

import model.component.movement.Velocity

/**
 * Created by ajonnav on 4/16/16.
 * @author Anirudh Jonnavithula
 */
velocity = universe.getEntity(EntityID).get(0) getComponent(Velocity.class);
velocity.setVXY(velocity.getVX(), velocity.getVY() - 10);