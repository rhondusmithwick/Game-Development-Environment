package groovyScripts

import model.component.movement.Velocity

/**
 * Created by ajonnav on 4/22/16.
 * @author Anirudh Jonnavithula
 */
universe.getEntitiesWithName("Anolyn").get(0).getComponent(Velocity.class).setVXY(10, 10);