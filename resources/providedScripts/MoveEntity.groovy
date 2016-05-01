package providedScripts

import groovy.transform.BaseScript
import groovy.transform.Field
import model.component.movement.Velocity

@BaseScript ScriptHelpers ScriptHelpers

/**
 * Movs a sprite using the Physics Engine.
 * @author Rhondu Smithwick
 */

// Parameter: velocityX: what to change x velocity to
// Parameter: velocityY: what to change y velocity to
@Field Double velocityXField = containsVariable("velocityX") ? getDouble("velocityX") : 0.0;
@Field Double velocityYField = containsVariable("velocityY") ? getDouble("velocityY") : 0.0;

def move = { entity ->
    if (entity.hasComponent(Velocity.class)) {
        Velocity velocity = entity.getComponent(Velocity.class);
        velocity.setVXY(velocityXField, velocityYField);
    }
}

workOnEntities(move);