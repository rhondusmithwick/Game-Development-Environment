package testing.demo;

import javafx.scene.Group
import javafx.scene.image.ImageView

import javax.script.ScriptEngine

import model.component.movement.Orientation
import model.component.movement.Position
import model.component.visual.ImagePath
import model.entity.Entity
import model.physics.PhysicsEngine
import usecases.SystemManager
import api.IEntity
import api.IEntitySystem
import api.IPhysicsEngine
import api.ISystemManager

/**
 * 
 * @author Tom
 *
 */
public class Pong {

	private Group root;
	private ScriptEngine engine;
	private ISystemManager model = new SystemManager();
	private IPhysicsEngine physics = new PhysicsEngine();

	public Pong(Group root, GroovyShell shell) {
		this.root = root;

		// TODO: figure out why these don't work
		//		this.engine.put("game", this.model);
		//		this.engine.put("universe", this.model.getEntitySystem());
		//		this.engine.put("demo", new GroovyDemoTest());
		shell.setVariable("game", this.model);
		IEntitySystem universe = this.model.getEntitySystem();
		shell.setVariable("universe", universe);
		shell.setVariable("demo", new GroovyDemoTest());

		IEntity platform = new Entity();
		// TODO: add Collision component to platform, make sure it's immovable
		//		platform.addComponents(new Collision());
		universe.addEntity(platform);
	}

	public void update(double dt) {
		// simulate
		model.step(dt);

		IEntitySystem universe = model.getEntitySystem();
		Collection<IEntity> entities = universe.getEntitiesWithComponents(Position.class, ImagePath.class);

		// render
		root.getChildren().clear();
		for (IEntity e : entities) { // TODO: figure out why lambda doesn't work
			Position pos = e.getComponent(Position.class);
			ImagePath display = e.getComponent(ImagePath.class);
			ImageView imageView = display.getImageView();

			imageView.setTranslateX(pos.getX());
			imageView.setTranslateY(pos.getY());

			if(e.hasComponent(Orientation.class)) {
				Orientation o = e.getComponent(Orientation.class);
				imageView.setRotate(o.getOrientation());
			}
			if (!root.getChildren().contains(imageView))
				root.getChildren().add(imageView);
		}
	}

}
