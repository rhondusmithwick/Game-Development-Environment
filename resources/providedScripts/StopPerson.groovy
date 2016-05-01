package providedScripts

import api.IEntity
import groovy.transform.BaseScript
import model.component.movement.Velocity

/**
 * Created by ajonnav on 4/27/16.
 * @author Anirudh Jonnavithula
 */

@BaseScript ScriptHelpers ScriptHelpers

void resetVelocity (IEntity entity) {
    Velocity velocity = entity.getComponent(Velocity.class);
    velocity.setVXY(0, 0);
}

for (IEntity entity: getEntitiesWithNamesAndIDs()) {
    resetVelocity(entity);
}