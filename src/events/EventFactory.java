package events;

import utility.Pair;
import voogasalad.util.reflection.Reflection;

import java.util.HashMap;
import java.util.Map;

/***
 * @author Carolyn Yao
 *         factory to create triggers and events using duvall's reflection utility.
 */

public final class EventFactory {

    public Pair<Trigger, Action> createEvent (String triggerName, String scriptPath,
                                              Map<String, Object> parameters, Object... args) {
        Trigger trigger = createTrigger(triggerName, args);
        Action action = new Action(scriptPath, parameters);
        return new Pair<>(trigger, action);
    }

    public Pair<Trigger, Action> createEvent (String triggerName, String scriptPath,
                                              Object... args) {
        return createEvent(triggerName, scriptPath, new HashMap<String, String>(), args);
    }

    public Trigger createTrigger (String className, Object... args) {
        String eventsDirectoryPath = "events.";
        return (Trigger) Reflection.createInstance(eventsDirectoryPath + className, args);
    }
}
