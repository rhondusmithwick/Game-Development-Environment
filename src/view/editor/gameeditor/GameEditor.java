package view.editor.gameeditor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import api.IDataReader;
import api.IDataWriter;
import api.ISerializable;
import datamanagement.XMLReader;
import datamanagement.XMLWriter;
import enums.DefaultStrings;
import enums.GUISize;
import enums.Indexes;
import enums.ViewInsets;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import view.Authoring;
import view.Utilities;
import view.editor.Editor;

public class GameEditor extends Editor  {

	private VBox pane;
	private ResourceBundle myResources;
	@SuppressWarnings("unused")
	private Authoring authEnv;
	private String myLanguage;
	private ObservableList<ISerializable> masterEntityList;
	private ObservableList<ISerializable> masterEnvironmentList;

	private GameDetails gameDetails;
	private ObjectDisplay entDisp, envDisp, eventDisplay;


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
		eventDisplay = new EventDisplay(myLanguage, masterEntityList, authEnv);

		setPane();
	}


	private void setPane() {
		pane = new VBox(GUISize.GAME_EDITOR_PADDING.getSize());
		pane.setPadding(ViewInsets.GAME_EDIT.getInset());
		pane.setAlignment(Pos.TOP_LEFT);
	}
	

	private void loadFile(String fileName) {
		IDataReader<SaveGame> xReader  = new XMLReader<>();
		SaveGame s = xReader.readSingleFromFile(DefaultStrings.CREATE_LOC.getDefault() + fileName+ DefaultStrings.XML.getDefault());
		gameDetails.setDetails(Arrays.asList(s.getName(), s.getDesc(), s.getIcon()));
		masterEntityList.addAll(s.getEntites());
		masterEnvironmentList.addAll(s.getEnvironments());

	}


	@Override
	public Pane getPane() {
		populateLayout();
		return pane;
	}

	@Override
	public void populateLayout() {
		VBox right = rightPane();
		VBox left = leftPane();
		left.prefWidthProperty().bind(pane.widthProperty().divide(2));
		right.prefWidthProperty().bind(pane.widthProperty().divide(2));
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
		temp.getChildren().addAll(Arrays.asList(entDisp.makeNewObject(), envDisp.makeNewObject(), eventDisplay.makeNewObject(), Utilities.makeButton(myResources.getString("saveGame"), e->saveGame())));

		return temp;
	}
	
	private void saveGame() {
		SaveGame sGame = new SaveGame(gameDetails.getGameDetails(), new ArrayList<ISerializable>(masterEntityList), new ArrayList<ISerializable>(masterEnvironmentList));
		IDataWriter<SaveGame> writer = new XMLWriter<>();
		String name = gameDetails.getGameDetails().get(Indexes.GAME_NAME.getIndex());
		writer.writeToFile(DefaultStrings.CREATE_LOC.getDefault() + name.trim()+ DefaultStrings.XML.getDefault(),sGame);
		System.out.println("Saved");
	}


	@Override
	public void updateEditor() {
		populateLayout();
	}

	@Override
	public void addSerializable(ISerializable serialize) {}
	@Override
	public void loadDefaults() {}



}
