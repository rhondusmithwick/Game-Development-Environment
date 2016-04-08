package view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import api.IEntity;
import gui.GuiObject;
import gui.GuiObjectFactory;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.entity.EntitySystem;

public class EditorEnvironment extends Editor{
	
	private ResourceBundle myResources;
	private EntitySystem myEntities;
	private GridPane environmentPane;
	private List<Node> viewList;
	private Utilities utilities;
	private SubScene gameScene;
	private VBox entityOptions;
	private Group gameRoot;
	
	public EditorEnvironment(EntitySystem entitySystem){
		myResources = ResourceBundle.getBundle("authoring");
		myEntities = entitySystem;
		environmentPane = new GridPane();
		utilities = new Utilities();
		viewList = new ArrayList<Node>();
		addLayoutComponents();
	}
	
	private void addLayoutComponents(){
		entityOptions = new VBox();
		setAndAdd(entityOptions,0,0,1,1);
		populateVbox(entityOptions);
		gameRoot = new Group();
		gameScene = new SubScene(gameRoot,Integer.parseInt(myResources.getString("gameSceneHeight")),Integer.parseInt(myResources.getString("gameSceneWidth")));
		gameScene.setFill(Color.WHITE);
		setAndAdd(gameScene,1,0,1,1);
	}
	
	private void populateVbox(VBox vbox) {
		Collection<IEntity> entityList = myEntities.getAllEntities();
		for (IEntity entity: entityList){
			try{
			Button entityButton = Utilities.makeButton(entity.toString(), e -> addToScene(entity));
			vbox.getChildren().add(entityButton);
			} catch(NullPointerException e){
				// do nothing
			}
		}
	}
	
	private Object addToScene(IEntity entity) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
	}

	public void addEntitySystem(EntitySystem entitySystem) {
		myEntities = entitySystem;
	}

	public EntitySystem getEntitySystem() {
		return myEntities;
	}

	public Object getEntity(int i) {
		return myEntities.getEntity(i);
	}

	@Override
	public Pane getPane() {
		//populateLayout();
		return environmentPane;
	}

}
