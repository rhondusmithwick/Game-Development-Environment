package events;

import api.ISerializable;

public class EventContainer implements ISerializable {
	private Trigger trigger;
	private Action action;
	
	public EventContainer(Trigger t, Action a) {
		trigger = t;
		action = a;
	}

	public Action getAction() {
		return action;
	}

	public Trigger getTrigger() {
		return trigger;
	}
}