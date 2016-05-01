package groovyScripts

import model.component.character.Health
import model.component.movement.Position
import model.component.physics.Collision
import model.component.visual.Sprite

/**
 * Created by cyao42 on 4/22/2016.
 */

charEntity = universe.getEntity(entityID);
collision = charEntity.getComponent(Collision.class);
if (!collision.getCollidingIDs().equals("")) {
    health = charEntity.getComponent(Health.class);
    health.setHealth(0);
}
else {
	health = charEntity.getComponent(Health.class);
	health.setHealth(100);
}