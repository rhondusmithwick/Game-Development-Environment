package view.editor.gameeditor;

import java.util.Arrays;
import java.util.ResourceBundle;

import api.IEntity;
import api.ILevel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Vooga;
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
/**
 * 
 * @author calinelson
 *
 */
public class GameEditor extends Editor  {

    private VBox pane;
    private ResourceBundle myResources;
    @SuppressWarnings("unused")
    private Authoring authEnv;
    private String myLanguage;
    private ObservableList<IEntity> masterEntityList;
    private ObservableList<ILevel> masterEnvironmentList;
    private GameDetails gameDetails;
    private ObjectDisplay entDisp, envDisp, eventDisplay;
    private ScrollPane scrollPane;
    private final GameLoader gameLoader = new GameLoader();

    public GameEditor(Authoring authEnv, String language, String fileName, Scene myScene){
        this(authEnv, language, myScene);
        gameLoader.loadGame(fileName, gameDetails, masterEntityList, masterEnvironmentList);
    }

    public GameEditor(Authoring authEnv, String language, Scene myScene){
        myLanguage = language;
        gameDetails = new GameDetails(language);
        myResources = ResourceBundle.getBundle(language);
        this.authEnv=authEnv;
        this.masterEntityList = FXCollections.observableArrayList();
        this.masterEnvironmentList = FXCollections.observableArrayList();
        entDisp = new EntityDisplay(myLanguage, masterEntityList, authEnv);
        envDisp = new EnvironmentDisplay(myLanguage, masterEnvironmentList, masterEntityList, authEnv, myScene);
        eventDisplay = new EventDisplay(myLanguage, masterEntityList, masterEnvironmentList, authEnv);
        setPane();
    }


    private void setPane() {
        pane = new VBox(GUISize.GAME_EDITOR_PADDING.getSize());
        pane.setPadding(ViewInsets.GAME_EDIT.getInset());
        pane.setAlignment(Pos.TOP_LEFT);
        scrollPane = new ScrollPane(pane);
    }



    @Override
    public ScrollPane getPane() {
        populateLayout();
        return scrollPane;
    }

    @Override
    public void populateLayout() {
        VBox right = rightPane();
        VBox left = leftPane();
        left.prefWidthProperty().bind(scrollPane.widthProperty().divide(GUISize.HALF.getSize()));
        right.prefWidthProperty().bind(scrollPane.widthProperty().divide(GUISize.HALF.getSize()));
        HBox container = new HBox(GUISize.GAME_EDITOR_PADDING.getSize());
        container.getChildren().addAll(left, right);
        pane.getChildren().addAll(container);
    }

    private VBox rightPane() {
        VBox temp = new VBox(GUISize.GAME_EDITOR_PADDING.getSize());
        temp.getChildren().add( new Label(myResources.getString("entities")));
        temp.getChildren().add(entDisp.init());
        temp.getChildren().add( new Label(myResources.getString("environments")));
        temp.getChildren().add(envDisp.init());
        return temp;
    }

    private VBox leftPane() {
        VBox temp = new VBox(GUISize.GAME_EDITOR_PADDING.getSize());
        temp.getChildren().addAll(gameDetails.getElements());
        Button mainMenu = ButtonFactory.makeButton(myResources.getString("mainMenu"), e->toMainMenu());
        temp.getChildren().addAll(Arrays.asList(entDisp.makeNewObject(), envDisp.makeNewObject(), eventDisplay.makeNewObject(), mainMenu, ButtonFactory.makeButton(myResources.getString("saveGame"), e->saveGame())));

        return temp;
    }


    private void toMainMenu () {
        Stage myStage = (Stage) pane.getScene().getWindow();
        myStage.setWidth(GUISize.MAIN_SIZE.getSize());
        myStage.setHeight(GUISize.MAIN_SIZE.getSize());
        Vooga vooga = new Vooga(myStage);
        vooga.init();
    }

    private void saveGame() {
        new GameSaver().saveGame(masterEnvironmentList, masterEntityList, gameDetails.getGameDetails());
        Alerts.showAlert("", "", myResources.getString("saved"), AlertType.INFORMATION);
    }


    @Override
    public void updateEditor() {
        populateLayout();
    }
}
