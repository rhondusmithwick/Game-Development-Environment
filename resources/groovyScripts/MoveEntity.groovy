package groovyScripts

import api.IEntity
import groovy.transform.BaseScript
import groovy.transform.Field

@BaseScript ScriptHelpers ScriptHelpers

/**
 * Move an Entity by the provided X and Y.
 * @author Rhondu Smithwick
 */

import model.component.movement.Position;

@Field Double movedX = containsVariable("movedX") ? (Double) getVariable("movedX") : 0.0;
@Field Double movedY = containsVariable("movedY") ? (Double) getVariable("movedY") : 0.0;


void move(IEntity entity) {
    Position position = entity.getComponent(Position.class);
    position.setX(position.getX() + movedX);
    position.setY(position.getY() + movedY);
}

for (IEntity entity: getEntitiesWithNamesAndIDs()) {
    move(entity);
}