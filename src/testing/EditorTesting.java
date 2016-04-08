package testing;

import model.component.movement.Speed;
import model.entity.Entity;
import model.entity.EntitySystem;
import api.IEntity;
import api.IEntitySystem;
import view.EditorEntity;
import view.EditorFactory;
import view.Editor;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EditorTesting extends Application {
	private Stage myStage;
    private static final String LOAD_FILE_NAME = "resources/savedEntities/player.xml";
    private final IEntitySystem entitySystem = new EntitySystem();
    private static final String ENTITY_EDITOR = "EditorEntity";

	/**
	 * Sets up a stage to launch our window and initializes the splash screen.
	 * @param stage
	 */

	public void start (Stage stage) {

		myStage = stage;
        IEntity entity = entitySystem.createEntityFromLoad(LOAD_FILE_NAME);
        EditorFactory factory = new EditorFactory();
		//Editor editorEntity = (Editor) new EditorEntity((Entity) entity);
        Editor editorEntity = factory.createEditor(ENTITY_EDITOR);
        Scene scene = new Scene(editorEntity.getPane());
		myStage.setScene(scene);
		myStage.show();
	}

	/**
	 * Launches our program.
	 * @param args
	 */
	public static void main (String[] args) {
		launch(args);
	}

}

