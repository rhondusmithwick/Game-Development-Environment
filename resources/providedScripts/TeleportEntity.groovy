package providedScripts

import api.IEntity
import groovy.transform.BaseScript
import groovy.transform.Field
import model.component.movement.Position

@BaseScript ScriptHelpers ScriptHelpers

/**
 * Move an Entity by the provided X and Y.
 * @author Rhondu Smithwick
 */
@Field Double movedX = containsVariable("movedX") ? (Double) getVariable("movedX") : 0.0;
@Field Double movedY = containsVariable("movedY") ? (Double) getVariable("movedY") : 0.0;


def move = { entity ->
    if (entity.hasComponent(Position.class)) {
        Position position = entity.getComponent(Position.class);
        position.setX(position.getX() + movedX);
        position.setY(position.getY() + movedY);
    }
}

workOnEntities(move);