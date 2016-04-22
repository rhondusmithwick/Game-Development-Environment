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
import javafx.scene.control.ScrollPane;
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
	
	private GridPane editorPane;
	private IEntity myEntity;
	private VBox vbox;
	private String myLanguage;
	private ObservableList<ISerializable> entityList;
	private Button saveButton, addButton;
	private ResourceBundle myResources;
	private TextField name;
	private ScrollPane scrollPane;
	private int row = 0;
	private int column = 0;

	public EditorEntity(String language, ISerializable toEdit, ObservableList<ISerializable> addToList, ObservableList<ISerializable> emptyList) {
		editorPane = new GridPane();
		scrollPane = new ScrollPane(editorPane);
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
	public ScrollPane getPane() {
		return scrollPane;
	}

	@Override
	public void populateLayout() {
		name = Utilities.makeTextArea(myResources.getString("enterName"));
		name.setText(myEntity.getName());
		editorPane.add(name, column++, row);
		GuiObjectFactory guiFactory = new GuiObjectFactory(myLanguage);
		Collection<IComponent> componentList = myEntity.getAllComponents();
		for (IComponent component: componentList){
			for (SimpleObjectProperty<?> property: component.getProperties()){
				GuiObject object = guiFactory.createNewGuiObject(property.getName(), property, property.getValue());
				if (object!=null){
					editorPane.add((Node) object.getGuiNode(), column++, row);
					if(column > 4) {
						row++;
						column = 0;
					}
				}
			}
		}
		saveButton = Utilities.makeButton(myResources.getString("saveEntity"), e-> addSerializable(myEntity));
		addButton = Utilities.makeButton(myResources.getString("addComponent"), e-> addComponent());
		editorPane.add(saveButton, 2, ++row);
		editorPane.add(addButton, 3, row);
	}
	
	private void addComponent() {
		
	}
	

	@Override
	public void updateEditor() {
		populateLayout();
	}

	@Override
	public void addSerializable(ISerializable serialize) {	
		((IEntity) serialize).setName(name.getText());
		((IEntity) serialize).getAllComponents().stream().forEach(e->removeBinding(e));
		entityList.remove(serialize);
		entityList.add(serialize);
		editorPane.getChildren().clear();
		editorPane.getChildren().add((saveMessage(myResources.getString("saveMessage"))));
	}

	private void removeBinding(IComponent e) {
		e.removeBindings();
	}


}
