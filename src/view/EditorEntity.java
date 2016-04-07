package view;

import java.util.Collection;

import gui.GuiObject;
import gui.GuiObjectFactory;
import model.entity.Entity;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import api.IComponent;
/**
 * 
 * @author Melissa Zhang
 *
 */

public class EditorEntity extends Editor{
	
	private Pane editorPane;
	private Entity myEntity;


	public EditorEntity(Entity entity) {
		editorPane = new GridPane();
		myEntity = entity;
	}

	@Override
	public void loadDefaults() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Pane getPane() {
		populateLayout();
		return editorPane;
	}

	@Override
	public void populateLayout() {
		GuiObjectFactory guiFactory = new GuiObjectFactory();
		Collection<IComponent> componentList = myEntity.getAllComponents();
		for (IComponent component: componentList){
			System.out.println(component.getClass().getSimpleName());
			GuiObject object = guiFactory.createNewGuiObject(component.getClass().getSimpleName(), component);
			if (object!=null){
				editorPane.getChildren().add((Node) object.getGuiNode());
			}
		}
			

	}
	

	@Override
	public void updateEditor() {
		populateLayout();
	}


}
