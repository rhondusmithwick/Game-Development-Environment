package gamescripts

import api.IGameScript
import api.ILevel
import api.IPhysicsEngine
import api.ISystemManager
/**
 * Created by Tom on 5/1/2016.
 */
public class PhysicsGameScript implements IGameScript {

    private IPhysicsEngine physicsEngine;
    private ILevel level;

    @Override
    void init(GroovyShell shell, ISystemManager game) {
        physicsEngine = game.getLevel().getPhysicsEngine();
        level = game.getLevel();
    }

    @Override
    void update(double dt) {
        physicsEngine.update(level, dt);
    }

}
