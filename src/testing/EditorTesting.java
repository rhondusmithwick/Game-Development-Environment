package testing;

import api.IEntity;
import api.ILevel;
import api.ISerializable;
import model.entity.Level;
import view.editor.Editor;
import view.editor.EditorFactory;
import view.editor.entityeditor.EditorEntity;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EditorTesting extends Application {
	private Stage myStage;
    private static final String LOAD_FILE_NAME = "resources/savedEntities/player.xml";
    private final ILevel entitySystem = new Level();
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

	@Override
	public void start (Stage stage) {

		myStage = stage;
        IEntity entity = entitySystem.createEntityFromLoad(LOAD_FILE_NAME);
        EditorFactory factory = new EditorFactory();
        ObservableList<ISerializable> entityList = FXCollections.observableArrayList();
        //Editor editorEntity = factory.createEditor(EditorEnvironment.class, new Level(), LANGUAGE, new Button());
        Editor editorEntity = new EditorEntity(null, entity, entityList);
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