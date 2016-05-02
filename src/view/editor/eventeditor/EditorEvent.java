package view.editor.eventeditor;

import api.IEntity;
import api.ILevel;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import view.editor.Editor;
import view.editor.eventeditor.tabs.KeyBindingEditor;
import view.editor.eventeditor.tabs.PropertyEventEditor;
import view.editor.eventeditor.tabs.TimeEventEditor;
import view.enums.GUISize;
import view.enums.ViewInsets;

import java.util.ResourceBundle;

/**
 * Main Event Editor class, extends the abstract Editor. Contains all different tabs.
 * Each tab is - quite literally - a TabPane, and each one will display a different type of
 * Event Editor.
 *
 * @author Alankmc
 */

/*
 * TODO: Clean this shit up
 */
public class EditorEvent extends Editor {
    private final ScrollPane scrollPane;
    private final TabPane tabPane;
    private ResourceBundle myResources;

    /**
     * Constructor, follows the super's constructor.
     *
     * @param language
     * @param masterList
     * @param levelList
     */
    public EditorEvent (String language, ObservableList<IEntity> masterList, ObservableList<ILevel> levelList) {
        PropertyEventEditor propertyEventEditor = new PropertyEventEditor(language, levelList);
        KeyBindingEditor keyBindingEditor = new KeyBindingEditor(language, levelList);
        TimeEventEditor timeEventEditor = new TimeEventEditor(language, levelList);

        Pane pane = new VBox(GUISize.EVENT_EDITOR_PADDING.getSize());
        pane.setPadding(ViewInsets.EVENT_EDIT.getInset());
        pane.setPrefWidth(GUISize.EVENT_EDITOR_WIDTH.getSize());
        myResources = ResourceBundle.getBundle(language);
        scrollPane = new ScrollPane(pane);
        tabPane = new TabPane();
        Tab propertyTab = new Tab();
        pane.getChildren().add(tabPane);
        myResources = ResourceBundle.getBundle(language);
        // TODO: Put editors in map and use cool for loop for this
        populateEditorTab(propertyEventEditor);
        populateEditorTab(keyBindingEditor);
        populateEditorTab(timeEventEditor);
    }

    /**
     * Makes a tab with a certain Editor in it.
     *
     * @param editor
     */
    private void populateEditorTab (Editor editor) {
        Tab newTab = new Tab();
        newTab.setContent(editor.getPane());
        newTab.setClosable(false);
        tabPane.getTabs().add(newTab);
        newTab.setText(myResources.getString(editor.getClass().toString().split(" ")[1]));
    }

    public void populateLayout () {
    }

    @Override
    public ScrollPane getPane () {
        return scrollPane;
    }

    @Override
    public void updateEditor () {
    }

}