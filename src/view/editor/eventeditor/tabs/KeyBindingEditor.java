// This entire file is part of my masterpiece.
// Alan Cavalcanti

package view.editor.eventeditor.tabs;

import api.IEntity;
import api.ILevel;
import events.Action;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import view.editor.eventeditor.tables.EventViewManager;
import view.editor.eventeditor.tables.TabWithViewerManager;
import view.enums.GUISize;
import view.enums.ViewInsets;
import view.utilities.ButtonFactory;
import view.utilities.ComboFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Event Editor pane that contains an Entity navigator, and a key listener.
 * Extends the EventEditorWithViewer for it contains the navigator, and a very simple
 * trigger input (Key)
 * @author Alankmc
 *
 */
public class KeyBindingEditor extends EventEditorWithViewer{
    
	private final ScrollPane scrollPane;
    private final VBox pane;
    private final ResourceBundle myResources;
    private boolean keyListenerIsActive;
    private ComboBox<String> chooseKeyEventTypeBox;
    private KeyCode currentKey;
    private Text keyInputText;
    private EventType keyEventType;
    private static final String TRIGGER_TYPE = "KeyTrigger";
    
    public KeyBindingEditor (String language, ObservableList<ILevel> levelList) {
        super(language, levelList, TRIGGER_TYPE);
        
        myResources = ResourceBundle.getBundle(language);
        scrollPane = new ScrollPane();
        keyListenerIsActive = false;
        pane = new VBox(GUISize.EVENT_EDITOR_PADDING.getSize());
        pane.setPadding(ViewInsets.GAME_EDIT.getInset());
        pane.setAlignment(Pos.TOP_LEFT);
        
        currentKey = null;

        pane.setOnKeyPressed(e -> keyWasPressed(e.getCode()));
        addParametersPane(pane);

        choseLevels(new ArrayList<>(levelList));
        getCreateEventButton().setOnAction(e -> createEvent(currentKey.getName(), keyEventType));
        populateLayout();
    }

    /**
     * When key is pressed while key listener is active, set the pressed key.
     * @param KeyCode code
     */
    private void keyWasPressed (KeyCode code) {
        if (!keyListenerIsActive)
            return;

        currentKey = code;
        keyInputText.setText(myResources.getString("key") + code.getName());
        keyListenerIsActive = false;
    }

    /**
     * Button handler, that activates the key listener.
     */
    private void listenButtonPress () {
        keyListenerIsActive = true;
        keyInputText.setText(myResources.getString("listening"));   
    }
    
    /**
     * Populates the upper side of the layout. Contains the input keys, and the 
     * Entity navigator.
     */
    public void makeUpperSide () {
        HBox container = new HBox(GUISize.EVENT_EDITOR_PADDING.getSize());
        VBox innerContainer = new VBox(GUISize.EVENT_EDITOR_SUBPADDING.getSize());

        Button listenToKey = ButtonFactory.makeButton(myResources.getString("pressKey"), e -> listenButtonPress());
        keyInputText = new Text(myResources.getString("noKeyPressed"));
        ObservableList<String> keyEventTypes = FXCollections.observableArrayList(myResources.getString("keyPress"), myResources.getString("keyRelease"));
        chooseKeyEventTypeBox = ComboFactory.makeComboBox(myResources.getString("chooseKeyEventType"), keyEventTypes, e -> setEventType(chooseKeyEventTypeBox.getValue()));

        innerContainer.getChildren().addAll(listenToKey, chooseKeyEventTypeBox, keyInputText, getActionPane(), getCreateEventButton());
        container.getChildren().addAll(getLevelPickerPane(), getTable(), getEntityPickerPane(), innerContainer);

        pane.getChildren().add(container);
    }

    /**
     * Make bottom side of the UI. Contains the Event Viewer.
     */
    public void makeBottomSide () {
        pane.getChildren().add(getEventViewer());
    }

    /**
     * ComboBox Handler. Will change the type of the keyEvent.
     * @param String eventType
     */
    private void setEventType (String eventType) {
        if (eventType.equals(myResources.getString("keyPress"))) {
            this.keyEventType = KeyEvent.KEY_PRESSED;
        }
        if (eventType.equals(myResources.getString("keyRelease"))) {
            this.keyEventType = KeyEvent.KEY_RELEASED;
        }
    }
    
    @Override
    public void populateLayout () {
        makeUpperSide();
        makeBottomSide();

        scrollPane.setContent(pane);
    }
    
    @Override
    public ScrollPane getPane () {
        return scrollPane;
    }
   
    @Override
    public void updateEditor () {
    }

}
