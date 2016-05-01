package events;
import java.util.HashMap;
import java.util.Map;

import utility.Pair;
import voogasalad.util.reflection.Reflection;

/***
 * @author Carolyn Yao
 *  factory to create triggers and events using duvall's reflection utility.
 */

public final class EventFactory {

    private final String eventsDirectoryPath = "events.";

	public Pair<Trigger, Action> createEvent(String triggerName, String scriptPath,
                                             Map<String, Object> parameters, Object... args) {
		Trigger trigger = createTrigger(triggerName, args);
		Action action = new Action(scriptPath, parameters);
		return new Pair<>(trigger, action);
	}

    public Pair<Trigger, Action> createEvent(String triggerName, String scriptPath,
                                              Object... args) {
        return createEvent(triggerName, scriptPath, new HashMap<String, String>(), args);
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
