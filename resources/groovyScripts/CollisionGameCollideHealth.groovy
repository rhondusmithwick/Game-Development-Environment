package groovyScripts

import model.component.character.Health
import model.component.movement.Position
import model.component.physics.Collision
import model.component.visual.Sprite

/**
 * Created by cyao42 on 4/22/2016.
 */

charEntity = universe.getEntitiesWithName("Anolyn").get(0);
collision = charEntity.getComponent(Collision.class);
if (!collision.getCollidingIDs().equals("")) {
    health = universe.getEntitiesWithName("Anolyn").get(0).getComponent(Health.class);
    health.setHealth(0);
}
else {
	health = universe.getEntitiesWithName("Anolyn").get(0).getComponent(Health.class);
	health.setHealth(100);
}