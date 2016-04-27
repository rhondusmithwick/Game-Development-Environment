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
	
	public static Pair<Trigger, Action> createEvent(Map<String, String> triggerMapDescription, String scriptPath) {
		Trigger trigger = null;
        String className = triggerMapDescription.get("trigger_type");
		try {
			trigger = (Trigger) Class.forName(className).getConstructor(Map.class).newInstance(triggerMapDescription);
        }
		catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
            System.out.println("illegal access found");
		} catch (IllegalArgumentException e) {
            System.out.println("illegal argument found");
		} catch (InvocationTargetException e) {
            System.out.println("invocation target not found");
		} catch (NoSuchMethodException e) {
            System.out.println("no such method found");
		} catch (SecurityException e) {
            System.out.println("security exception");
		}
        catch (ClassNotFoundException e) {
            System.out.println(className);
            System.out.println("class not found");
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
