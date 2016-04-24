package groovyScripts

/**
 * Created by ajonnav on 4/22/16.
 * @author Anirudh Jonnavithula
 */

import model.component.physics.Gravity;

universe.getEntitiesWithName("Anolyn").get(0).addComponent(new Gravity(5000));