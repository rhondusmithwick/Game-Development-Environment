package view.editor.gameeditor;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import api.IEntity;
import api.ILevel;
import datamanagement.XMLReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import view.Authoring;
import view.editor.Editor;
import view.enums.DefaultStrings;
import view.enums.GUISize;
import view.enums.ViewInsets;
import view.utilities.Alerts;
import view.utilities.ButtonFactory;

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

	public GameEditor(Authoring authEnv, String language, String fileName){
		this(authEnv, language);
		loadFile(fileName);
	}
	
	public GameEditor(Authoring authEnv, String language){
		myLanguage = language;
		gameDetails = new GameDetails(language);
		myResources = ResourceBundle.getBundle(language);
		this.authEnv=authEnv;
		this.masterEntityList = FXCollections.observableArrayList();
		this.masterEnvironmentList = FXCollections.observableArrayList();
		entDisp = new EntityDisplay(myLanguage, masterEntityList, authEnv);
		envDisp = new EnvironmentDisplay(myLanguage, masterEnvironmentList, masterEntityList, authEnv);
		eventDisplay = new EventDisplay(myLanguage, masterEntityList, masterEnvironmentList, authEnv);
		setPane();
	}


	private void setPane() {
		pane = new VBox(GUISize.GAME_EDITOR_PADDING.getSize());
		pane.setPadding(ViewInsets.GAME_EDIT.getInset());
		pane.setAlignment(Pos.TOP_LEFT);
		scrollPane = new ScrollPane(pane);
	}
	

	private void loadFile(String fileName) {

		fileName = DefaultStrings.CREATE_LOC.getDefault() + fileName;
		gameDetails.setDetails(new XMLReader<List<String>>().readSingleFromFile(fileName + DefaultStrings.METADATA_LOC.getDefault()));
		masterEntityList.addAll(new XMLReader<List<IEntity>>().readSingleFromFile((fileName + DefaultStrings.ENTITIES_LOC.getDefault())));
		loadLevels(fileName);
	}


	private void loadLevels(String fileName) {
		fileName = fileName + DefaultStrings.LEVELS_LOC.getDefault();
		File file = new File(fileName);
		for(File f: file.listFiles()){
			masterEnvironmentList.add(new XMLReader<ILevel>().readSingleFromFile(f.getPath()));
		}
		
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
		temp.getChildren().addAll(Arrays.asList(entDisp.makeNewObject(), envDisp.makeNewObject(), eventDisplay.makeNewObject(), ButtonFactory.makeButton(myResources.getString("saveGame"), e->saveGame())));

		return temp;
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
