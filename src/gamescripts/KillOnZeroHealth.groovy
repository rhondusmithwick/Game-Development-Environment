package gamescripts

import api.IEntity
import api.IGameScript
import api.ILevel
import api.ISystemManager
import model.component.character.Health
import model.component.physics.Mass
import model.component.visual.AnimatedSprite

/**
 * Created by Tom on 5/2/2016.
 */
public class KillOnZeroHealth implements IGameScript {

    private ILevel level;

    @Override
    public void init(GroovyShell shell, ISystemManager game) {
        level = game.getEntitySystem();
    }

    @Override
    public void update(double dt) {
        for(IEntity entity : level.getEntitiesWithComponents(Health.class)) {
            if(entity.getComponent(Health.class).getHealth()<=0) {
                if(entity.hasComponent(AnimatedSprite.class)) {
                    AnimatedSprite animatedSprite = entity.getComponent(AnimatedSprite.class);
                    if(animatedSprite.hasAnimation("Death")) {
                        animatedSprite.createAndPlayAnimation("Death");
                        if(entity.hasComponent(Mass.class)) {
                            
                        }
                    }
                }
            }
        }
    }

}
