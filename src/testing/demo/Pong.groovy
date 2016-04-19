package testing.demo;

import api.IEntitySystem
import api.ISystemManager

/**
 * 
 * @author Tom
 *
 */
public class Pong {

	//	private Group root;

	public Pong(GroovyShell shell, ISystemManager model) {
		// TODO: figure out why these don't work
		//		this.engine.put("game", this.model);
		//		this.engine.put("universe", this.model.getEntitySystem());
		//		this.engine.put("demo", new GroovyDemoTest());
		shell.setVariable("game", model);
		IEntitySystem universe = model.getEntitySystem();
		shell.setVariable("universe", universe);
		GroovyDemoTest test = new GroovyDemoTest();
		shell.setVariable("demo", test);
	}

	public void init() {

	}

	public void update(double dt) {
		// TODO: call under level hierarchy
		//		Collection<IEntity> entities = universe.getEntitiesWithComponents(Position.class, ImagePath.class);
	}
}
