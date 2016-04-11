package view.editor;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import api.IEntity;
import api.ISerializable;
import enums.DefaultStrings;
import enums.GUISize;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import model.component.movement.Position;
import model.component.visual.ImagePath;
import model.entity.Entity;
import view.DragAndResize;
import view.LoadDefaults;
import view.Utilities;

public class EditorEnvironment extends Editor{
	
	private static final List<Node> environmentEntities = null;
	private GridPane environmentPane;
	private List<Node> viewList;
	private SubScene gameScene;
	private VBox entityOptions;
	private Group gameRoot;
	private ResourceBundle myResources;
	private ObservableList<ISerializable> masterEntities;
	private ObservableList<ISerializable> envionmentEntities;
	private ObservableList<ISerializable> displayEntities;
	
	public EditorEnvironment(String language, ISerializable toEdit, ObservableList<ISerializable>masterList, ObservableList<ISerializable> addToList){
		myResources = ResourceBundle.getBundle(language);
		masterEntities = masterList;
		displayEntities = masterList;
		envionmentEntities = addToList;
		addLayoutComponents();
	}

	private void addLayoutComponents(){
		environmentPane = new GridPane();
		viewList = new ArrayList<Node>();
		setEntityOptions();
		setGameScene();
	}
	
	private void setGameScene() {
		gameRoot = new Group();
		gameScene = new SubScene(gameRoot,(GUISize.TWO_THIRDS_OF_SCREEN.getSize()),GUISize.HEIGHT_MINUS_TAB.getSize());
		gameScene.setFill(Color.WHITE);
		setAndAdd(gameScene,1,0,1,1);
	}

	private void setEntityOptions() {
		entityOptions = new VBox();
		entityOptions.setMinWidth(GUISize.ONE_THIRD_OF_SCREEN.getSize()/3);
		entityOptions.setMinHeight(GUISize. HEIGHT_MINUS_TAB.getSize());
		ScrollPane pane = new ScrollPane(entityOptions);
		pane.setMinWidth(GUISize.ONE_THIRD_OF_SCREEN.getSize());
		pane.setMinHeight(GUISize. HEIGHT_MINUS_TAB.getSize());
		setAndAdd(pane,0,0,1,1);
		populateVbox(entityOptions,displayEntities);
		loadDefaults();
	}

	private void populateVbox(VBox vbox, ObservableList<ISerializable> entityChoices) {
		try{
			if (entityChoices.isEmpty()){
				Utilities.showError("",myResources.getString(DefaultStrings.NO_ENTITIES.getDefault()));
			}
		for (ISerializable entity: entityChoices){
			Button entityButton = Utilities.makeButton(entity.toString(), e -> addToScene((IEntity) entity));
			(entityButton).setMaxWidth(Double.MAX_VALUE);
			vbox.getChildren().add(entityButton);
			} }catch(NullPointerException e){
				// do nothing, no real error 
			}
		}
	
	private void addToScene(IEntity entity) {
		if (!entity.hasComponent(Position.class)){
			ButtonType answer = Utilities.confirmationBox("Confirm.", "Entity must have a position component to be displayed.", "Is it okay to add this component?");
			if (answer == ButtonType.OK){
			entity.setSpec(Position.class, 1); 
			Position pos = new Position();
			entity.addComponent(pos);
			}
			else{
				return;
			}
		}
		if (!entity.hasComponent(ImagePath.class)){
			ButtonType answer = Utilities.confirmationBox("Confirm.", "Entity must have an image component to be displayed.", "Is it okay to add this component?");
			if (answer == ButtonType.OK){
				try{
			    List<ExtensionFilter> filters = new ArrayList<ExtensionFilter>();
			    filters.add(new FileChooser.ExtensionFilter("All Images", "*.*"));
			    filters.add(new FileChooser.ExtensionFilter("JPG", "*.jpg"));
			    filters.add(new FileChooser.ExtensionFilter("PNG", "*.png"));
				File file = Utilities.promptAndGetFile(filters,"Pick the image for the Image Path Component.");
				entity.setSpec(ImagePath.class, 1); 
				entity.addComponent(new ImagePath(file.getPath()));
				} catch(Exception e) {
					Utilities.showError("ERROR", "Unable to add entity to the scene.");
					return;
				}
			}
			else{
				return;
			}
		}
		//updateEditor();
		ImageView entityView = createImage(entity.getComponent(ImagePath.class), entity.getComponent(Position.class));
        DragAndResize.makeResizable(entityView);
        envionmentEntities.add(entity);
		gameRoot.getChildren().add(entityView);
	}

		private ImageView createImage(ImagePath path, Position pos) {
			URI resource = new File(path.getImagePath()).toURI();
			Image image = new Image(resource.toString());
			ImageView imageView = new ImageView(image);
			imageView.setFitHeight(100);
			imageView.setPreserveRatio(true);
			imageView.xProperty().bind(pos.xProperty());
			imageView.yProperty().bind(pos.yProperty());
			return imageView;
		}

	private void setAndAdd(Node node, int col, int row, int colspan, int rowspan) {
		GridPane.setConstraints(node, col, row, colspan, rowspan, HPos.CENTER, VPos.CENTER);
		viewList.add(node);
		try {
			((Region) node).setMaxWidth(Double.MAX_VALUE);
		} catch (ClassCastException e) {
			// do nothing
		}
	}
		
	@Override
	public void populateLayout() {
		environmentPane.getChildren().addAll(viewList);
	}

	@Override
	public void loadDefaults() {
		displayEntities.add(LoadDefaults.loadBackgroundDefault());
		displayEntities.add(LoadDefaults.loadPlatformDefault(masterEntities));
		populateVbox(entityOptions,displayEntities);
	}

	@Override
	public void updateEditor() {
		entityOptions.getChildren().clear();
		populateVbox(entityOptions,displayEntities);
	}

	public ObservableList<ISerializable> getEntitySystem() {
		return envionmentEntities;
	}

	@Override
	public Pane getPane() {
		//populateLayout();
		return environmentPane;
	}

	@Override
	public void addSerializable(ISerializable serialize) {
		envionmentEntities.add(serialize);
	}

}
