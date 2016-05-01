package groovyScripts

import api.IEntity
import groovy.transform.BaseScript
import model.component.movement.Orientation
import model.component.visual.AnimatedSprite
import providedScripts.ScriptHelpers

/**
 * Created by rhondusmithwick on 4/30/16.
 * @author Rhondu Smithwick
 */

@BaseScript ScriptHelpers ScriptHelpers

void ensureDefault(IEntity entity) {
    AnimatedSprite animatedSprite = entity.getComponent(AnimatedSprite.class);
    Orientation orientation = entity.getComponent(Orientation.class);
    animatedSprite.setDefaultAnimation(orientation.getOrientationString() + "Default");
}

for (IEntity entity: universe.getEntitiesWithComponents(AnimatedSprite.class, Orientation.class)) {
    ensureDefault(entity);
}