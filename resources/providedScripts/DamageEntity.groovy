package providedScripts

import api.IEntity

/**
 * Created by cyao42 on 5/1/2016.
 */
import groovy.transform.BaseScript
import groovy.transform.Field
import model.component.character.Attack
import model.component.character.Defense
import model.component.character.Health
import model.component.movement.Position
import model.component.physics.Collision

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
    if (entity.hasComponent(Attack.class)) {
        Collision collision = entity.getComponent(Collision.class);
        String[] attacked = collision.getCollidingIDs().split("~");
        for (String colliding: attacked) {
            System.out.println("hhh");
            String entID = colliding.split("_")[0];
            IEntity collidingEntity = universe.getEntity(entID);
            doDamage(entity, collidingEntity);
        }
    }
}

void doDamage(IEntity attackingEntity, IEntity defendingEntity) {
    if (defendingEntity.hasComponent(Health.class)) {
        double defense = 0.0;
        if (defendingEntity.hasComponent(Defense.class)) {
            defense = defendingEntity.getComponent(Defense.class).getDefense();
        }
        double attack = attackingEntity.getComponent(Attack.class).getAttack();
        double damageDone = (attack * attackRatioField) - (defense * defenseRatioField);
        Health health = defendingEntity.getComponent(Health.class);
        health.setHealth(health.getHealth() - damageDone);
    }
}

workOnEntities(damage);