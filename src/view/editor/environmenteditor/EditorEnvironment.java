package view.editor.environmenteditor;

import api.IEntity;
import api.ILevel;
import api.IView;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.component.movement.Position;
import model.component.visual.Sprite;
import view.View;
import view.ViewFeatureMethods;
import view.editor.Editor;
import view.enums.DefaultEntities;
import view.enums.DefaultStrings;
import view.enums.GUISize;
import view.utilities.Alerts;
import view.utilities.ButtonFactory;
import view.utilities.FileUtilities;
import view.utilities.TitledPaneFactory;
import view.utilities.UserInputBoxFactory;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static view.utilities.SpriteUtilities.isSprite;

/**
 * @author Bruna
 */
public class EditorEnvironment extends Editor {

    private final BorderPane environmentPane = new BorderPane();
    private final ResourceBundle myResources;
    private final ObservableList<IEntity> masterEntityList;
    private final ObservableList<ILevel> allEnvironmentsList;
    private final VBox leftPane = new VBox();
    private final VBox rightPane = new VBox();
    private final VBox masterEntityButtonsBox = new VBox();
    private final VBox environmentEntityButtonsBox = new VBox();
    private final TextField nameField = new TextField();
    private final ScrollPane scrollPane = new ScrollPane(environmentPane);
    private final IView view;
    private final EnvironmentButtonUtilites myButtonsClass;

    public EditorEnvironment (String language, ILevel toEdit, ObservableList<IEntity> masterList,
                              ObservableList<ILevel> addToList, Scene myScene) {
        String myLanguage = language;
        myResources = ResourceBundle.getBundle(language);
        masterList.addListener((ListChangeListener<IEntity>) c -> this.updateEditor());
        masterEntityList = masterList;
        ILevel myLevel = toEdit;
        allEnvironmentsList = addToList;

        view = new View((GUISize.TWO_THIRDS_OF_SCREEN.getSize()), GUISize.HEIGHT_MINUS_TAB.getSize(), GUISize.SCENE_SIZE.getSize(), GUISize.SCENE_SIZE.getSize(), myLevel,
                myLanguage, true);
        view.setScene(myScene);
        myButtonsClass = new EnvironmentButtonUtilites(view, environmentEntityButtonsBox, masterList, this, language);
        addLayoutComponents();
        System.setProperty("glass.accessible.force", "false");
        double DELAY = 3000;
        ViewFeatureMethods.startTimeline(DELAY, e -> updateEditor());

    }

    private void addLayoutComponents () {
        setLeftPane();
        setRightPane();
    }

    private void setLeftPane () {
        leftPane.getChildren().add(setNameDisplay());
        leftPane.getChildren().add(setEntityOptionsDisplay());
    }

    private TextField setNameDisplay () {
        if (view.getEntitySystem().getName().equals("")) {
            nameField.setText(myResources.getString("environmentName"));
        } else {
            nameField.setText(view.getEntitySystem().getName());
        }
        return nameField;
    }

    private ScrollPane setEntityOptionsDisplay () {
        if (masterEntityList.isEmpty()) {
            loadDefaults();
        }
        myButtonsClass.populateVbox(masterEntityButtonsBox, masterEntityList, "createAddEntityButton");
        return (new ScrollPane(TitledPaneFactory.makeTitledPane(myResources.getString("masterTemplates"), masterEntityButtonsBox, true)));
    }

    void addToSystem (IEntity entity) {
        String newName = UserInputBoxFactory.userInputBox(myResources.getString("noName"),
                myResources.getString("addEntityName"));
        if (newName != null) {
            entity.setName(newName);
        }
        view.getEntitySystem().addEntity(entity);
        try {
            if (!entity.hasComponent(Position.class) || !isSprite(entity)) { // TODO: animation?
                addComponents(entity);
            }
            environmentEntityButtonsBox.getChildren().add(myButtonsClass.createEntityButton(entity));
        } catch (Exception e) {
            Alerts.showAlert(myResources.getString("error"), null, myResources.getString("unableToAddEntity"),
                    AlertType.ERROR);
        }
    }

    private void loadDefaults () {
        if (Alerts.showAlert(myResources.getString("addDefaults"), myResources.getString("addDefaultsQuestion"),
                myResources.getString("defaultsMessage"), AlertType.CONFIRMATION)) {
            masterEntityList.add(DefaultEntities.BACKGROUND.getDefault());
            masterEntityList.add(DefaultEntities.CHAR_1.getDefault());
            masterEntityList.add(DefaultEntities.CHAR_2.getDefault());
        }
    }

