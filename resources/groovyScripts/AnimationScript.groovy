package groovyScripts

import api.IEntity
import model.component.visual.AnimatedSprite

/**
 * Created by rhondusmithwick on 4/30/16.
 * @author Rhondu Smithwick
 */

void animate(IEntity entity) {
    if (entity.hasComponent(AnimatedSprite.class)) {
        AnimatedSprite animatedSprite = entity.getComponent(AnimatedSprite.class);
        animatedSprite.createAndPlayAnimation(animationName);
    }
}

void runAllEntities() {
    List<IEntity> entities = new ArrayList<>();
    if (entityID != null) {
        entities.add(universe.getEntity(entityID));
    }
    if (entityName != null) {
        entities.addAll(universe.getEntitiesWithName(entityName));
    }
    for (IEntity entity: entities) {
        animate(entity);
    }
}

runAllEntities()