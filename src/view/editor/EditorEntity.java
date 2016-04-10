package view.editor;

import java.util.Collection;

import gui.GuiObject;
import gui.GuiObjectFactory;
import model.entity.Entity;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import api.IComponent;
import api.IEntity;
import api.ISerializable;
/**
 * 
 * @author Melissa Zhang
 *
 */

public class EditorEntity extends Editor{
	
	private Pane editorPane;
	private IEntity myEntity;

	public EditorEntity(ISerializable entity, String language, Button button) {
		editorPane = new GridPane();
		myEntity = (Entity) entity;
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
			GuiObject object = guiFactory.createNewGuiObject(component.getClass().toString(), component);
			if (object!=null){
				editorPane.getChildren().add((Node) object.getGuiNode());
			}
		}
			

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
