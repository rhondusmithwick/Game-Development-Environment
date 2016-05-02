package providedScripts

/**
 * Created by cyao42 on 5/2/2016.
 */


import groovy.transform.BaseScript
import groovy.transform.Field
import model.component.character.Health

@BaseScript ScriptHelpers ScriptHelpers

/**
 * Kill an Entity when its health reaches 0.
 * @author Carolyn Yao
 */

@Field String nextLevelPathField = containsVariable("nextLevelPath") ? getVariable("nextLevelPath") : "";

def dieAndMoveLevel = { entity ->
    if (entity.hasComponent(Health.class)) {
        Health health = entity.getComponent(Health.class);
        System.out.println("this character should die");
        if (health.getHealth() <= 0) {
            System.out.println("this character should die");
            if (!nextLevelPathField.equals("")) {
                universe.setLevelOverAndLoadNextLevel(nextLevelPathField);
            }
        }
    }
}

workOnEntities(dieAndMoveLevel);
