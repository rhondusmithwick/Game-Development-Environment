package view.editor.gameeditor;

import api.IEntity;
import api.ILevel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import view.Authoring;
import view.editor.Editor;
import view.editor.gameeditor.displays.EntityDisplay;
import view.editor.gameeditor.displays.EnvironmentDisplay;
import view.editor.gameeditor.displays.EventDisplay;
import view.editor.gameeditor.displays.ObjectDisplay;
import view.enums.GUISize;
import view.enums.ViewInsets;
import view.utilities.Alerts;
import view.utilities.ButtonFactory;
import view.utilities.ToMainMenu;

import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * @author calinelson
 */
public class GameEditor extends Editor {

    private VBox pane;
    private final ResourceBundle myResources;
    private final ObservableList<IEntity> masterEntityList;
    private final ObservableList<ILevel> masterEnvironmentList;
    private final GameDetails gameDetails;
    private final ObjectDisplay entDisp;
    private final ObjectDisplay envDisp;
    private final ObjectDisplay eventDisplay;
    private ScrollPane scrollPane;

    public GameEditor (Authoring authEnv, String language, String fileName, Scene myScene) {
        this(authEnv, language, myScene);
        GameLoader gameLoader = new GameLoader();
        gameLoader.loadGame(fileName, gameDetails, masterEntityList, masterEnvironmentList);
    }

    public GameEditor (Authoring authEnv, String language, Scene myScene) {
        String myLanguage = language;
        myResources = ResourceBundle.getBundle(language);
        Authoring authEnv1 = authEnv;
        this.masterEntityList = FXCollections.observableArrayList();
        this.masterEnvironmentList = FXCollections.observableArrayList();
        entDisp = new EntityDisplay(myLanguage, masterEntityList, authEnv);
        envDisp = new EnvironmentDisplay(myLanguage, masterEnvironmentList, masterEntityList, authEnv, myScene);
        eventDisplay = new EventDisplay(myLanguage, masterEntityList, masterEnvironmentList, authEnv);
        gameDetails = new GameDetails(language);
        setPane();
    }


    private void setPane () {
        pane = new VBox(GUISize.GAME_EDITOR_PADDING.getSize());
        pane.setPadding(ViewInsets.GAME_EDIT.getInset());
        pane.setAlignment(Pos.TOP_LEFT);
        scrollPane = new ScrollPane(pane);
    }


    @Override
    public ScrollPane getPane () {
        populateLayout();
        return scrollPane;
    }

    @Override
    public void populateLayout () {
        VBox right = rightPane();
        VBox left = leftPane();
        left.prefWidthProperty().bind(scrollPane.widthProperty().divide(GUISize.HALF.getSize()));
        right.prefWidthProperty().bind(scrollPane.widthProperty().divide(GUISize.HALF.getSize()));
        HBox container = new HBox(GUISize.GAME_EDITOR_PADDING.getSize());
        container.getChildren().addAll(left, right);
        pane.getChildren().addAll(container);
    }

    private VBox rightPane () {
        VBox temp = new VBox(GUISize.GAME_EDITOR_PADDING.getSize());
        temp.getChildren().add(new Label(myResources.getString("entities")));
        temp.getChildren().add(entDisp.init());
        temp.getChildren().add(new Label(myResources.getString("environments")));
        temp.getChildren().add(envDisp.init());
        return temp;
    }

    private VBox leftPane () {
        VBox temp = new VBox(GUISize.GAME_EDITOR_PADDING.getSize());
        temp.getChildren().addAll(gameDetails.getElements());
        Button mainMenu = ButtonFactory.makeButton(myResources.getString("mainMenu"), e -> ToMainMenu.toMainMenu(pane));
        temp.getChildren().addAll(Arrays.asList(entDisp.makeNewObject(), envDisp.makeNewObject(), eventDisplay.makeNewObject(), mainMenu, ButtonFactory.makeButton(myResources.getString("saveGame"), e -> saveGame())));

        return temp;
    }


    private void saveGame () {
        new GameSaver().saveGame(masterEnvironmentList, masterEntityList, gameDetails.getGameDetails());
        Alerts.showAlert("", "", myResources.getString("saved"), AlertType.INFORMATION);
    }


    @Override
    public void updateEditor () {
        populateLayout();
    }
}
