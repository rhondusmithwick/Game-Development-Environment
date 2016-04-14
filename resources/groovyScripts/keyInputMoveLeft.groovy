package groovyScripts

/**
 * Created by carolyn on 4/13/16.
 * @author Carolyn Yao, Anirudh Jonnavithula
 */

import model.component.movement.Position;
import model.component.character.Health;

position = universe.getEntitiesWithName("Anolyn").get(0)getComponent(Position.class);
position.setX(position.getX()-10);