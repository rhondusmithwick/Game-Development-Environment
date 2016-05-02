package providedScripts

import groovy.transform.BaseScript
import model.component.movement.Velocity

/**
 * Stops a person.
 * @author Anirudh Jonnavithula, Rhondu Smithwick
 */

@BaseScript ScriptHelpers ScriptHelpers

def resetVelocity = { entity ->
    if (entity.hasComponent(Velocity.class)) {
        Velocity velocity = entity.getComponent(Velocity.class);
        velocity.setVXY(0, 0);
    }
}

workOnEntities(resetVelocity);