package providedScripts

import groovy.transform.BaseScript
import groovy.transform.Field
import model.component.movement.Position

@BaseScript ScriptHelpers ScriptHelpers

/**
 * Created by rhondusmithwick on 4/30/16.
 * @author Rhondu Smithwick
 */

@Field Double newX = containsVariable("newX") ? (Double) getVariable("newX") : null;
@Field Double newY = containsVariable("newY") ? (Double) getVariable("newY") : null;

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

