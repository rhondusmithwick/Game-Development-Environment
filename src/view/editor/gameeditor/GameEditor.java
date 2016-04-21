package view.editor.gameeditor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import api.IDataReader;
import api.IEntity;
import api.IEntitySystem;
import api.ISerializable;
import api.ISystemManager;
import datamanagement.XMLReader;
import enums.DefaultStrings;
import enums.GUISize;
import enums.Indexes;
import enums.ViewInsets;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.core.SystemManager;
import model.entity.EntitySystem;
import view.Authoring;
import view.Utilities;
import view.editor.Editor;

public class GameEditor extends Editor  {

	private VBox pane;
	private ResourceBundle myResources;
	@SuppressWarnings("unused")
	private Authoring authEnv;
	private String myLanguage;
	private ObservableList<IEntity> masterEntityList;
	private ObservableList<IEntitySystem> masterEnvironmentList;
	private ISystemManager system;
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
		this.system=new SystemManager();
		this.masterEntityList = FXCollections.observableArrayList();
		this.masterEnvironmentList = FXCollections.observableArrayList();
		entDisp = new EntityDisplay(myLanguage, masterEntityList, authEnv);
		envDisp = new EnvironmentDisplay(myLanguage, masterEnvironmentList, masterEntityList, authEnv);
		eventDisplay = new EventDisplay(myLanguage, masterEntityList, authEnv);


		// TEST
	//	Entity test = new Entity("Hello");
	//	test.addComponent(new Position());
	//	masterEntityList.add(test);
		
		//


		setPane();
	}


	private void setPane() {
		pane = new VBox(GUISize.GAME_EDITOR_PADDING.getSize());
		pane.setPadding(ViewInsets.GAME_EDIT.getInset());
		pane.setAlignment(Pos.TOP_LEFT);
		scrollPane = new ScrollPane(pane);
	}
	

	private void loadFile(String fileName) {
		IDataReader<ISystemManager> xReader  = new XMLReader<>();
		system = xReader.readSingleFromFile(DefaultStrings.CREATE_LOC.getDefault() + fileName + DefaultStrings.XML.getDefault());
		gameDetails.setDetails(system.getDetails());
		masterEntityList.addAll(system.getSharedEntitySystem().getAllEntities());
		if(system.getEntitySystem() != null){
			masterEnvironmentList.add(system.getEntitySystem());
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
		left.prefWidthProperty().bind(scrollPane.widthProperty().divide(2));
		right.prefWidthProperty().bind(scrollPane.widthProperty().divide(2));
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
		system.getSharedEntitySystem().addEntities(new ArrayList<IEntity>(masterEntityList));
		if(masterEnvironmentList.size()>0){
				system.setEntitySystem(masterEnvironmentList.get(0));
		}
		system.setDetails(gameDetails.getGameDetails());
		String name = DefaultStrings.CREATE_LOC.getDefault() + gameDetails.getGameDetails().get(Indexes.GAME_NAME.getIndex()) + DefaultStrings.XML.getDefault();
		system.saveSystem(name);
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
