package view.editor.entityeditor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;
import view.Utilities;
import view.editor.Editor;
import view.enums.DefaultStrings;
import gui.GuiObject;
import gui.GuiObjectFactory;
import model.entity.Entity;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;
import api.IComponent;
import api.IEntity;
import api.ISerializable;
/**
 * 
 * @author Melissa Zhang
 * @author Cali Nelson
 * @author Ben Zhang
 *
 */

public class EditorEntity extends Editor{
	private IEntity myEntity;
	private String myLanguage;
	private ObservableList<ISerializable> entityList;
	private Button saveButton, addButton, removeButton;
	private ResourceBundle myResources, myLocs;
	private TextField name;
	private ScrollPane scrollPane;
	private List<String> myComponents;
	private VBox container;
	private final GuiObjectFactory guiFactory = new GuiObjectFactory();


	public EditorEntity(String language, ISerializable toEdit, ObservableList<ISerializable> addToList) {
		scrollPane = new ScrollPane();
		myLanguage = language;
		myResources = ResourceBundle.getBundle(language);
		myEntity = (Entity) toEdit;
		entityList = addToList;
		
	}

	private void getComponents() {
		myLocs = ResourceBundle.getBundle(DefaultStrings.COMPONENT_LOC.getDefault());
		Enumeration<String> iter = myLocs.getKeys();
		while(iter.hasMoreElements()) {
			myComponents.add(myResources.getString(iter.nextElement()));
		}
	}

	@Override
	public ScrollPane getPane() {
		return scrollPane;
	}

	@Override
	public void populateLayout() {
		container = new VBox();
		scrollPane.setContent(container);
		container.getStyleClass().add("vbox");
		myComponents = new ArrayList<String>();
		addName();
		addComponentsToPane();
		addButtons();
	}

	private void addButtons() {
		saveButton = Utilities.makeButton(myResources.getString("saveEntity"), e -> save());
		addButton = Utilities.makeButton(myResources.getString("addComponent"), e -> addComponent());
		removeButton = Utilities.makeButton(myResources.getString("removeComp"), e->removeComponent());
		container.getChildren().addAll(addButton, removeButton, saveButton);
	}

	private void addName() {
		name = Utilities.makeTextArea(myResources.getString("enterName"));
		name.setText(myEntity.getName());
		container.getChildren().add(name);
	}

	private void addComponentsToPane() {
		Collection<IComponent> componentList = myEntity.getAllComponents();
		for (IComponent component: componentList){
			addObject(component);
		}
	}
	
	private void addObject(IComponent component) {
		component.getProperties().stream().forEach(e -> addVisualObject(e));
	}

	private void addVisualObject(SimpleObjectProperty<?> property) {
		GuiObject object = guiFactory.createNewGuiObject(property.getName(), DefaultStrings.GUI_RESOURCES.getDefault(),myLanguage, property, property.getValue());
		if (object != null){
			container.getChildren().add((Node) object.getGuiNode());
		}
	}

	private void removeComponent() {
		(new ComponentRemover(myLanguage, myEntity)).modifyComponentList();
		updateEditor();
	}
	
	private void addComponent() {
		(new ComponentAdder(myLanguage, myEntity)).modifyComponentList();
		updateEditor();
	}


	@Override
	public void updateEditor() {
		getComponents();
		populateLayout();
	}

	private void save() {	
		myEntity.setName(name.getText());
		myEntity.getAllComponents().stream().forEach(e -> removeBinding(e));
		entityList.remove(myEntity);
		entityList.add(myEntity);
		container = new VBox();
		container.getChildren().add(super.saveMessage(myResources.getString("saveMessage")));
		scrollPane.setContent(container);
	}

	private void removeBinding(IComponent e) {
		e.removeBindings();
	}
}