package groovyScripts

import model.component.character.Health
import model.component.movement.Position

/**
 * Created by carolyn on 4/12/16.
 * @author Carolyn Yao
 */
position = universe.getEntitiesWithName("Anolyn").get(0) getComponent(Position.class);
health = universe.getEntitiesWithName("Anolyn").get(0).getComponent(Health.class);
//System.out.println(position.getX());
if (position.getX() > 500) {
    health.setHealth(0);
}
//position.setX(position.getX() + moved);