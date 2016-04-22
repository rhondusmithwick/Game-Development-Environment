package view.editor;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;
import view.Utilities;
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
import enums.DefaultStrings;
import enums.GUISize;
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
	private int maxColumns = GUISize.GRIDPANE_COLUMNS.getSize();
	private ComboBox<String> componentBox;
	private List<String> myComponents;
	private HBox container;

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
		//System.out.println(language);
		getComponents(DefaultStrings.COMPONENT_LOC.getDefault());
		//System.out.println(myComponents);
	}

	private void getComponents(String loc) {
		myLocs = ResourceBundle.getBundle(loc);
		Enumeration<String> iter = myLocs.getKeys();
		while(iter.hasMoreElements()) {
			//String key = iter.nextElement();
			//System.out.println(key);
			myComponents.add(iter.nextElement());
		}
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
		Collection<IComponent> componentList = myEntity.getAllComponents();
		for (IComponent component: componentList){
			addObject(component);
		}
		saveButton = Utilities.makeButton(myResources.getString("saveEntity"), e-> addSerializable(myEntity));
		addButton = Utilities.makeButton(myResources.getString("addComponent"), e-> addComponent());
		editorPane.add(saveButton, maxColumns/2, row+1);
		//		editorPane.add(addButton, 3, row);
		componentBox = Utilities.makeComboBox(myResources.getString("chooseComponent"), myComponents, null);
		container.getChildren().addAll(Arrays.asList(componentBox, addButton));
		editorPane.add(container, maxColumns/2+1, row+1);
		//System.out.println(myComponents);
	}

	private void addObject(IComponent component) {
		GuiObjectFactory guiFactory = new GuiObjectFactory(myLanguage);
		for (SimpleObjectProperty<?> property: component.getProperties()){
			GuiObject object = guiFactory.createNewGuiObject(property.getName(), property, property.getValue());
			if (object!=null){
				editorPane.add((Node) object.getGuiNode(), column++, row);
				if(column > maxColumns) {
					row++;
					column = 0;
				}
				//System.out.println(property.getName());
				myComponents.remove(property.getName());
			}
		}
		//System.out.println(myComponents);
	}

	private void addComponent() {
		//		HBox container = new HBox(GUISize.GAME_EDITOR_HBOX_PADDING.getSize());
		//
		//		templateBox = Utilities.makeComboBox(myResources.getString("chooseComponent"), list, null);
		String selected = componentBox.getSelectionModel().getSelectedItem();
		componentBox.getSelectionModel().clearSelection();
		componentBox.getItems().remove(selected);
		if(selected != null) {
			try {
				//String a = ResourceBundle.getBundle("propertyFiles/componentLocations").getString(selected);
				IComponent component = (IComponent) Class.forName(myLocs.getString(selected)).getConstructor().newInstance();
				//System.out.println(a);
				myEntity.addComponent(component);
				addObject(component);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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
