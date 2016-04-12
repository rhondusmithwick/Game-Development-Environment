package view.editor;

import javafx.scene.layout.Pane;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import view.Authoring;
import view.Utilities;
import api.IEditor;
import api.ISerializable;
import enums.DefaultStrings;
import enums.FileExtensions;
import enums.GUISize;
import enums.ViewInsets;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * 
 * @author Alankmc
 *
 */

/*
 * TODO: Entity picker		--> Groundwork done
 * TODO: Table with collapsable panes	--> Done
 * TODO: Pane 1 - Retrieve Entity Properties and put in Table
 * TODO: Pane 2 - Editable Action Table
 * TODO: Load Groovy button
 * TODO: Editable + Resetable Groovy table
 * TODO: Write bindings to file
 * 
 */
public class EditorEvent extends Editor
{
	private final VBox pane;
	private final ResourceBundle myResources;
	private ComboBox<String> entityPicker;
	
	// Entity table contains TitledPanes...
	private TitledPane entityPane;
	// ... that in turn contain TableViews...
	private final HashMap<String, Node> entitySubPanes;
	// ... specified here.
	private final HashMap<String, Node> entityCustomizables;
	

	/*
	 *  Constructor is made based on new Editor Factory.
	 *  I tried to pass an event system, but it deprecated.
	 *  TODO: Change this, because it's temporary
	 */
	public EditorEvent(String language, ISerializable toEdit, ObservableList<ISerializable> masterList, ObservableList<ISerializable> addToList)
	{
		pane = new VBox(GUISize.EVENT_EDITOR_PADDING.getSize());
		pane.setPadding(ViewInsets.GAME_EDIT.getInset());
		pane.setAlignment(Pos.TOP_LEFT);
		myResources = ResourceBundle.getBundle(language);
		
		entityPicker = new ComboBox<String>();
		entityPane = new TitledPane();
		entitySubPanes = new HashMap<String, Node>();
		entityCustomizables = new HashMap<String, Node>();
	}
	
	private void makeEntityPanes()
	{
		for (String name: new String[]{myResources.getString("propertiesPane"), myResources.getString("actionsPane")})
		{
			entityCustomizables.put(name, Utilities.makeSingleColumnTable(name) );
			entitySubPanes.put(name, Utilities.makeTitledPane(name, entityCustomizables.get(name), true) );
		}
	}

	private void makeButtons()
	{

	}

	private void makeComboBox()
	{
		List<String> entityNames = new ArrayList<String>();
		
		entityPicker = Utilities.makeComboBox(myResources.getString("chooseEntity"), 
				entityNames, e -> selectedAnEntity());
		
		pane.getChildren().add(entityPicker);
	}
	
	private void makeTables()
	{
		HBox container = new HBox(GUISize.EVENT_EDITOR_HBOX_PADDING.getSize());
		VBox internalBox = new VBox();
		
		makeEntityPanes();
		
		internalBox.getChildren().addAll(entitySubPanes.values());		
		entityPane = Utilities.makeTitledPane(myResources.getString("entityPane"), internalBox, false);
		
		container.getChildren().add(entityPane);
		pane.getChildren().add(container);
	}
	
	private void selectedAnEntity()
	{
		
	}
	
	public void populateLayout() 
	{
		makeComboBox();
		makeButtons();
		makeTables();
	}

	@Override
	public void loadDefaults() {
		// TODO Auto-generated method stub

	}

	@Override
	public Pane getPane() 
	{
		return pane;
	}

	@Override
	public void addSerializable(ISerializable serialize) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateEditor() {
		// TODO Auto-generated method stub

	}

}