package model.events;

import api.IEntity;
import api.IEventListener;
import model.component.movement.Velocity;

@Deprecated
public class MoveEntityLeft implements IEventListener {

	private IEntity myEntity;

	/**
	 * Constructor that sets the entity to be moved left by this listener
	 * 
	 * @param entity
	 */
	public MoveEntityLeft(IEntity entity) {
		this.myEntity = entity;
	}

	@Override
	public void handleEvent() {
		myEntity.getComponent(Velocity.class).setSpeed(-50);
		System.out.println(myEntity.getComponent(Velocity.class));
	}
}
