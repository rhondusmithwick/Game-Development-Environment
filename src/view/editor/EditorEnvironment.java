package view.editor;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import api.IEntity;
import api.IEntitySystem;
import api.ISerializable;
import enums.DefaultStrings;
import enums.GUISize;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.SubScene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import model.component.movement.Position;
import model.component.visual.ImagePath;
import model.entity.Entity;
import model.entity.EntitySystem;
import view.DragAndResize;
import view.LoadDefaults;
import view.Utilities;

public class EditorEnvironment extends Editor{
	
	private BorderPane environmentPane;
	private List<Node> viewList;
	private SubScene gameScene;
	private VBox entityOptions;
	private Group gameRoot;
	private ResourceBundle myResources;
	private IEntitySystem envionmentEntities;
	private ObservableList<ISerializable> displayEntities;
	private ObservableList<ISerializable> whenSave;
	
	@SuppressWarnings("unchecked")
	public EditorEnvironment(String language, ISerializable toEdit, ObservableList<ISerializable>masterList, ObservableList<ISerializable> addToList){
		myResources = ResourceBundle.getBundle(language);
		masterList.addListener((ListChangeListener<? super ISerializable>) c -> {this.updateDisplay(masterList);}); 
		displayEntities = masterList;
		envionmentEntities = (IEntitySystem) toEdit;
		whenSave = addToList;
		addLayoutComponents();
	}

	private void updateDisplay(ObservableList<ISerializable> masterList) {
		displayEntities = masterList;
		updateEditor();
	}

	private void addLayoutComponents(){
		environmentPane = new BorderPane();
		viewList = new ArrayList<Node>();
		setEntityOptions();
		setGameScene();
		setSaveButton();
	}
	
	private void setSaveButton() {
		Button saveButton = Utilities.makeButton("Save Environment", e-> saveEnvironment());
		environmentPane.setRight(saveButton);
	}

	private void saveEnvironment() {
		whenSave.add(envionmentEntities);
	}

	private void setGameScene() {
		gameRoot = new Group();
		gameScene = new SubScene(gameRoot,(GUISize.TWO_THIRDS_OF_SCREEN.getSize()),GUISize.HEIGHT_MINUS_TAB.getSize());
		gameScene.setFill(Color.WHITE);
		environmentPane.setCenter(gameScene);
	}

	private void setEntityOptions() {
		entityOptions = new VBox();
		ScrollPane pane = new ScrollPane(entityOptions);
		environmentPane.setLeft(pane);
		populateVbox(entityOptions,displayEntities);
		loadDefaults();
	}

	private void populateVbox(VBox vbox, ObservableList<ISerializable> entityChoices) {
		try{
			if (entityChoices.isEmpty()){
				Utilities.showAlert("Information",null,myResources.getString(DefaultStrings.NO_ENTITIES.getDefault()),AlertType.INFORMATION);
			}
		for (ISerializable entity: entityChoices){
			Button entityButton = Utilities.makeButton(((IEntity) entity).getName(), e -> addToScene((IEntity) entity));
			(entityButton).setMaxWidth(Double.MAX_VALUE);
			vbox.getChildren().add(entityButton);
			} }catch(NullPointerException e){
				// do nothing, no real error 
			}
		}
	
	private void addToScene(IEntity entity) {
		if (!entity.hasComponent(Position.class)){
			if (Utilities.showAlert("Confirm.", "Entity must have a position component to be displayed.", "Is it okay to add this component?",AlertType.CONFIRMATION)){
			entity.setSpec(Position.class, 1); 
			Position pos = new Position();
			entity.addComponent(pos);
			}
			else{
				return;
			}
		}
		if (!entity.hasComponent(ImagePath.class)){
			if (Utilities.showAlert("Confirm.", "Entity must have an image component to be displayed.", "Is it okay to add this component?",AlertType.CONFIRMATION)){
				try{
			    List<ExtensionFilter> filters = new ArrayList<ExtensionFilter>();
			    filters.add(new FileChooser.ExtensionFilter("All Images", "*.*"));
			    filters.add(new FileChooser.ExtensionFilter("JPG", "*.jpg"));
			    filters.add(new FileChooser.ExtensionFilter("PNG", "*.png"));
				File file = Utilities.promptAndGetFile(filters,"Pick the image for the Image Path Component.");
				entity.setSpec(ImagePath.class, 1); 
				entity.addComponent(new ImagePath(file.getPath()));
				} catch(Exception e) {
					Utilities.showAlert("ERROR",null, "Unable to add entity to the scene.",AlertType.ERROR);
					return;
				}
			}
			else{
				return;
			}
		}
		//updateEditor();
		IEntity newEntity = Utilities.copyEntity(entity);
		ImageView entityView = Utilities.createImage(newEntity.getComponent(ImagePath.class), newEntity.getComponent(Position.class));
        DragAndResize.makeResizable(entityView, newEntity.getComponent(ImagePath.class), newEntity.getComponent(Position.class));
        entityView.setOnMouseClicked(new EventHandler<MouseEvent>(){
    		@Override
    		public void handle(MouseEvent event){
    			MouseButton button = event.getButton();
                if(button==MouseButton.SECONDARY){
                    removeFromDisplay(entityView,newEntity);
                }
    		}});
        if (!envionmentEntities.containsEntity(newEntity)){
        envionmentEntities.addEntity(newEntity);
		gameRoot.getChildren().add(entityView);
        }
	}

	protected void removeFromDisplay(ImageView entityView, IEntity entity) {
		gameRoot.getChildren().remove(entityView);
		envionmentEntities.removeEntity(entity.getID());
	}
		
	@Override
	public void populateLayout() {
		//environmentPane.getChildren().addAll(viewList);
	}

	@Override
	public void loadDefaults() {
		if  (Utilities.showAlert("Add Defaults", "We have some defaults we can add!", 
				"Do you want to go ahead and let us add some default entities for your use?", AlertType.CONFIRMATION)){
		displayEntities.add(LoadDefaults.loadBackgroundDefault());
		displayEntities.add(LoadDefaults.loadPlatformDefault(displayEntities));
		//populateVbox(entityOptions,displayEntities);
		}
	}

	@Override
	public void updateEditor() {
		entityOptions.getChildren().clear();
		populateVbox(entityOptions,displayEntities);
	}

	public IEntitySystem getEntitySystem() {
		return envionmentEntities;
	}

	@Override
	public Pane getPane() {
		//populateLayout();
		return environmentPane;
	}

	@Override
	public void addSerializable(ISerializable serialize) {
		envionmentEntities.addEntity((IEntity) serialize);
	}

	public boolean displayContains(IEntity checkEntity) {
		return displayEntities.contains(checkEntity);
	}

}
