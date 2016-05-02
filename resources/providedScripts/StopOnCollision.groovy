package providedScripts

import groovy.transform.BaseScript
import groovy.transform.Field
import model.component.movement.Velocity
import model.component.physics.Collision

@BaseScript ScriptHelpers ScriptHelpers

/**
 * Stops on collision with a given sprite ID
 * @author Anirudh Jonnavithula
 */

// Parameter: stopID: the spriteID that should stop the other sprite 
@Field String stopIDField = containsVariable("stopID") ? getString("stopID") : "";

def stop = { entity ->
	if (!stopIDField.equals("")) {
		if(entity.getComponent(Collision.class).getCollidingIDs().contains(stopIDField)) {
			Velocity velocity = entity.getComponent(Velocity.class);
			velocity.setVXY(velocity.getVX(), 0);
		}
	}
}

workOnEntities(stop);