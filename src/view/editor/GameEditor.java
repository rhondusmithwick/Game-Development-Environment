package view.editor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import api.IDataWriter;
import api.IEditor;
import api.ISerializable;
import datamanagement.XMLReader;
import datamanagement.XMLWriter;
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
import model.entity.Entity;
import model.entity.EntitySystem;
import view.Authoring;
import view.Utilities;

public class GameEditor extends Editor  {

	private VBox pane, entities, environments;
	private ResourceBundle myResources;
	private EditorFactory editFact;
	private Authoring authEnv;
	private String myLanguage;
	private ObservableList<ISerializable> masterEntityList;
	private ObservableList<ISerializable> masterEnvironmentList;
	private GameDetails gameDetails;


	public GameEditor(Authoring authEnv, String language){
		myLanguage = language;
		gameDetails = new GameDetails(language);
		myResources = ResourceBundle.getBundle(language);
		editFact = new EditorFactory();
		this.authEnv=authEnv;
		this.masterEntityList = FXCollections.observableArrayList();
		this.masterEnvironmentList = FXCollections.observableArrayList();
		addShit();
		setPane();
		addListeners();
	}

	private void addShit() {
		Entity temp = new Entity("aaaa");
		masterEntityList.add(temp);
		EntitySystem tt = new EntitySystem();
		tt.addEntity(temp);
		temp = new Entity("bbb");
		masterEntityList.add(new Entity("bbbb"));
		tt.addEntity(temp);
		masterEnvironmentList.add(tt);
		
	}

	private void setPane() {
		pane = new VBox(GUISize.GAME_EDITOR_PADDING.getSize());
		pane.setPadding(ViewInsets.GAME_EDIT.getInset());
		pane.setAlignment(Pos.TOP_LEFT);
	}
	
	public GameEditor(Authoring authEnv, String language, String fileName){
		this(authEnv, language);
		loadFile(fileName);
	}

	private void loadFile(String fileName) {
		XMLReader<SaveGame> xReader  = new XMLReader<SaveGame>();
		SaveGame s = xReader.readSingleFromFile(DefaultStrings.CREATE_LOC.getDefault() + fileName+ DefaultStrings.XML.getDefault());
		gameDetails.setDetails(Arrays.asList(s.getName(), s.getDesc(), s.getIcon()));
		masterEntityList = FXCollections.observableArrayList(s.getEntites());
		masterEnvironmentList = FXCollections.observableArrayList(s.getEnvironments());
		System.out.println(masterEnvironmentList.get(0));
	}

	
	private void addListeners() {	
		masterEntityList.addListener(new ListChangeListener<ISerializable>() {

			@Override
			public void onChanged(@SuppressWarnings("rawtypes") ListChangeListener.Change change) {
			
				updateEntities();
			}
		});
		
		masterEnvironmentList.addListener(new ListChangeListener<ISerializable>() {

			@Override
			public void onChanged(@SuppressWarnings("rawtypes") ListChangeListener.Change change) {
				updateEnvironments();
			}
		});

	}

	@Override
	public void loadDefaults() {}

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
		temp.getChildren().add(createEntityList());
		temp.getChildren().add( new Label(myResources.getString("environments")));
		temp.getChildren().add(createEnvList());
		return temp;
	}
	
	private VBox leftPane() {
		VBox temp = new VBox(GUISize.GAME_EDITOR_PADDING.getSize());
		temp.getChildren().addAll(gameDetails.getElements());
		editorButtons(temp);
		temp.getChildren().add(Utilities.makeButton(myResources.getString("saveGame"), e->saveGame()));
		return temp;
	}
	

	private ScrollPane createEnvList() {
		ScrollPane scroll = new ScrollPane();
		environments = new VBox(GUISize.SCROLL_PAD.getSize());
		updateEnvironments();
		scroll.setContent(environments);
		return scroll;
	}

	private void updateEnvironments() {
		environments.getChildren().remove(environments.getChildren());
		//masterEnvironmentList.stream().forEach(e-> addEnvironmentToScroll(e, container));
	}

	//private void addEnvironmentToScroll(EntitySystem e, VBox container) {
		//container.getChildren().add(Utilities.makeButton(, handler))
	//}

	private ScrollPane createEntityList() {
		ScrollPane scroll = new ScrollPane();
		entities = new VBox(GUISize.SCROLL_PAD.getSize());
		updateEntities();
		scroll.setContent(entities);
		return scroll;
	}

	private void updateEntities() {
			entities.getChildren().remove(entities.getChildren());
		masterEntityList.stream().forEach(e-> addEntityToScroll(e, entities));
	}

	private void addEntityToScroll(ISerializable entity, VBox container) {
		container.getChildren().add(Utilities.makeButton(((Entity) entity).getName(), f->createEditor(EditorEntity.class, entity, FXCollections.observableArrayList())));

	}

	private void saveGame() {
		SaveGame sGame = new SaveGame(gameDetails.getGameDetails(), new ArrayList<ISerializable>(masterEntityList), new ArrayList<ISerializable>(masterEnvironmentList));
		IDataWriter<SaveGame> writer = new XMLWriter<>();
		String name = gameDetails.getGameDetails().get(Indexes.GAME_NAME.getIndex());
		writer.writeToFile(DefaultStrings.CREATE_LOC.getDefault() + name.trim()+ DefaultStrings.XML.getDefault(),sGame);
		System.out.println("Saved");
	}

	private void editorButtons(VBox container) {
		container.getChildren().add(Utilities.makeButton(myResources.getString(DefaultStrings.ENTITY_EDITOR_NAME.getDefault()), 
				e->createEditor(EditorEntity.class, new Entity(), FXCollections.observableArrayList())));
		container.getChildren().add(Utilities.makeButton(myResources.getString(DefaultStrings.ENVIRONMENT_EDITOR_NAME.getDefault()), 
				e->createEditor(EditorEnvironment.class, new EntitySystem(), masterEnvironmentList)));
	}
	
	private void createEditor(Class<?> editName, ISerializable toEdit, ObservableList<ISerializable> otherList) {
		IEditor editor = editFact.createEditor(editName,  myLanguage,toEdit, masterEntityList, otherList);
		editor.populateLayout();
		authEnv.createTab(editor.getPane(), editName.getSimpleName(), true);
	}

	
	@Override
	public void updateEditor() {
		populateLayout();

	}

	@Override
	public void addSerializable(ISerializable serialize) {}

}
