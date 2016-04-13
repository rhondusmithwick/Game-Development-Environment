package events;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import api.IEntitySystem;
import javafx.scene.input.KeyCode;

/***
 * Created by ajonnav 04/12/16
 * @author Anirudh Jonnavithula
 *
 */
public class EventSystem {
	
	private InputSystem inputSystem;
	Map<String, String> actionMap = new HashMap<>();
	
	public void registerEventsFromFile(String filepath, IEntitySystem universe) {
		actionMap = loadFile(filepath);
	}
	
	public void takeKeyCode(KeyCode keycode) {
		inputSystem.take(keycode);
	}
	
	public Map<String, String> loadFile(String filepath) {
		Map<String, String> map= new HashMap<>();
		Properties properties = new Properties();
		try {
			InputStream s = getClass().getResourceAsStream(filepath);
			properties.load(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		properties.keySet().stream().forEach(e-> {
			String action = (String) e;
			String scriptPath = (String)properties.get(action);
			inputSystem.addEvent(action, getAction(scriptPath, entity));
		});
		return map;
	}
}
