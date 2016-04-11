package testing;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import api.IEntity;
import events.Action;
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

	private Scene mYInit() {
		BorderPane splash = new BorderPane();
		Scene scene = new Scene(splash, 500, 500);
		scene.setOnKeyPressed(e -> inputSystem.take(e.getCode()));
		return scene;
	}

	private void setUpCharacter() {
		IEntity entity = new Entity("Ben");
		Position position = new Position();
		entity.forceAddComponent(position, true);
		universe.addEntity(entity);
	}

	private Action getAction(String key, String scriptPath) {
		String script = null;
		try {
			script = Files.toString(new File(scriptPath), Charsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new UniverseAction(script, universe);
	}
	
	private void addKeyBindings() {
		Properties properties = new Properties();
		try {
			InputStream s = getClass().getResourceAsStream(KEY_BINDINGS);
			properties.load(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("HEHE");
		}
		properties.keySet().stream().forEach(e-> {
			String key = (String) e;
			String scriptPath = (String)properties.get(key);
			inputSystem.addEvent(KeyCode.getKeyCode(key), getAction(key, scriptPath));
		});
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setScene(mYInit());
		primaryStage.show();
		setUpCharacter();
		addKeyBindings();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}