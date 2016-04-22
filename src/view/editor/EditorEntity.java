package view.editor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;
import view.Utilities;
import view.enums.DefaultStrings;
import view.enums.GUISize;
import gui.GuiObject;
import gui.GuiObjectFactory;
import model.entity.Entity;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import api.IComponent;
import api.IEntity;
import api.ISerializable;
/**
 * 
 * @author Melissa Zhang & Cali Nelson
 *
 */

public class EditorEntity extends Editor{

	private GridPane editorPane;
	private IEntity myEntity;
	private String myLanguage;
	private ObservableList<ISerializable> entityList;
	private Button saveButton, addButton;
	private ResourceBundle myResources, myLocs;
	private TextField name;
	private ScrollPane scrollPane;
	private int row = 0;
	private int column = 0;
	private ComboBox<String> componentBox;
	private List<String> myComponents;
	private HBox container;
	private GuiObjectFactory guiFactory;

	public EditorEntity(String language, ISerializable toEdit, ObservableList<ISerializable> addToList, ObservableList<ISerializable> emptyList) {
		editorPane = new GridPane();
		scrollPane = new ScrollPane(editorPane);
		myComponents = new ArrayList<String>();
		myLanguage = language;
		container = new HBox(GUISize.ENTITY_EDITOR_PADDING.getSize());
		myResources = ResourceBundle.getBundle(language);
		myEntity = (Entity) toEdit;
		entityList = addToList;
		editorPane.setHgap(GUISize.ENTITY_EDITOR_PADDING.getSize());
		editorPane.setVgap(GUISize.ENTITY_EDITOR_PADDING.getSize());
		getComponents(DefaultStrings.COMPONENT_LOC.getDefault());
	}

	private void getComponents(String loc) {
		myLocs = ResourceBundle.getBundle(loc);
		Enumeration<String> iter = myLocs.getKeys();
		while(iter.hasMoreElements()) {
			myComponents.add(iter.nextElement());
		}
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
		Collection<IComponent> componentList = myEntity.getAllComponents();
		for (IComponent component: componentList){
			addObject(component);
		}
		saveButton = Utilities.makeButton(myResources.getString("saveEntity"), e-> save());
		addButton = Utilities.makeButton(myResources.getString("addComponent"), e-> addComponent());
		editorPane.add(saveButton, GUISize.HALF_COLUMNS.getSize() + GUISize.ONE.getSize(), row+GUISize.ONE.getSize());
		componentBox = Utilities.makeComboBox(myResources.getString("chooseComponent"), myComponents, null);
		container.getChildren().addAll(Arrays.asList(componentBox, addButton));
		editorPane.add(container, GUISize.HALF_COLUMNS.getSize()+GUISize.ONE.getSize(), row+GUISize.ONE.getSize());
	}

	private void addObject(IComponent component) {
		guiFactory = new GuiObjectFactory(myLanguage);
		component.getProperties().stream().forEach(e->addVisualObject(e));
	}

	private void addVisualObject(SimpleObjectProperty<?> property) {
		GuiObject object = guiFactory.createNewGuiObject(property.getName(), property, property.getValue());
		if (object!=null){
			editorPane.add((Node) object.getGuiNode(), column++, row);
			if(column > GUISize.GRIDPANE_COLUMNS.getSize()) {
				row++;
				column = 0;
			}
			myComponents.remove(property.getName());
		}
	}

	private void addComponent() {
		String selected = componentBox.getSelectionModel().getSelectedItem();
		componentBox.getSelectionModel().clearSelection();
		componentBox.getItems().remove(selected);
		if(selected != null) {
			try {
				IComponent component = (IComponent) Class.forName(myLocs.getString(selected)).getConstructor().newInstance();
				myEntity.addComponent(component);
				addObject(component);
			} catch (Exception e) {
				Utilities.showError(myResources.getString("error"), myResources.getString("addCompError"));
			}
		}
	}


	@Override
	public void updateEditor() {
		populateLayout();
	}

	private void save() {	
		myEntity.setName(name.getText());
		myEntity.getAllComponents().stream().forEach(e->removeBinding(e));
		entityList.remove(myEntity);
		entityList.add(myEntity);
		editorPane.getChildren().clear();
		editorPane.getChildren().add((saveMessage(myResources.getString("saveMessage"))));
	}

	private void removeBinding(IComponent e) {
		e.removeBindings();
	}


}
