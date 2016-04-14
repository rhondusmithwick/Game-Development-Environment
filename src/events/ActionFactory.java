package events;

import java.io.File;
import java.io.IOException;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import api.IEntitySystem;

public class ActionFactory {
	
	private ActionFactory(){}
	
	/*public static Action createAction(String actionDescription, String scriptPath, IEntitySystem universe) {
		String[] descriptors = actionDescription.split(":");
		
	}*/
	
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
