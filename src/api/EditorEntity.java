package api;

import gui.GuiObject;
import gui.GuiObjectFactory;
import model.component.movement.Position;
import model.entity.Entity;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;


public class EditorEntity extends Editor{
	
	private Pane editorPane;
	private Entity myEntity;


	public EditorEntity(Entity entity) {
		editorPane = new Pane();
		myEntity = entity;
	}

	@Override
	public void loadDefaults() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Group getGroup() {
		populateLayout(editorPane);
		Group editorGroup = new Group();
		editorGroup.getChildren().add(editorPane);
		return editorGroup;
	}

	@Override
	public void populateLayout(Pane pane) {
		GuiObjectFactory guiFactory = new GuiObjectFactory();
		GuiObject object = guiFactory.createNewGuiObject("ORIENTATION", new Position(10.0, 10.0));
		pane.getChildren().add((Node) object.getGuiNode());
	}
	

	@Override
	public void updateEditor() {
		populateLayout(editorPane);
	}


}
