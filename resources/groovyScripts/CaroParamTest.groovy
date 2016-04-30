package groovyScripts

import model.component.physics.Collision

/**
 * Created by cyao42 on 4/30/2016.
 */

charEntity = universe.getEntity("EntityID");
collision = charEntity.getComponent(Collision.class);
if (!collision.getCollidingIDs().equals("")) {
    health = charEntity.getComponent(Health.class);
    health.setHealth(0);
}
else {
    health = charEntity.getComponent(Health.class);
    health.setHealth(100);
}