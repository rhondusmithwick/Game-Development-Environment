package providedScripts

import groovy.transform.BaseScript
import groovy.transform.Field
import model.component.visual.AnimatedSprite

@BaseScript ScriptHelpers ScriptHelpers

/**
 * Animates sprites.
 * @author Rhondu Smithwick
 */
// Parameter: animationName is what the animation will be

@Field String animationNameField = containsVariable("animationName") ? (String) getVariable("animationName") : "";

def animate = { entity ->
    System.out.println("here");
    if (entity.hasComponent(AnimatedSprite.class)) {
        AnimatedSprite animatedSprite = entity.getComponent(AnimatedSprite.class);
        if (animatedSprite.hasAnimation(animationNameField)) {
            animatedSprite.createAndPlayAnimation(animationNameField);
        }
    }
}

workOnEntities(animate);