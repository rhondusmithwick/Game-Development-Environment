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
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;
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
	private GridPane editorPane;
	private IEntity myEntity;
	private String myLanguage;
	private ObservableList<ISerializable> entityList;
	private Button saveButton, addButton, removeButton;
	private ResourceBundle myResources, myLocs;
	private TextField name;
	private ScrollPane scrollPane;
	private int row = 0;
	private int column = 0;
	private ChoiceDialog<String> componentBox, removeBox;
	private List<String> myComponents;
	private VBox container;
	private GuiObjectFactory guiFactory;
	private final ComponentFactory componentFactory = new ComponentFactory();

	public EditorEntity(String language, ISerializable toEdit, ObservableList<ISerializable> addToList, ObservableList<ISerializable> emptyList) {
		editorPane = new GridPane();
		scrollPane = new ScrollPane(editorPane);
		myLanguage = language;
		myResources = ResourceBundle.getBundle(language);
		myEntity = (Entity) toEdit;
		entityList = addToList;
		editorPane.getStyleClass().add("grid-pane");
		
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
		container.getStyleClass().add("vbox");
		myComponents = new ArrayList<String>();
		getComponents();
		name = Utilities.makeTextArea(myResources.getString("enterName"));
		name.setText(myEntity.getName());
		editorPane.add(name, column++, row);
		Collection<IComponent> componentList = myEntity.getAllComponents();
		for (IComponent component: componentList){
			addObject(component);
		}
		saveButton = Utilities.makeButton(myResources.getString("saveEntity"), e -> save());
		addButton = Utilities.makeButton(myResources.getString("addComponent"), e -> addComponent());
		removeButton = Utilities.makeButton(myResources.getString("removeComp"), e->removeComponent());
		container.getChildren().addAll(Arrays.asList(addButton, removeButton, saveButton));
		editorPane.add(container, GUISize.HALF_COLUMNS.getSize() + GUISize.ONE.getSize(), row + GUISize.ONE.getSize());
	}

	private void addObject(IComponent component) {
		guiFactory = new GuiObjectFactory(myLanguage);
		component.getProperties().stream().forEach(e -> addVisualObject(e));
	}

	private void addVisualObject(SimpleObjectProperty<?> property) {
		GuiObject object = guiFactory.createNewGuiObject(property.getName(), property, property.getValue());
		if (object != null){
			editorPane.add((Node) object.getGuiNode(), column++, row);
			if(column > GUISize.GRIDPANE_COLUMNS.getSize()) {
				row++;
				column = 0;
			}
			myComponents.remove(property.getName());
		}
	}

	private void addComponent() {
		componentBox = new ChoiceDialog<>(myResources.getString("chooseComponent"), myComponents);
		componentBox.showAndWait();
		String chosen = componentBox.getSelectedItem();
		if(chosen.equals(myResources.getString("chooseComponent"))||chosen==null){
			return;
		}
		String componentName = myResources.getString(chosen);

		try {
			IComponent component = componentFactory.getComponent(Class.forName(myLocs.getString(componentName)), myEntity);
			myEntity.forceAddComponent(component, true);
			addObject(component);
		} catch (Exception e) {
			e.printStackTrace();
			Utilities.showError(myResources.getString("error"), myResources.getString("addCompError"));
		}
		adjustMenu();
	}
	
	@SuppressWarnings("unchecked")
	private void removeComponent() {
		List<IComponent> components = (List<IComponent>) myEntity.getAllComponents();
		List<String> componentNames = new ArrayList<>();
		components.forEach(component->componentNames.add(myResources.getString(component.getClass().getSimpleName())));
		removeBox = new ChoiceDialog<String>(myResources.getString("chooseRem"), componentNames);
		removeBox.showAndWait();
		String chosen = removeBox.getSelectedItem();
		if(chosen.equals(myResources.getString("chooseRem"))||chosen==null){
			return;
		}
		String componentName = myResources.getString(chosen);
		try {
			myEntity.removeComponent((Class<? extends IComponent>) Class.forName(myLocs.getString(componentName)));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		editorPane.getChildren().clear();
		populateLayout();
		
	}



	private void adjustMenu() {
		if(row == GridPane.getRowIndex(container)) {
			editorPane.getChildren().remove(container);
			editorPane.add(container, GUISize.HALF_COLUMNS.getSize() + GUISize.ONE.getSize(), row + GUISize.ONE.getSize());
		}
	}

	@Override
	public void updateEditor() {
		populateLayout();
	}

	private void save() {	
		myEntity.setName(name.getText());
		myEntity.getAllComponents().stream().forEach(e -> removeBinding(e));
		entityList.remove(myEntity);
		entityList.add(myEntity);
		editorPane.getChildren().clear();
		editorPane.getChildren().add((saveMessage(myResources.getString("saveMessage"))));
	}

	private void removeBinding(IComponent e) {
		e.removeBindings();
	}
}
