package usecases;

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
	private IEventSystem eventSystem;
	private IEntitySystem universe = new EntitySystem();
	private IPhysicsEngine physics = new PhysicsEngine();
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
}
