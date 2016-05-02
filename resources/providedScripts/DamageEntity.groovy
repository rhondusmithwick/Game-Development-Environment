package providedScripts

import api.IEntity

/**
 * Created by cyao42 on 5/1/2016.
 */
import groovy.transform.BaseScript
import groovy.transform.Field
import model.component.character.Attack
import model.component.character.Health
import model.component.physics.Collision

@BaseScript ScriptHelpers ScriptHelpers

/**
 * Kill an Entity when its health reaches 0.
 * @author Carolyn Yao
 */

@Field Double damageAmountField = containsVariable("damageAmount") ? getDouble("damageAmount") : 0.0;


def damage = { entity ->
    System.out.println("running damage script");
    Collision collision = entity.getComponent(Collision.class);
    Attack attack = entity.getComponent(Attack.class);
    String[] attacked = collision.getCollidingIDs().split("~");
    if (attacked.length >= 2) {
        System.out.println("This is the colliding ID: " + collision.getCollidingIDs());
        for (String colliding : attacked) {
            System.out.println("this is the entity ID: " + colliding.split("_")[0]);
            if (!colliding.equals("")) {
                String entID = colliding.split("_")[0];
                IEntity collidingEntity = universe.getEntity(entID);
                if (collidingEntity.hasComponent(Health.class)) {
                    Health health = collidingEntity.getComponent(Health.class);
                    health.setHealth(health.getHealth() - attack.getAttack());
                    System.out.println(health.getHealth());
                }
            }
        }
    }
}

workOnEntities(damage);