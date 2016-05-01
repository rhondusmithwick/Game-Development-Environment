package providedScripts

import api.IEntity
import groovy.transform.BaseScript
import groovy.transform.Field
import model.component.movement.Velocity
import providedScripts.ScriptHelpers

@BaseScript ScriptHelpers ScriptHelpers

/**
 * Created by rhondusmithwick on 4/30/16.
 * @author Rhondu Smithwick
 */


@Field Double movedX = containsVariable("velocityX") ? (Double) getVariable("velocityX") : 0.0;
@Field Double movedY = containsVariable("velocityY") ? (Double) getVariable("velocityY") : 0.0;

void move(IEntity entity) {
    Velocity velocity = entity.getComponent(Velocity.class);
    velocity.setVXY(movedX, movedY);
}

for (IEntity entity: getEntitiesWithNamesAndIDs()) {
    move(entity);
}
