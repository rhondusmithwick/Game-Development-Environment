package model.events;

import com.google.common.eventbus.Subscribe;

import api.IEntity;
import api.IEvent;
import api.IEventListener;
import model.component.movement.Velocity;

public class MoveEntityLeft implements IEventListener {
	
	private IEntity myEntity;
	
	/**
	 * Constructor that sets the entity to
	 * be moved left by this listener
	 * @param entity
	 */
	public MoveEntityLeft(IEntity entity) {
		this.myEntity = entity;
	}
	
	public void handleEvent() {
		//TODO:  fix "getComponent" -- currently results in error
//		myEntity.getComponent(Velocity.class).setSpeed(-50);
	}
}
