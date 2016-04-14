package view.editor;

import java.util.Collection;
import java.util.ResourceBundle;
import view.Utilities;
import gui.GuiObject;
import gui.GuiObjectFactory;
import model.entity.Entity;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
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
	private ResourceBundle myResources;
	private TextField name;

	public EditorEntity(String language, ISerializable toEdit, ObservableList<ISerializable> addToList, ObservableList<ISerializable> emptyList) {
		editorPane = new GridPane();
		myLanguage = language;
		myResources = ResourceBundle.getBundle(language);
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
		vbox.setAlignment(Pos.CENTER);
		name = Utilities.makeTextArea(myResources.getString("enterName"));
		name.setText(myEntity.getName());
		vbox.getChildren().add(name);
		GuiObjectFactory guiFactory = new GuiObjectFactory(myLanguage);
		Collection<IComponent> componentList = myEntity.getAllComponents();
		
		for (IComponent component: componentList){
			for (SimpleObjectProperty<?> property: component.getProperties()){
				//System.out.println(component.getProperties());
				GuiObject object = guiFactory.createNewGuiObject(property.getName(), property, property.getValue());
				if (object!=null){
					vbox.getChildren().add((Node) object.getGuiNode());
				}
			}
		}
		saveButton = Utilities.makeButton(myResources.getString("saveEntity"), e-> addSerializable(myEntity));
		vbox.getChildren().add(saveButton);
		editorPane.getChildren().add(vbox);

	}
	

	@Override
	public void updateEditor() {
		populateLayout();
	}

	@Override
	public void addSerializable(ISerializable serialize) {	
		((IEntity) serialize).setName(name.getText());
		entityList.remove(serialize);
		entityList.add(serialize);
		editorPane.getChildren().clear();
		editorPane.getChildren().add((saveMessage(myResources.getString("saveMessage"))));
		

	}


}
