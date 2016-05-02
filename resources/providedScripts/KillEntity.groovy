package providedScripts

import events.Action

/**
 * Created by cyao42 on 5/1/2016.
 */

import groovy.transform.BaseScript
import groovy.transform.Field
import model.component.character.Health
import model.component.visual.AnimatedSprite

@BaseScript ScriptHelpers ScriptHelpers

/**
 * Kill an Entity when its health reaches 0.
 * @author Carolyn Yao
 */


@Field String animationNameField = containsVariable("animationName") ? getVariable("animationName") : "death";

def die = { entity ->
    if (entity.hasComponent(Health.class)) {
        Health health = entity.getComponent(Health.class);
        if (health.getHealth() <= 0) {
//            if (entity.hasComponent(AnimatedSprite.class)) {
//                AnimatedSprite animatedSprite = entity.getComponent(AnimatedSprite.class);
//                if (animatedSprite.hasAnimation(animationNameField)) {
//                    animatedSprite.createAndPlayAnimation(animationNameField);
//                }
//            }
            setVariable("animationName", animationNameField);
            evaluate(new File("resources/providedScripts/AnimationScript.groovy"));
        }
    }
}

workOnEntities(die);