package testing;

import model.entity.EntitySystem;

import view.editor.Editor;
import view.editor.EditorEnvironment;
import view.editor.EditorFactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class EditorTesting extends Application {
	private Stage myStage;
//    private static final String LOAD_FILE_NAME = "resources/savedEntities/player.xml";
//    private final IEntitySystem entitySystem = new EntitySystem();
    private static final String LANGUAGE = "english";

	/**
	 * Sets up a stage to launch our window and initializes the splash screen.
	 * 
	 * @param stage
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */

	public void start (Stage stage) {

		myStage = stage;
        //IEntity entity = entitySystem.createEntityFromLoad(LOAD_FILE_NAME);
        EditorFactory factory = new EditorFactory();
        Editor editorEntity = factory.createEditor(EditorEnvironment.class, new EntitySystem(), LANGUAGE, new Button());
        Scene scene = new Scene(editorEntity.getPane());

		myStage.setScene(scene);
		myStage.show();
	}

	/**
	 * Launches our program.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

}