package groovyScripts

import api.IEntity
import groovy.transform.BaseScript
import groovy.transform.Field

@BaseScript ScriptHelpers ScriptHelpers

/**
 * Created by rhondusmithwick on 4/8/16.
 * @author Rhondu Smithwick
 */

import model.component.movement.Position;

@Field Double movedX = (Double) getVariable("movedX");
@Field Double movedY = (Double) getVariable("movedY");


void move(IEntity entity) {
    Position position = entity.getComponent(Position.class);
    position.setX(position.getX() + movedX);
    position.setY(position.getY() + movedY);
}

for (IEntity entity: getEntitiesWithNamesAndIDs()) {
    move(entity);
}