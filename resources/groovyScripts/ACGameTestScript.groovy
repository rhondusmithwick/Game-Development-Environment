package groovyScripts

/**
 * Created by carolyn on 4/12/16.
 * @author Carolyn Yao
 */

import model.component.movement.Position;
import model.component.character.Health;

position = entity.getComponent(Position.class);
health = entity.getComponent(Health.class);

def moved = $1 as double;

if (position.getX() > 500) {
	health.setHealth(0);
	System.out.println(health.getHealth());
}
//position.setX(position.getX() + moved);