package groovyScripts

/**
 * Created by carolyn on 4/13/16.
 * @author Carolyn Yao, Anirudh Jonnavithula
 */

import model.component.movement.Position;

position = universe.getEntitiesWithName("Cani").get(0) getComponent(Position.class);
position.setX(position.getX() - 30);