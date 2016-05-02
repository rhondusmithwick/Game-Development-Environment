package providedScripts

import groovy.transform.Field
import model.component.character.Health

import groovy.transform.BaseScript
@BaseScript ScriptHelpers ScriptHelpers
/**
 * Created by cyao42 on 5/2/2016.
 */


@Field Double entityHealthField = containsVariable("entityHealth") ? getDouble("entityHealth") : 100.0;

def heal = { entity ->
    if (entity.hasComponent(Health.class)) {
        Health health = entity.getComponent(Health.class);
        System.out.println(health.getHealth() + " turns into " + entityHealthField);
        health.setHealth(entityHealthField);
    }
}

workOnEntities(heal);
