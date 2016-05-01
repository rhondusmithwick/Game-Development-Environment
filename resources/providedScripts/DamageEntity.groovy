package providedScripts

/**
 * Created by cyao42 on 5/1/2016.
 */
import groovy.transform.BaseScript
import groovy.transform.Field
import model.component.movement.Position
@BaseScript ScriptHelpers ScriptHelpers

/**
 * Kill an Entity when its health reaches 0.
 * @author Carolyn Yao
 */

@Field Double movedX = containsVariable("movedX") ? (Double) getVariable("movedX") : 0.0;
@Field Double movedY = containsVariable("movedY") ? (Double) getVariable("movedY") : 0.0;


def damage = { entity ->


    if (entity.hasComponent(Attack.class)) {
        Position position = entity.getComponent(Position.class);
        position.setX(position.getX() + movedX);
        position.setY(position.getY() + movedY);
    }
}

workOnEntities(damage);