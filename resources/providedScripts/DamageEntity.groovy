package providedScripts

import api.IEntity

/**
 * Created by cyao42 on 5/1/2016.
 */
import groovy.transform.BaseScript
import groovy.transform.Field
import model.component.character.Attack
import model.component.movement.Position
import model.component.physics.Collision

@BaseScript ScriptHelpers ScriptHelpers

/**
 * Kill an Entity when its health reaches 0.
 * @author Carolyn Yao
 */

@Field Double damageRatio = containsVariable("damageRatio") ? (Double) getVariable("damageRatio") : 0.0;


def damage = { entity ->
    if (entity.hasComponent(Attack.class)) {
        Collision collision = entity.getComponent(Collision.class);
        String attacked = collision.getCollidingIDs();
        // get the ID's of all the colliding entities


    }
}

workOnEntities(damage);