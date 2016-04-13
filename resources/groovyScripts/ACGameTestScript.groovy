package groovyScripts

/**
 * Created by carolyn on 4/12/16.
 * @author Carolyn Yao
 */

import model.component.movement.Position;
import model.component.character.Health;

<<<<<<< HEAD
position = entity.getComponent(Position.class);
health = entity.getComponent(Health.class);

//def moved = $1 as double;

//if (position.getX() > 500) {
	health.setHealth(0);
	System.out.println(health.getHealth());
//}
=======
position = universe.getEntitiesWithName("Anolyn").get(0)getComponent(Position.class);
health = universe.getEntitiesWithName("Anolyn").get(0).getComponent(Health.class);
System.out.println(position.getX());
if (position.getX() > 500) {
	health.setHealth(0);
	System.out.println("HEY " + health.getHealth());
}
>>>>>>> 4609cd144e75fde07f63eb0cd2fd3e2f3fc53d50
//position.setX(position.getX() + moved);