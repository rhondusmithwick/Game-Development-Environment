package groovyScripts

/**
 * Created by carolyn on 4/12/16.
 * @author Carolyn Yao
 */

import model.component.movement.Position;
import model.component.character.Health;

position = universe.getEntitiesWithName("Anolyn").get(0)getComponent(Position.class);
health = universe.getEntitiesWithName("Anolyn").get(0).getComponent(Health.class);
System.out.println(position.getX());
if (position.getX() > 500) {
	health.setHealth(0);
	System.out.println("HEY " + health.getHealth());
}
//position.setX(position.getX() + moved);