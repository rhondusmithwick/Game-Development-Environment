package testing;

import api.IEntity;
import api.ILevel;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.entity.Level;
import view.editor.Editor;
import view.editor.EditorFactory;
import view.editor.entityeditor.EditorEntity;

public class EditorTesting extends Application {
    private static final String LOAD_FILE_NAME = "resources/savedEntities/player.xml";
    private static final String LANGUAGE = "english";
    private final ILevel entitySystem = new Level();
    private Stage myStage;

    /**
     * Launches our program.
     *
     * @param args
     */
    public static void main (String[] args) {
        launch(args);
    }

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
        ObservableList<IEntity> entityList = FXCollections.observableArrayList();
        //Editor editorEntity = factory.createEditor(EditorEnvironment.class, new Level(), LANGUAGE, new Button());
        Editor editorEntity = new EditorEntity(null, entity, entityList);
        Scene scene = new Scene(editorEntity.getPane());

        myStage.setScene(scene);
        myStage.show();
    }

}