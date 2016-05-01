package providedScripts

import groovy.transform.BaseScript
import model.component.movement.Velocity

/**
 * Created by ajonnav on 4/27/16.
 * @author Anirudh Jonnavithula
 */

@BaseScript ScriptHelpers ScriptHelpers

def resetVelocity = { entity ->
    if (entity.hasComponent(Velocity.class)) {
        Velocity velocity = entity.getComponent(Velocity.class);
        velocity.setVXY(0, 0);
    }
}

workOnEntities(resetVelocity);