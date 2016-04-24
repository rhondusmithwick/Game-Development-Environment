package groovyScripts

/**
 * Created by ajonnav on 4/22/16.
 * @author Anirudh Jonnavithula
 */

import model.component.movement.Velocity;
import model.component.physics.Gravity;

universe.getEntitiesWithName("Anolyn").get(0).getComponent(Velocity.class).setVXY(10,10);