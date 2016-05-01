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
import view.editor.eventeditor.tables.KeyBindingTableManager;
import view.enums.GUISize;
import view.enums.ViewInsets;
import view.utilities.ButtonFactory;
import view.utilities.ComboFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

// TODO put Action setting and file picker on abstract
public class KeyBindingEditor extends EventEditorTab {
    private boolean keyListenerIsActive;
    private ScrollPane scrollPane;
    private ScrollPane chosenEntityBox;
    private Text chosenEntityText;
    private Text chosenEntityTitle;
    private ComboBox<String> chooseKeyEventTypeBox;

    private VBox pane;

    private KeyCode currentKey;
    private Button listenToKey;
    private EventType<KeyEvent> keyEventType;
    private Text keyInputText;
    private ResourceBundle myResources;
    private Action action;
    private Button createEventButton;
    private String language;

    private KeyBindingTableManager tableManager;
    private EventViewManager eventViewManager;

    private List<IEntity> chosenEntities;

    public KeyBindingEditor (String language, ObservableList<ILevel> levelList) {
        super(language, levelList);
        this.language = language;

        eventViewManager = new EventViewManager();

        chosenEntityTitle = new Text("== PICKED ENTITIES ==\n");    // TODO resource
        chosenEntityTitle.setFont(new Font(20));    // TODO enum...?

        chosenEntities = new ArrayList<IEntity>();
        scrollPane = new ScrollPane();
        myResources = ResourceBundle.getBundle(language);
        keyListenerIsActive = false;
        pane = new VBox(GUISize.EVENT_EDITOR_PADDING.getSize());
        pane.setPadding(ViewInsets.GAME_EDIT.getInset());
        pane.setAlignment(Pos.TOP_LEFT);
        tableManager = new KeyBindingTableManager(language, this);
        action = null;
        currentKey = null;

        pane.setOnKeyPressed(e -> keyWasPressed(e.getCode()));
        addParametersPane(pane);
        choseLevels(new ArrayList<ILevel>(levelList));
        eventViewManager.levelWasPicked(new ArrayList<ILevel>(levelList));
        populateLayout();
    }

    private void keyWasPressed (KeyCode code) {
        if (!keyListenerIsActive)
            return;

        currentKey = code;
        keyInputText.setText(myResources.getString("key") + code.getName());
        keyListenerIsActive = false;
    }

    private void printEvents () {
        for (ILevel level : getChosenLevels()) {
            System.out.println(level.getName());
            System.out.println(level.getEventSystem().getEventsAsString());
        }
    }

    private void createEvent () {
        addEventToLevels(getChosenLevels(), getChosenEntities(), "KeyTrigger", currentKey.getName());
        flashText(getEventCreatedText());
        eventViewManager.updateTable();
    }

    private void listenButtonPress () {
        keyListenerIsActive = true;
        keyInputText.setText("Listening....");    // TODO resource
    }

    @Override
    public ScrollPane getPane () {
        return scrollPane;
    }


    public void makeUpperSide () {
        HBox container = new HBox(GUISize.EVENT_EDITOR_PADDING.getSize());
        VBox innerContainer = new VBox(GUISize.EVENT_EDITOR_SUBPADDING.getSize());

        listenToKey = ButtonFactory.makeButton(myResources.getString("pressKey"), e -> listenButtonPress());

        keyInputText = new Text(myResources.getString("noKeyPressed"));

        // chooseFileButton = ButtonFactory.makeButton(myResources.getString("chooseGroovy"), e -> getFile());

        ObservableList<String> keyEventTypes = FXCollections.observableArrayList(myResources.getString("keyPress"), myResources.getString("keyRelease"));

        chooseKeyEventTypeBox = ComboFactory.makeComboBox(myResources.getString("chooseKeyEventType"), keyEventTypes, e -> setEventType(chooseKeyEventTypeBox.getValue()));

        createEventButton = ButtonFactory.makeButton(myResources.getString("makeEvent"), e -> createEvent());

        innerContainer.getChildren().addAll(listenToKey, keyInputText, getActionPane(), createEventButton);

        createEventButton.setOnAction(e -> createEvent());

        chosenEntityText = new Text();

        chosenEntityBox = new ScrollPane(new VBox(chosenEntityTitle, chosenEntityText));

        fillChosenEntityBox();
        container.getChildren().addAll(getLevelPickerPane(), tableManager.getContainer(), chosenEntityBox, innerContainer);

        pane.getChildren().add(container);
    }


    public void makeBottomSide () {
        HBox container = new HBox(GUISize.EVENT_EDITOR_HBOX_PADDING.getSize());

        container.getChildren().add(eventViewManager.getPane());
        pane.getChildren().add(container);
    }

    @Override
    public void populateLayout () {
        makeUpperSide();
        makeBottomSide();

        scrollPane.setContent(pane);
    }

    private void setEventType (String eventType) {
        if (eventType.equals(myResources.getString("keyPress"))) {
            keyEventType = KeyEvent.KEY_PRESSED;
        }
        if (eventType.equals(myResources.getString("keyRelease"))) {
            keyEventType = KeyEvent.KEY_RELEASED;
        }
    }


    private void fillChosenEntityBox () {
        if (chosenEntities.isEmpty()) {
            chosenEntityText.setText("No Entities Selected!");    // TODO resource
        } else {
            String entityString = "";
            for (IEntity entity : chosenEntities) {
                entityString += entity.getName() + "\n";
            }

            chosenEntityText.setText(entityString);
        }
    }

    public void choseEntity (List<IEntity> entities) {
        this.chosenEntities = entities;

        fillChosenEntityBox();
    }

    public List<IEntity> getChosenEntities () {
        return chosenEntities;
    }

    @Override
    public void updateEditor () {
    }

    @Override
    public void actionOnChosenLevels (List<ILevel> levels) {
        tableManager.levelWasPicked(levels);
        eventViewManager.levelWasPicked(levels);
    }

}
