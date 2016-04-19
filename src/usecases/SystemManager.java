package usecases;

import java.io.IOException;
import java.io.ObjectInputStream;

import api.IEntitySystem;
import api.IEventSystem;
import api.IPhysicsEngine;
import api.ISystemManager;
import events.EventSystem;
import events.InputSystem;
import model.entity.EntitySystem;
import model.physics.PhysicsEngine;

/**
 * Created by rhondusmithwick on 3/31/16.
 *
 * @author Rhondu Smithwick
 */
public class SystemManager implements ISystemManager {
	private transient IEventSystem eventSystem;
	private IEntitySystem universe = new EntitySystem();
	private transient IPhysicsEngine physics = new PhysicsEngine();
	private boolean isRunning = true;

	public SystemManager() {
		this.eventSystem = new EventSystem(universe, new InputSystem(universe));
		// eventSystem.init(universe);
	}

	@Override
	public void pauseLoop() {
		this.isRunning = false;
	}

	@Override
	public void step(double dt) {
		if (this.isRunning) {
			physics.update(universe, dt);
		}
	}

	@Override
	public IEntitySystem getEntitySystem() {
		return this.universe;
	}

	@Override
	public IEventSystem getEventSystem() {
		return this.eventSystem;
	}

	@Override
	public void play() {
		this.isRunning = true;
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		this.eventSystem = new EventSystem(universe, new InputSystem(universe));
		this.physics = new PhysicsEngine();
	}
}
