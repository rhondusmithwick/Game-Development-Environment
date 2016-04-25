package events;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import api.ILevel;
import utility.Pair;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import api.IEntitySystem;

/***
 * @author Anirudh Jonnavithula, Carolyn Yao
 */

public final class EventFactory {
	
	private EventFactory(){}
	
	public static Pair<Trigger, Action> createEvent(Map<String, String> triggerMapDescription, String scriptPath, ILevel level) {
		Trigger trigger=null;
		try {
			trigger = ((Trigger) Class.forName(triggerMapDescription.get("trigger_type")).getConstructor(Map.class).newInstance(triggerMapDescription));
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Action action = new Action(scriptPath);
		return new Pair<Trigger, Action>(trigger, action);
	}
	
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
