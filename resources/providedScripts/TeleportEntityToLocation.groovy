package providedScripts

import groovy.transform.BaseScript
import groovy.transform.Field
import model.component.movement.Position

@BaseScript ScriptHelpers ScriptHelpers

/**
 * Instantly move an entity.
 * @author Rhondu Smithwick
 */

@Field Double newX = containsVariable("newX") ? getDouble("newX") : null;
@Field Double newY = containsVariable("newY") ? getDouble("newY") : null;

def move = { entity ->
    if (entity.hasComponent(Position.class)) {
        Position position = entity.getComponent(Position.class);
        if (newX != null) {
            position.setX(newX);
        }
        if (newY != null) {
            position.setY(newY);
        }
    }
}

workOnEntities(move);

