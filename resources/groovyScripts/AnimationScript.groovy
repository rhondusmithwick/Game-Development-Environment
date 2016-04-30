package groovyScripts

import api.IEntity
import groovy.transform.BaseScript
import model.component.visual.AnimatedSprite
@BaseScript ScriptHelpers ScriptHelpers

/**
 * Created by rhondusmithwick on 4/30/16.
 * @author Rhondu Smithwick
 */

void animate(IEntity entity) {
    if (entity.hasComponent(AnimatedSprite.class)) {
        AnimatedSprite animatedSprite = entity.getComponent(AnimatedSprite.class);
        String animationName = (String) getVariable("animationName");
        animatedSprite.createAndPlayAnimation(animationName);
    }
}

for (IEntity entity: getEntitieWithNamesAndIDs()) {
    animate(entity);
}
