package view.editor;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import api.IEntity;
import api.ISerializable;
import enums.GUISize;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.component.movement.Position;
import model.component.visual.ImagePath;
import model.entity.Entity;
import model.entity.EntitySystem;
import view.DragAndResize;
import view.Utilities;

public class EditorEnvironment extends Editor{
	
	private EntitySystem myEntities;
	private GridPane environmentPane;
	private List<Node> viewList;
	private SubScene gameScene;
	private VBox entityOptions;
	private Group gameRoot;
	private static final String IMAGE_PATH = "resources/RhonduSmithwick.JPG";
	
	public EditorEnvironment(String language, ISerializable toEdit, ObservableList<ISerializable> masterList, ObservableList<ISerializable> addToList){
		//myResources = ResourceBundle.getBundle(language);
		myEntities = (EntitySystem) toEdit;
		myEntities.addEntity(new Entity());
		environmentPane = new GridPane();
		viewList = new ArrayList<Node>();
		addLayoutComponents();
	}

	private void addLayoutComponents(){
		setEntityOptions();
		setGameScene();
	}
	
	private void setGameScene() {
		gameRoot = new Group();
		gameScene = new SubScene(gameRoot,(GUISize.TWO_THIRDS_OF_SCREEN.getSize()),GUISize.HEIGHT_MINUS_TAB.getSize());
		gameScene.setFill(Color.WHITE);
		setAndAdd(gameScene,1,0,1,1);
	
		gameScene.setOnMousePressed(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	                printxevent(event);
	            }});
	}

	private void printxevent(MouseEvent event) {
		System.out.println("location of mouse on game scene. X: " + event.getX());
	}

	private void setEntityOptions() {
		entityOptions = new VBox();
		entityOptions.setMinWidth(GUISize.ONE_THIRD_OF_SCREEN.getSize()/3);
		entityOptions.setMinHeight(GUISize. HEIGHT_MINUS_TAB.getSize());
		ScrollPane pane = new ScrollPane(entityOptions);
		pane.setMinWidth(GUISize.ONE_THIRD_OF_SCREEN.getSize());
		pane.setMinHeight(GUISize. HEIGHT_MINUS_TAB.getSize());
		setAndAdd(pane,0,0,1,1);
		populateVbox(entityOptions);
	}

	private void populateVbox(VBox vbox) {
		try{
		Collection<IEntity> entityList = myEntities.getAllEntities();
		for (IEntity entity: entityList){
			Button entityButton = Utilities.makeButton(entity.toString(), e -> addToScene(entity));
			vbox.getChildren().add(entityButton);
			(entityButton).setMaxWidth(Double.MAX_VALUE);
			} }catch(NullPointerException e){
				// do nothing
			}
		}
	
	private void addToScene(IEntity entity) {
		if (!entity.hasComponent(Position.class)){
		Position pos = new Position();
		entity.forceAddComponent(pos, true);
		}
		if (!entity.hasComponent(ImagePath.class)){
		entity.forceAddComponent(new ImagePath(IMAGE_PATH), true);
		}
		updateEditor();
		ImageView entityView = createImage(entity.getComponent(ImagePath.class), entity.getComponent(Position.class));
        DragAndResize.makeResizable(entityView);
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
		// TODO Auto-generated method stub
	}

	@Override
	public void updateEditor() {
		entityOptions.getChildren().clear();
		populateVbox(entityOptions);
	}

	public void addEntitySystem(EntitySystem entitySystem) {
		myEntities = entitySystem;
		updateEditor();
	}

	public EntitySystem getEntitySystem() {
		return myEntities;
	}

	public Object getEntity(String i) {
		return myEntities.getEntity(i);
	}

	@Override
	public Pane getPane() {
		//populateLayout();
		return environmentPane;
	}

	@Override
	public void addSerializable(ISerializable serialize) {
		myEntities = (EntitySystem) serialize;
		updateEditor();
	}

}
