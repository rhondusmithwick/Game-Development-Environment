package groovyScripts

import api.IEntity
import api.ILevel
import groovy.transform.Field
import model.component.visual.AnimatedSprite

/**
 * Created by rhondusmithwick on 4/30/16.
 * @author Rhondu Smithwick
 */
@Field ILevel universe = (ILevel) binding.getVariable("universe");

void animate(IEntity entity) {
    if (entity.hasComponent(AnimatedSprite.class)) {
        AnimatedSprite animatedSprite = entity.getComponent(AnimatedSprite.class);
        String animationName = (String) binding.getVariable("animationName");
        animatedSprite.createAndPlayAnimation(animationName);
    }
}

void runAllEntities() {
    List<IEntity> entities = new ArrayList<>();
    if (containsVariable("entityID")) {
        String entityID = (String) binding.getVariable("entityID");
        entities.add(universe.getEntity(entityID));
    }
    if (containsVariable("entityName")) {
        String entityName = (String) binding.getVariable("entityName");
        entities.addAll(universe.getEntitiesWithName(entityName));
    }
    for (IEntity entity : entities) {
        animate(entity);
    }
}

boolean containsVariable (String variableName) {
    return binding.variables.containsKey(variableName)
}

runAllEntities()
