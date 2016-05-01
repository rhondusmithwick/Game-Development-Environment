package model.core

import api.IEntity
import api.IEntitySystem
import api.IGameScript
import api.ISystemManager
import model.component.movement.Orientation
import model.component.visual.AnimatedSprite

/**
 * Created by rhondusmithwick on 4/30/16.
 * @author Rhondu Smithwick
 */
public class DefaultAnimationUpdater implements IGameScript {

    private ISystemManager game;

    @Override
    public void init(GroovyShell shell, ISystemManager game) {
        this.game = game;
    }

    @Override
    public void update(double dt) {
        IEntitySystem universe = game.getEntitySystem();
        for (IEntity entity: universe.getEntitiesWithComponents(AnimatedSprite.class, Orientation.class)) {
            ensureDefault(entity);
        }
    }

    private static void ensureDefault(IEntity entity) {
        AnimatedSprite animatedSprite = entity.getComponent(AnimatedSprite.class);
        Orientation orientation = entity.getComponent(Orientation.class);
        animatedSprite.setDefaultAnimation(orientation.getOrientationString() + "Default");
    }
}
