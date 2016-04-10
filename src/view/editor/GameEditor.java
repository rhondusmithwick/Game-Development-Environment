package view.editor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import api.IEditor;
import api.ISerializable;
import enums.DefaultStrings;
import enums.FileExtensions;
import enums.GUISize;
import enums.ViewInsets;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.entity.Entity;
import model.entity.EntitySystem;
import view.Authoring;
import view.Utilities;

public class GameEditor extends Editor  {

	private VBox pane, entities, environments;
	private List<Node> entryList;
	private String iconPath;
	private ImageView icon;
	private ResourceBundle myResources;
	private EditorFactory editFact;
	private Authoring authEnv;
	private String myLanguage;
	private ObservableList<Entity> masterEntityList;
	private ObservableList<EntitySystem> masterEnvironmentList;


	public GameEditor(Authoring authEnv, String language){
		myLanguage = language;
		pane = new VBox(GUISize.GAME_EDITOR_PADDING.getSize());
		pane.setPadding(ViewInsets.GAME_EDIT.getInset());
		pane.setAlignment(Pos.TOP_LEFT);
		entryList = new ArrayList<>();
		myResources = ResourceBundle.getBundle(language);
		editFact = new EditorFactory();
		this.authEnv=authEnv;
		this.masterEntityList = FXCollections.observableArrayList();
		this.masterEnvironmentList = FXCollections.observableArrayList();
		addListners();

	}

	private void addListners() {
		masterEntityList.addListener(new ListChangeListener<Entity>() {

			@Override
			public void onChanged(@SuppressWarnings("rawtypes") ListChangeListener.Change change) {
				updateEntities();
			}
		});
		
		masterEnvironmentList.addListener(new ListChangeListener<EntitySystem>() {

			@Override
			public void onChanged(@SuppressWarnings("rawtypes") ListChangeListener.Change change) {
				updateEnvironments();
			}
		});



	}

	@Override
	public void loadDefaults() {
		// TODO Auto-generated method stub

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
		HBox container = new HBox(GUISize.GAME_EDITOR_PADDING.getSize());
		container.getChildren().addAll(left, right);
		pane.getChildren().addAll(container);

	}

	private VBox rightPane() {
		VBox temp = new VBox(GUISize.GAME_EDITOR_PADDING.getSize());
		temp.getChildren().add(createEntityList());
		temp.getChildren().add(createEnvList());
		return temp;


	}
	
	
	private VBox leftPane() {
		VBox temp = new VBox(GUISize.GAME_EDITOR_PADDING.getSize());
		temp.getChildren().add(createTextEntry("gName"));
		temp.getChildren().add(createTextEntry("gDesc"));
		temp.getChildren().add(showIcon());
		editorButtons(temp);
		temp.getChildren().add(Utilities.makeButton(myResources.getString("saveGame"), e->saveGame()));
		return temp;
	}
	

	private ScrollPane createEnvList() {
		ScrollPane scroll = new ScrollPane();
		updateEnvironments();
		scroll.setContent(environments);
		return scroll;
	}

	private void updateEnvironments() {
		environments = new VBox();
		//masterEnvironmentList.stream().forEach(e-> addEnvironmentToScroll(e, container));
	}

	//private void addEnvironmentToScroll(EntitySystem e, VBox container) {
	//container.getChildren().add(Utilities.makeButton(, handler))
	//}

	private ScrollPane createEntityList() {
		ScrollPane scroll = new ScrollPane();
		updateEntities();
		scroll.setContent(entities);
		return scroll;
	}

	private void updateEntities() {
		entities = new VBox();
		masterEntityList.stream().forEach(e-> addEntityToScroll(e, entities));
	}

	private void addEntityToScroll(Entity entity, VBox container) {
		container.getChildren().add(Utilities.makeButton(entity.getName(), f->createEditor(EditorEntity.class, (ISerializable) entity)));

	}


	private HBox createTextEntry(String name){
		HBox container = new HBox(GUISize.GAME_EDITOR_HBOX_PADDING.getSize());
		Label title = new Label(myResources.getString(name));
		TextArea entryBox = Utilities.makeTextArea(myResources.getString(name));
		container.getChildren().addAll(title, entryBox);
		HBox.setHgrow(entryBox, Priority.SOMETIMES);
		entryList.add(entryBox);
		return container;
	}

	private void saveGame() {
		// TODO Auto-generated method stub
	}

	private void editorButtons(VBox container) {
		container.getChildren().add(Utilities.makeButton(myResources.getString(DefaultStrings.ENTITY_EDITOR_NAME.getDefault()), 
				e->createEditor(EditorEntity.class, (ISerializable) new Entity())));
		container.getChildren().add(Utilities.makeButton(myResources.getString(DefaultStrings.ENVIRONMENT_EDITOR_NAME.getDefault()), 
				e->createEditor(EditorEnvironment.class, (ISerializable) new EntitySystem())));
	}
	
	private void createEditor(Class<?> editName, ISerializable passedParameter) {
		IEditor editor = editFact.createEditor(editName, passedParameter, myLanguage, new Button());
		editor.populateLayout();
		authEnv.createTab(editor.getPane(), editName.getSimpleName(), true);
	}

	private HBox showIcon() {
		HBox iconBox = new HBox(GUISize.GAME_EDITOR_HBOX_PADDING.getSize());
		iconBox.setAlignment(Pos.CENTER_LEFT);
		Label iconTitle = new Label(myResources.getString("gIcon"));
		icon = new ImageView();
		setIconPicture(new File(DefaultStrings.DEFAULT_ICON.getDefault()));
		iconBox.getChildren().addAll(iconTitle, icon, Utilities.makeButton(myResources.getString("cIcon"), e->updateIcon()));
		return iconBox;
	}

	private void setIconPicture(File file) {
		iconPath = file.toURI().toString();
		icon.setImage(new Image(iconPath));
		icon.setFitHeight(GUISize.ICON_SIZE.getSize());
		icon.setFitWidth(GUISize.ICON_SIZE.getSize());
	}

	private void updateIcon() {
		Stage s = new Stage();
		FileChooser fChoose = new FileChooser();
		fChoose.setTitle(myResources.getString("cIcon"));
		fChoose.getExtensionFilters().addAll(FileExtensions.GIF.getFilter(), FileExtensions.JPG.getFilter(), FileExtensions.PNG.getFilter());
		File file = fChoose.showOpenDialog(s);
		setIconPicture(file);
	}


	@Override
	public void updateEditor() {
		populateLayout();

	}

	@Override
	public void addSerializable(ISerializable serialize) {
		// TODO Auto-generated method stub

	}

}
