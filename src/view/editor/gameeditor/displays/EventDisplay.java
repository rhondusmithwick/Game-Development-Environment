package view.editor.gameeditor.displays;

import api.IEntity;
import api.ILevel;
import api.ISerializable;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import view.Authoring;
import view.editor.eventeditor.EditorEvent;
import view.enums.DefaultStrings;
import view.utilities.ButtonFactory;

import java.util.ResourceBundle;

public class EventDisplay extends ObjectDisplay {

    private final Authoring authoringEnvironment;
    private String language;
    private final ResourceBundle myResources;
    private final ObservableList<IEntity> masterEntityList;
    private ObservableList<ISerializable> masterEnvironmentList;
    private final ObservableList<ILevel> levelList;

    public EventDisplay (String language,
                         ObservableList<IEntity> masterEntityList, ObservableList<ILevel> levelList,
                         Authoring authoringEnvironment) {
        super(authoringEnvironment);
        this.levelList = levelList;
        this.language = language;
        this.masterEntityList = masterEntityList;
        this.authoringEnvironment = authoringEnvironment;
        this.language = language;
        this.myResources = ResourceBundle.getBundle(language);
    }

    @Override
    protected void addNewObjects (VBox container) {
    }

    @Override
    public Node makeNewObject () {
        return ButtonFactory.makeButton(myResources.getString(DefaultStrings.EVENT_EDITOR_NAME.getDefault()),
                e -> createEventEditor());//createEditor(EditorEvent.class, new Entity(), masterEnvironmentList));
    }

    private void createEventEditor () {
        EditorEvent editor = new EditorEvent(language, masterEntityList, levelList);
        editor.populateLayout();
        authoringEnvironment.createTab(editor.getPane(), editor.getClass().getSimpleName(), true);
    }
}
