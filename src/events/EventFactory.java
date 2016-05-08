// This entire class is part of my masterpiece. Carolyn Yao cy88
// This is a very short class that uses the vooga salad reflection utility. It hides the
// typecasting of the different types of triggers effectively. I also edited this class a bit by adding the
// events directory path into a property file and referencing that instead. This means that
// when we change the directory of things this code will not break.

package events;

import utility.Pair;
import voogasalad.util.reflection.Reflection;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/***
 * @author Carolyn Yao
 *         factory to create triggers and events using duvall's reflection utility.
 */

public final class EventFactory {

    private final ResourceBundle directoryPaths = ResourceBundle.getBundle("/propertyFiles/ClassDirectoryPaths.properties");
    private final String eventDirectoryPath = directoryPaths.getString("EVENT");

    private EventFactory () {
    }

    public static Pair<Trigger, Action> createEvent (String triggerName, String scriptPath,
                                                     Map<String, Object> parameters, Object... args) {
        Trigger trigger = createTrigger(triggerName, args);
        Action action = new Action(scriptPath, parameters);
        return new Pair<>(trigger, action);
    }

    public static Trigger createTrigger (String className, Object... args) {
        String eventsDirectoryPath = "events.";
        return (Trigger) Reflection.createInstance(eventsDirectoryPath + className, args);
    }
}
