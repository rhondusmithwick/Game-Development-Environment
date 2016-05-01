package view.utilities;

import api.IComponent;
import api.IEntity;
import api.ISerializable;
import model.component.movement.Position;
import model.component.visual.Sprite;
import model.entity.Entity;

public class EntityCopier {
    private EntityCopier () {
    }

    /**
     * Creates an IEntity copy of the given IEntity with the same specs,
     * components, and component values.
     *
     * @param IEntity entity: given IEntity to copy
     * @return IEntity newEntity: returned copy of the given IEntity
     */

    public static IEntity copyEntity (IEntity entity) {
//        IEntity newEntity = new Entity(entity.getName());
//        newEntity.setSpecs(entity.getSpecs());
//        for (IComponent component : entity.getAllComponents()) {
//            newEntity.addComponent(component.clone(component.getClass()));
//            componentInitialization(newEntity, entity);
//        }
        IEntity newEntity = entity.clone(Entity.class);
        newEntity.regenerateID();
        return newEntity;
    }

    @Deprecated
    private static void componentInitialization (IEntity newEntity, IEntity oldEntity) {
        if (newEntity.hasComponent(Position.class)) {
            newEntity.removeComponent(Position.class);
            Position newPos = new Position();
            newEntity.forceAddComponent(newPos, true);
        }
        if (newEntity.hasComponent(Sprite.class)) {
            newEntity.removeComponent(Sprite.class);
            Sprite newPath = new Sprite(oldEntity.getComponent(Sprite.class).getImagePath());
            newEntity.forceAddComponent(newPath, true);
        }
    }
}
