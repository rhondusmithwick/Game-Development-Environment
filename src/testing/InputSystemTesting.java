package testing;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import api.IEntity;
import events.Action;
import events.EntityAction;
import events.InputSystem;
import events.UniverseAction;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.component.movement.Position;
import model.entity.Entity;
import model.entity.EntitySystem;

/**
 * Created by rhondusmithwick on 4/10/16.
 *
 * @author Rhondu Smithwick
 */
public class InputSystemTesting extends Application {

	private final EntitySystem universe = new EntitySystem();
	private final InputSystem inputSystem = new InputSystem();

	private final String KEY_BINDINGS = "/propertyFiles/keyBindings.properties";
	private final String ACTION_BINDINGS = "/propertyFiles/ActionScriptBindings.properties";
	

	private Scene mYInit() {
		BorderPane splash = new BorderPane();
		Scene scene = new Scene(splash, 500, 500);
		scene.setOnKeyPressed(e -> inputSystem.take(e.getCode()));
		return scene;
	}

	private IEntity setUpCharacter() {
		IEntity entity = new Entity("Ben");
		Position position = new Position();
		entity.forceAddComponent(position, true);
		universe.addEntity(entity);
		return entity;
	}

	private Action getAction(String scriptPath, IEntity entity) {
		String script = null;
		try {
			script = Files.toString(new File(scriptPath), Charsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new EntityAction(script, entity);
	}
	
	private void addActionBindings(IEntity entity) {
		//TODO change this so that we get the action bindings file from a resource file or something 
		String actionBindingsPath = ACTION_BINDINGS;
		
		Properties properties = new Properties();
		try {
			InputStream s = getClass().getResourceAsStream(actionBindingsPath);
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
	}
	
	private void addKeyBindings(String keyBindingsPath) {
		Properties properties = new Properties();
		try {
			InputStream s = getClass().getResourceAsStream(keyBindingsPath);
			properties.load(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		properties.keySet().stream().forEach(e-> {
			String key = (String) e;
			String actionName = (String)properties.get(key);
			inputSystem.addKeyBinding(KeyCode.getKeyCode(key), actionName);
		});
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setScene(mYInit());
		primaryStage.show();
		IEntity character = setUpCharacter();
		addActionBindings(character);
		addKeyBindings(KEY_BINDINGS);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static void main(String[] args) { 
		launch(args);
	}

}