package model.core

import api.IEntitySystem
import api.IGameScript
import api.ISystemManager
import model.component.movement.Orientation
import model.component.visual.AnimatedSprite

/**
 * Ensures that AnimatedSprite Default Animations are in line with their Orientation.
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
        universe.getEntitiesWithComponents(AnimatedSprite.class, Orientation.class).each(ensureDefault);
    }

    def ensureDefault = { entity ->
        AnimatedSprite animatedSprite = entity.getComponent(AnimatedSprite.class);
        Orientation orientation = entity.getComponent(Orientation.class);
        animatedSprite.setDefaultAnimation(orientation.getOrientationString() + "Default");
    }

}
