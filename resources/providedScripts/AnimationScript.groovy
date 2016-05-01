package providedScripts

import groovy.transform.BaseScript
import groovy.transform.Field
import model.component.visual.AnimatedSprite

@BaseScript ScriptHelpers ScriptHelpers

/**
 * Animates sprites.
 * @author Rhondu Smithwick
 */

@Field String animationName = containsVariable("animationName") ? (String) getVariable("animationName") : "";

def animate = { entity ->
    if (entity.hasComponent(AnimatedSprite.class)) {
        AnimatedSprite animatedSprite = entity.getComponent(AnimatedSprite.class);
        if (animatedSprite.hasAnimation(animationName)) {
            animatedSprite.createAndPlayAnimation(animationName);
        }
    }
}

workOnEntities(animate);