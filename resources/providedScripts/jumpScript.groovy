package providedScripts

import groovy.transform.BaseScript
import groovy.transform.Field
import model.component.movement.Velocity
import model.component.movement.Position

@BaseScript ScriptHelpers ScriptHelpers

/**
 * Jumps a sprite
 * @author Anirudh Jonnavithula
 */

// Parameter: velocityY: what to change y velocity to
@Field Double velocityYField = containsVariable("velocityY") ? getDouble("velocityY") : 0.0;

def move = { entity ->
    if (entity.hasComponent(Velocity.class)) {
    	Position position = entity.getComponent(Position.class);
    	position.setXY(position.getX(), position.getY()-10);
        Velocity velocity = entity.getComponent(Velocity.class);
        velocity.setVXY(velocity.getVX(), velocityYField);
    }
}

workOnEntities(move);