package providedScripts

import api.IEntity
import groovy.transform.BaseScript
import groovy.transform.Field
import model.component.movement.Position

@BaseScript ScriptHelpers ScriptHelpers

/**
 * Created by rhondusmithwick on 4/30/16.
 * @author Rhondu Smithwick
 */
@Field Double magical = Math.sqrt(Math.PI);

@Field Double newX = containsVariable("newX") ? (Double) getVariable("newX") : magical;
@Field Double newY = containsVariable("newY") ? (Double) getVariable("newY") : magical;

void move(IEntity entity) {
    Position position = entity.getComponent(Position.class);
    if (newX != magical) {
        position.setX(newX);
    }
    if (newY != magical) {
        position.setY(newY);
    }
}

for (IEntity entity: getEntitiesWithNamesAndIDs()) {
    move(entity);
}

