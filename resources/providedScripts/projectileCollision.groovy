package providedScripts

import api.IEntity
import groovy.transform.BaseScript
import groovy.transform.Field
import model.component.character.Score
import model.component.movement.Velocity
import model.component.movement.Position
import model.component.physics.Collision
import model.component.physics.Gravity
import model.component.visual.Sprite
import model.entity.Entity

@BaseScript ScriptHelpers ScriptHelpers

/**
 * Collision with opposing sprite
 * @author Anirudh Jonnavithula
 */

// Parameter: velocityY: what to change y velocity to
@Field String playerNameField = containsVariable("playerName") ? getString("playerName") : "";
@Field String opposingProjectileNameField = containsVariable("opposingProjectileName") ? getString("opposingProjectileName") : "";

IEntity player = universe.getEntitiesWithName(playerNameField).get(0);
Collision collision = player.getComponent(Collision.class);
String[] attacked = collision.getCollidingIDs().split("~");
for (String colliding : attacked) {
    if (!colliding.equals("")) {
        String entID = colliding.split("_")[0];
        IEntity collidingEntity = universe.getEntity(entID);
        System.out.println(opposingProjectileNameField);
        if(collidingEntity!=null && collidingEntity.getName().equals(opposingProjectileNameField)) {
            universe.removeEntity(collidingEntity.getID());
            if(player.hasComponent(Score.class)) {
                player.getComponent(Score.class).setScore(player.getComponent(Score.class).getScore()+1)
            }
        }
    }
}