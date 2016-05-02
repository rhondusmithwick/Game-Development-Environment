package providedScripts

/**
 * Created by cyao42 on 5/1/2016.
 */

import groovy.transform.BaseScript
import model.component.character.Health

@BaseScript ScriptHelpers ScriptHelpers

/**
 * Kill an Entity when its health reaches 0.
 * @author Carolyn Yao
 */


def die = { entity ->
    if (entity.hasComponent(Health.class)) {
        Health health = entity.getComponent(Health.class);
        if (health.getHealth() <= 0) {
            universe.removeEntity(entityID);
        }
    }
}

workOnEntities(die);