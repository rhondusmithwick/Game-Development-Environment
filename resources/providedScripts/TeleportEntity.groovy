package providedScripts

import groovy.transform.BaseScript
import groovy.transform.Field
import model.component.movement.Position

@BaseScript ScriptHelpers ScriptHelpers


/**
 * Move an Entity by the provided X and Y.
 * @author Rhondu Smithwick
 */
// Parameter: movedX: how much to move in x direction
// Parameter: movedY: how much to move in y direction

@Field Double movedXField = containsVariable("movedX") ? getDouble("movedX") : 0.0;
@Field Double movedYField = containsVariable("movedY") ? getDouble("movedY") : 0.0;


def move = { entity ->
    if (entity.hasComponent(Position.class)) {
        Position position = entity.getComponent(Position.class);
        position.setX(position.getX() + movedXField);
        position.setY(position.getY() + movedYField);
    }
}
workOnEntities(move);