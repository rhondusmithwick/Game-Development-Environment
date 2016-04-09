package view.editor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import api.IEntity;
import api.ISerializable;
import enums.GUISize;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.entity.EntitySystem;
import view.Utilities;

public class EditorEnvironment extends Editor{
	
	private ResourceBundle myResources;
	private EntitySystem myEntities;
	private GridPane environmentPane;
	private List<Node> viewList;
	private SubScene gameScene;
	private VBox entityOptions;
	private Group gameRoot;
	
	public EditorEnvironment(String language, ISerializable entities){
		myResources = ResourceBundle.getBundle(language);
		myEntities = (EntitySystem) entities;
		environmentPane = new GridPane();
		viewList = new ArrayList<Node>();
		addLayoutComponents();
	}

	private void addLayoutComponents(){
		entityOptions = new VBox();
		entityOptions.setMinWidth(GUISize.ONE_THIRD_OF_SCREEN.getSize()/3);
		entityOptions.setMinHeight(GUISize. HEIGHT_MINUS_TAB.getSize());
		ScrollPane pane = new ScrollPane(entityOptions);
		pane.setMinWidth(GUISize.ONE_THIRD_OF_SCREEN.getSize());
		pane.setMinHeight(GUISize. HEIGHT_MINUS_TAB.getSize());
		setAndAdd(pane,0,0,1,1);
		populateVbox(entityOptions);
		
		gameRoot = new Group();
		gameScene = new SubScene(gameRoot,(GUISize.TWO_THIRDS_OF_SCREEN.getSize()),GUISize.HEIGHT_MINUS_TAB.getSize());
		gameScene.setFill(Color.WHITE);
		setAndAdd(gameScene,1,0,1,1);
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

	@Override
	public void addSerializable(ISerializable serialize) {
		myEntities = (EntitySystem) serialize;
		
	}

}
