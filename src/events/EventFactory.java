package events;

import java.io.File;
import java.io.IOException;
import utility.Pair;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import voogasalad.util.reflection.Reflection;

/***
 * @author Carolyn Yao, Anirudh Jonnavithula
 *  factory to create triggers and events using duvall's reflection utility.
 */

public final class EventFactory {

    private final String eventsDirectoryPath = "events.";

	public Pair<Trigger, Action> createEvent(String triggerName, String scriptPath, Object... args) {
		Trigger trigger = createTrigger(eventsDirectoryPath+triggerName, args);
		Action action = new Action(scriptPath);
		return new Pair<Trigger, Action>(trigger, action);
	}

    public Pair<Trigger, Action> createEvent(Trigger trigger, Action action) {
        return new Pair<Trigger, Action>(trigger, action);
    }

    public Action createAction(Object... args) {
        return (Action) Reflection.createInstance(eventsDirectoryPath+"Action", args);
    }

    public Trigger createTrigger(String className, Object... args) {
        Trigger trigger = (Trigger) Reflection.createInstance(eventsDirectoryPath+className, args);
        switch (className) {
            case "KeyTrigger":
                trigger = (KeyTrigger) trigger;
                break;
            case "PropertyTrigger":
                trigger = (PropertyTrigger) trigger;
                break;
            case "TimeTrigger":
                trigger = (TimeTrigger) trigger;
                break;
        }
        return trigger;
    }
}
