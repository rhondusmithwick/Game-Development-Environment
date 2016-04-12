package testing;

import java.io.File;
import java.io.IOException;

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

	private final String MOVE_SCRIPT = "resources/groovyScripts/SignalScript.groovy";

	private Scene mYInit() {
		BorderPane splash = new BorderPane();
		Scene scene = new Scene(splash, 500, 500);
		scene.setOnKeyPressed(e -> inputSystem.take(e.getCode()));
		return scene;
	}

	private void setUp() {
		IEntity entity = new Entity("Ben");
		Position position = new Position();
		entity.forceAddComponent(position, true);
		universe.addEntity(entity);
		inputSystem.addEvent(KeyCode.E, getAction());
	}

	private Action getAction() {
		String script = null;
		try {
			script = Files.toString(new File(MOVE_SCRIPT), Charsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new UniverseAction(script, universe);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setScene(mYInit());
		primaryStage.show();
		setUp();
	}
	
	public static void main(String[] args) { 
		launch(args);
	}

}