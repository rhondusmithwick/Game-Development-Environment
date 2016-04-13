package groovyScripts

/**
 * Created by ajonnav on 4/12/16.
 * @author Anirudh Jonnavithula
 */

import model.component.movement.Position;
import model.component.character.Health;

position = entity.getComponent(Position.class);

xCoordinate = position.getProperties().get(0);
yCoordinate = position.getProperties().get(1);

if(xCoordinate<0||xCoordinate>500||yCoordinate<0||yCoordinate>500) {
	entity.getComponent(Health.class).setHealth(entity.getComponent(Health.class).getHealth() - 40);
}