package providedScripts

import groovy.transform.BaseScript
import groovy.transform.Field
import model.component.movement.Position

@BaseScript ScriptHelpers ScriptHelpers

/**
 * Instantly move an entity.
 * @author Rhondu Smithwick
 */
// Parameter: newX: new x value
// Parameter: newY: new y value
@Field Double newXField = containsVariable("newX") ? getDouble("newX") : null;
@Field Double newYField = containsVariable("newY") ? getDouble("newY") : null;

def move = { entity ->
    if (entity.hasComponent(Position.class)) {
        Position position = entity.getComponent(Position.class);
        if (newXField != null) {
            position.setX(newXField);
        }
        if (newYField != null) {
            position.setY(newYField);
        }
    }
}

workOnEntities(move);

