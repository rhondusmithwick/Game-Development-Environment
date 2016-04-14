package usecases;

import java.util.List;

import api.IEntitySystem;
import api.IEventSystem;
import api.IPhysicsEngine;
import api.ISystem;
import api.ISystemManager;
import javafx.animation.Timeline;

import java.util.List;

import model.entity.EntitySystem;
import model.physics.PhysicsEngine;


/**
 * Created by rhondusmithwick on 3/31/16.
 *
 * @author Rhondu Smithwick
 */
public class SystemManager implements ISystemManager {
	// private IEventSystem eventSystem;
	private IEntitySystem universe = new EntitySystem();
	private IPhysicsEngine physics = new PhysicsEngine();

	public SystemManager() {
	}

	@Override
	public void pauseLoop() {

	}

	@Override
	public Timeline buildLoop() {
		return null;
	}

	@Override
	public void step(double dt) {
		physics.update(universe, dt);
	}

	@Override
	public IEntitySystem getEntitySystem() {
		return this.universe;
	}

	@Override
	public IEventSystem getEventSystem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ISystem> getSystems() {
		// TODO Auto-generated method stub
		return null;
	}
}
