package providedScripts

import api.IEntity
import groovy.transform.BaseScript
import groovy.transform.Field
import model.component.character.Attack
import model.component.character.Defense
import model.component.character.Health
import model.component.physics.Collision


/**
 * Created by cyao42 on 5/1/2016.
 */

@BaseScript ScriptHelpers ScriptHelpers

/**
 * Kill an Entity when its health reaches 0.
 * @author Carolyn Yao
 */

// Parameter: attackRatio ratio of the amount of attack used
// Parameter: defenseRatio: ratio of the amount of defense used

@Field Double attackRatioField = containsVariable("attackRatio") ? getDouble("attackRatio") : 0.5;
@Field Double defenseRatioField = containsVariable("defenseRatio") ? getDouble("defenseRatio") : 0.5;

def damage = { entity ->
    System.out.println("running damage script");
    if (entity.hasComponents(Collision.class, Attack.class)) {
        Collision collision = entity.getComponent(Collision.class);
        String[] attacked = collision.getCollidingIDs().split("~");
        System.out.println("This is the colliding ID: " + collision.getCollidingIDs());
        for (String colliding : attacked) {
            if (!colliding.equals("")) {
                String entID = colliding.split("_")[0];
                System.out.println("this is the entity ID: " + entID);
                IEntity collidingEntity = universe.getEntity(entID);
                doDamage(entity, collidingEntity);
            }
        }
    }
}

void doDamage(IEntity attackingEntity, IEntity defendingEntity) {
    System.out.println("doing damage now");
    if (defendingEntity.hasComponent(Health.class)) {
        double defense = 0.0;
        if (defendingEntity.hasComponent(Defense.class)) {
            defense = defendingEntity.getComponent(Defense.class).getDefense();
        }
        double attack = attackingEntity.getComponent(Attack.class).getAttack();
        double damageDone = (attack * attackRatioField) - (defense * defenseRatioField);
        Health health = defendingEntity.getComponent(Health.class);
        health.setHealth(health.getHealth() - damageDone);
        System.out.println(health.getHealth());
    }
}

workOnEntities(damage);