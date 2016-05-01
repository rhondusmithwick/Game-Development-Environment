package view.editor.eventeditor.tabs;

import api.IEntity;
import api.ILevel;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import view.editor.eventeditor.tables.EventViewManager;
import view.editor.eventeditor.tables.KeyBindingTableManager;
import view.enums.GUISize;
import view.enums.ViewInsets;
import view.utilities.ButtonFactory;
import view.utilities.TextFieldFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class TimeEventEditor extends EventEditorTab {
    private final ScrollPane scrollPane;
    private final Text chosenEntityTitle;
    private final VBox pane;
    private final ResourceBundle myResources;
    private final KeyBindingTableManager tableManager;
    private final EventViewManager eventViewManager;
    private final TextField textField;
    Text addedParametersText;
    private Text chosenEntityText;
    private HBox parametersPane;
    // TODO test
    private Button getEventsString;
    private List<IEntity> chosenEntities;

    public TimeEventEditor (String language, ObservableList<ILevel> levelList) {
        super(language, levelList);
        String language1 = language;

        eventViewManager = new EventViewManager();

        chosenEntityTitle = new Text("== PICKED ENTITIES ==\n");    // TODO resource
        chosenEntityTitle.setFont(new Font(20));    // TODO enum...?

        chosenEntities = new ArrayList<>();
        scrollPane = new ScrollPane();
        myResources = ResourceBundle.getBundle(language);

        pane = new VBox(GUISize.EVENT_EDITOR_PADDING.getSize());
        pane.setPadding(ViewInsets.GAME_EDIT.getInset());
        pane.setAlignment(Pos.TOP_LEFT);
        tableManager = new KeyBindingTableManager(language, this);

        addParametersPane(pane);
        choseLevels(new ArrayList<>(levelList));
        eventViewManager.levelWasPicked(new ArrayList<>(levelList));
        textField = TextFieldFactory.makeTextArea("INPUT TIME!");

        populateLayout();
    }

    // TODO test
    private void printEvents () {
        for (ILevel level : getChosenLevels()) {
            System.out.println(level.getName());
            System.out.println(level.getEventSystem().getEventsAsString());
        }
    }

    private void createEvent () {
        if (getChosenLevels().isEmpty())
            return;

        double time;

        try {
            time = Double.parseDouble(textField.getText());
        } catch (Exception e) {
            return;
        }
        addEventToLevels(getChosenLevels(), getChosenEntities(), "TimeTrigger", time);
        flashText(getEventCreatedText());
        eventViewManager.updateTable();
    }


    @Override
    public ScrollPane getPane () {
        return scrollPane;
    }


    public void makeUpperSide () {
        HBox container = new HBox(GUISize.EVENT_EDITOR_PADDING.getSize());
        VBox innerContainer = new VBox(GUISize.EVENT_EDITOR_SUBPADDING.getSize());


        // chooseFileButton = ButtonFactory.makeButton(myResources.getString("chooseGroovy"), e -> getFile());

        Button createEventButton = ButtonFactory.makeButton(myResources.getString("makeEvent"), e -> createEvent());

        innerContainer.getChildren().addAll(textField, getActionPane(), createEventButton);

        chosenEntityText = new Text();

        ScrollPane chosenEntityBox = new ScrollPane(new VBox(chosenEntityTitle, chosenEntityText));

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