    private void setRightPane () {
        rightPane.getChildren()
                .add(ButtonFactory.makeButton(myResources.getString("saveEnvironment"), e -> saveEnvironment()));
        rightPane.getChildren().add(new ScrollPane(TitledPaneFactory.makeTitledPane(myResources.getString("environmentInstances"), environmentEntityButtonsBox, true)));
    }

    @Override
    public void updateEditor () {

        myButtonsClass.populateVbox(masterEntityButtonsBox, masterEntityList, "createAddEntityButton");
        myButtonsClass.populateVbox(environmentEntityButtonsBox, view.getLevel().getAllEntities(), "createEntityButton");
    }

    @Override
    public void populateLayout () {
        environmentPane.setRight(rightPane);
        environmentPane.setLeft(leftPane);
        environmentPane.setCenter(view.getPane());
    }

    private void saveEnvironment () {
        String name = getName();
        view.getEntitySystem().setName(name);
        allEnvironmentsList.remove(view.getLevel());
        allEnvironmentsList.add(view.getLevel());
        environmentPane.getChildren().clear();
        environmentPane.setCenter(saveMessage(myResources.getString("saveMessage")));
    }

    private String getName () {
        String returnName = null;
        if (nameField.getText().equals(myResources.getString("environmentName"))) {
            returnName = UserInputBoxFactory.userInputBox(myResources.getString("noName"),
                    myResources.getString("noNameMessage"));
        } else {
            returnName = nameField.getText();
        }
        return returnName;
    }

    private void addComponents (IEntity entity) {
        if (Alerts.showAlert(myResources.getString("confirm"), myResources.getString("componentsRequired"),
                myResources.getString("addComponentQuestion"), AlertType.CONFIRMATION)) {
            addPositionComponent(entity);
            addSpriteComponent(entity);
        }
    }

    private void addPositionComponent (IEntity entity) {
        entity.setSpec(Position.class, 1);
        Position pos = new Position();
        entity.addComponent(pos);
    }

    private void addSpriteComponent (IEntity entity) {
        File file = FileUtilities.promptAndGetFile(FileUtilities.getImageFilters(),
                myResources.getString("pickImagePathImage"), DefaultStrings.GUI_IMAGES.getDefault());
        entity.setSpec(Sprite.class, 1);
        entity.addComponent(new Sprite(file.getPath()));
    }

    private void toggleHighlight (IEntity entity) {
        view.toggleHighlight(entity);
    }

    public void saveToMasterList (IEntity entity) {
        masterEntityList.add(entity);
    }

    public ILevel getLevel () {
        return view.getLevel();
    }

    public Map<String, EventHandler<ActionEvent>> makeMenuMap (IEntity entity, Button entityButton, MouseEvent event) {
        Map<String, EventHandler<ActionEvent>> menuMap = new LinkedHashMap<>();
        menuMap.put(myResources.getString("remove"), e -> ViewFeatureMethods.removeFromDisplay(entity, view.getEntitySystem()));
        menuMap.put(myResources.getString("sendBack"), e -> ViewFeatureMethods.sendToBack(entity, view.getEntitySystem()));
        menuMap.put(myResources.getString("sendFront"), e -> ViewFeatureMethods.sendToFront(entity, view.getEntitySystem()));
        menuMap.put(myResources.getString("sendBackOne"), e -> ViewFeatureMethods.sendBackward(entity, view.getEntitySystem()));
        menuMap.put(myResources.getString("sendForwardOne"), e -> ViewFeatureMethods.sendForward(entity, view.getEntitySystem()));
        menuMap.put(myResources.getString("saveAsMasterTemplate"), e -> saveToMasterList(entity));
        menuMap.put(myResources.getString("toggleHighlight"), e -> toggleHighlight(entity));
        return menuMap;
    }

    @Override
    public ScrollPane getPane () {
        return scrollPane;
    }

    public boolean displayContains (IEntity checkEntity) {
        return masterEntityList.contains(checkEntity);
    }

    public boolean environmentContains (IEntity checkEntity) {
        return view.getEntitySystem().containsEntity(checkEntity);
    }

    public void highlight (IEntity entity) {
        view.highlight(entity);
    }

    public void dehighlight (IEntity entity) {
        view.dehighlight(entity);
    }

}
