package view.editor;

import java.util.Collection;

import view.Utilities;
import gui.GuiObject;
import gui.GuiObjectFactory;
import model.entity.Entity;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
	private VBox vbox;
	private String myLanguage;
	private ObservableList<ISerializable> entityList;
	private Button saveButton;

	public EditorEntity(String language, ISerializable toEdit, ObservableList<ISerializable> addToList, ObservableList<ISerializable> emptyList) {
		editorPane = new GridPane();
		myLanguage = language;
		myEntity = (Entity) toEdit;
		entityList = addToList;

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
		vbox = new VBox();
		GuiObjectFactory guiFactory = new GuiObjectFactory(myLanguage);
		Collection<IComponent> componentList = myEntity.getAllComponents();
		
		for (IComponent component: componentList){
			for (SimpleObjectProperty<?> property: component.getProperties()){
				System.out.println(component.getProperties());
				GuiObject object = guiFactory.createNewGuiObject(property.getName(), property, property.getValue());
				if (object!=null){
					vbox.getChildren().add((Node) object.getGuiNode());
				}
			}
		}
			
		saveButton = Utilities.makeButton("Save Entity", e-> addSerializable(myEntity));
		vbox.getChildren().add(saveButton);
		editorPane.getChildren().add(vbox);

	}
	

	@Override
	public void updateEditor() {
		populateLayout();
	}

	@Override
	public void addSerializable(ISerializable serialize) {
		System.out.println("Saved Entity");
		entityList.add(serialize);
	}


}
