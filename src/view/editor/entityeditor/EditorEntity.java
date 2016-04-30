package view.editor.entityeditor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;
import view.editor.Editor;
import view.enums.DefaultStrings;
import view.utilities.ButtonFactory;
import view.utilities.TextFieldFactory;
import model.entity.Entity;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;
import api.IComponent;
import api.IEntity;
import guiObjects.GuiObject;
import guiObjects.GuiObjectFactory;
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
	private ObservableList<IEntity> entityList = FXCollections.observableArrayList();
	private Button saveButton, addButton, removeButton;
	private ResourceBundle myResources, myLocs, myComponentNames;
	private TextField name;
	private ScrollPane scrollPane;
	private List<String> myComponents;
	private VBox container;
	private final GuiObjectFactory guiFactory = new GuiObjectFactory();


	public EditorEntity(String language, IEntity toEdit){
		scrollPane = new ScrollPane();
		myLanguage = language;
		myResources = ResourceBundle.getBundle(language);
		myComponentNames = ResourceBundle.getBundle(language + DefaultStrings.COMPONENTS.getDefault());
		myEntity = (Entity) toEdit;
	}
	
	public EditorEntity(String language, IEntity toEdit, ObservableList<IEntity> addToList) {
		this(language, toEdit);
		entityList = addToList;	
	}

	private void getComponents() {
		myLocs = ResourceBundle.getBundle(DefaultStrings.COMPONENT_LOC.getDefault());
		Enumeration<String> iter = myLocs.getKeys();
		while(iter.hasMoreElements()) {
			myComponents.add(myComponentNames.getString(iter.nextElement()));
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
		saveButton = ButtonFactory.makeButton(myResources.getString("saveEntity"), e -> save());
		addButton = ButtonFactory.makeButton(myResources.getString("addComponent"), e -> addComponent());
		removeButton = ButtonFactory.makeButton(myResources.getString("removeComponent"), e->removeComponent());
		container.getChildren().addAll(addButton, removeButton, saveButton);
	}

	private void addName() {
		name = TextFieldFactory.makeTextArea(myResources.getString("enterName"));
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
		myEntity.updateComponents();
		myEntity.setName(name.getText());
		myEntity.getAllComponents().stream().forEach(e -> removeBindings(e));
		entityList.remove(myEntity);
		entityList.add(myEntity);
		container = new VBox();
		container.getChildren().add(super.saveMessage(myResources.getString("saveMessage")));
		scrollPane.setContent(container);
	}

	private void removeBindings(IComponent e) {
		e.removeBindings();
		
	}
}
