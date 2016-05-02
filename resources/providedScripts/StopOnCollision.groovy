package providedScripts

import groovy.transform.BaseScript
import groovy.transform.Field
import model.component.movement.Position
import model.component.movement.Velocity
import model.component.movement.MovementStatus
import model.component.physics.Collision
import model.component.visual.Sprite

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
			//!entity.getComponent(MovementStatus.class).getMovementStatus().equals("jumping")) {
			Velocity velocity = entity.getComponent(Velocity.class);
			velocity.setVXY(velocity.getVX(), 0);
			/*Position position = entity.getComponent(Position.class);
			position.setXY(position.getX(), universe.getEntitySystem().getEntity(stopIDField).getComponent(Position.class).getY() - 100);*/
		}
	}
}

workOnEntities(stop);