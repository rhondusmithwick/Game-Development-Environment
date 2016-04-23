package testing.demo;

import api.ILevel
import model.physics.PhysicsEngine
import api.IGameScript
import api.ILevel
import api.IPhysicsEngine
import api.ISystemManager

/**
 * 
 * @author Tom
 *
 */
public class Pong implements IGameScript {
	private ISystemManager game;
	private ILevel universe;
	private IPhysicsEngine physics = new PhysicsEngine();

	public void init(GroovyShell shell, ISystemManager game) {
		this.game = game;
		this.universe = game.getEntitySystem();

		// TODO: figure out why these don't work
		//		this.engine.put("game", this.model);
		//		this.engine.put("universe", this.model.getEntitySystem());
		//		this.engine.put("demo", new GroovyDemoTest());
		shell.setVariable("game", this.game);
		shell.setVariable("universe", universe);
		shell.setVariable("demo", new GroovyDemoTest());
	}

	public void update(double dt) {
		this.physics.update(universe, dt);
	}
}
