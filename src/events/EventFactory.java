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

	public Pair<Trigger, Action> createEvent(String triggerName, String scriptPath, Object... args) {
		Trigger trigger = createTrigger(triggerName, args);
//		trigger = (Trigger) Reflection.createInstance(className, triggerMapDescription);
		Action action = new Action(scriptPath);
		return new Pair<Trigger, Action>(trigger, action);
	}

    public Trigger createTrigger(String className, Object... args) {
        Trigger trigger = (Trigger) Reflection.createInstance(className, args);
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

//    public Pair<Trigger, Action> createEvent(String scriptPath, Object... args) {
//        Trigger trigger = null;
//        String className = triggerMapDescription.get("trigger_type");
//        trigger = (Trigger) Reflection.createInstance(className, triggerMapDescription);
//        Action action = new Action(scriptPath);
//        return new Pair<Trigger, Action>(trigger, action);
//    }

	public String getScriptFromPath(String scriptPath) {
    	String script = null;
		try {
			script = Files.toString(new File(scriptPath), Charsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return script;
    }
}
