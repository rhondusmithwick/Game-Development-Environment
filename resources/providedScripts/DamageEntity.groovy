package providedScripts

import api.IEntity

/**
 * Created by cyao42 on 5/1/2016.
 */
import groovy.transform.BaseScript
import groovy.transform.Field
import model.component.character.Attack
import model.component.character.Health
import model.component.movement.Position
import model.component.physics.Collision

@BaseScript ScriptHelpers ScriptHelpers

/**
 * Kill an Entity when its health reaches 0.
 * @author Carolyn Yao
 */

@Field Double damageAmount = containsVariable("damageAmount") ? (Double) getVariable("damageAmount") : 0.0;


def damage = { entity ->
    if (entity.hasComponent(Attack.class)) {
        Collision collision = entity.getComponent(Collision.class);
//        for (String entID: collision.getCollidingIDs()) {
//            IEntity collidingEntity = universe.getEntity(entID);
//            if (collidingEntity.hasComponent(Health.class)) {
//                Health health = collidingEntity.getComponent(Health.class);
//                health.setHealth(health.getHealth() - damageAmount);
//            }
//        }
        // collision component returns string on getCollidingIDs()
        String[] attacked = collision.getCollidingIDs().split("~");
        for (String colliding: attacked) {
            String entID = colliding.split("_")[0];
            IEntity collidingEntity = universe.getEntity(entID);
            if (collidingEntity.hasComponent(Health.class)) {
                Health health = collidingEntity.getComponent(Health.class);
                health.setHealth(health.getHealth() - damageAmount);
            }
        }
    }
}

workOnEntities(damage);