package view.editor.eventeditor;

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
import view.editor.Editor;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import model.entity.Entity;

/**
 * 
 * @author Alankmc
 *
 */

/*
 * TODO: Clean this shit up
 */
public class EditorEvent extends Editor
{
	private final VBox pane;
	private final ResourceBundle myResources;
	
	// Entity table contains...
	private TitledPane entityPane;
	// ... TitledPanes that in turn contain ...
	private final HashMap<String, Node> entitySubPanes;
	// ... TableViews specified here.
	private final HashMap<String, Node> entityCustomizables;
	
	private final ObservableList<ISerializable> entityList;
	private final HashMap<String, Button> actionButtons;
	
	private  ObservableList<String> actions;
	private TableView groovyTable;
	private Button chooseFileButton;
	private TableManager tableManager;
	
	public EditorEvent(String language, ISerializable toEdit, ObservableList<ISerializable> masterList, ObservableList<ISerializable> addToList)
	{
		pane = new VBox(GUISize.EVENT_EDITOR_PADDING.getSize());
		pane.setPadding(ViewInsets.GAME_EDIT.getInset());
		pane.setAlignment(Pos.TOP_LEFT);
		myResources = ResourceBundle.getBundle(language);
		this.entityList = masterList;
		
		entityPane = new TitledPane();
		entitySubPanes = new HashMap<String, Node>();
		entityCustomizables = new HashMap<String, Node>();
		
		actionButtons = new HashMap<String, Button>();
		tableManager = new TableManager(masterList);
		
	}
	
	private void makeEntityPanes()
	{
		for (String name: new String[]{myResources.getString("propertiesPane"), myResources.getString("actionsPane")})
		{
			entityCustomizables.put(name, Utilities.makeSingleColumnTable(name,GUISize.EVENT_EDITOR_TABLE_WIDTH.getSize()) );
			entitySubPanes.put(name, Utilities.makeTitledPane(name, entityCustomizables.get(name), true) );
		}
	}

	public void setActions(ObservableList<String> actions)
	{
		/*
		this.actions = actions;

		((TableView<String>)entityCustomizables.get(myResources.getString("actionsPane"))).setItems(actions);
		*/
	}

	private VBox makeGroovySide()
	{
		VBox container = new VBox(GUISize.EVENT_EDITOR_HBOX_PADDING.getSize());
		// Adding now the Groovy Table
		chooseFileButton = Utilities.makeButton("Choose file", e -> getFile());
		groovyTable = Utilities.makeSingleColumnTable("Groovy Scripts", GUISize.EVENT_EDITOR_TABLE_WIDTH.getSize()); // TODO: Temporary
		
		container.getChildren().addAll(chooseFileButton, groovyTable);
		return container;
	}

	private void getFile()
	{
		File groovyFile = null;
		
		groovyFile = Utilities.promptAndGetFile(new FileChooser.ExtensionFilter("groovy", "*.groovy"), "Select your groovy script!");
		if ( groovyFile != null )
		{
			
		}
	}
	
	private void makeBottomButtons()
	{
		HBox buttonSpace = new HBox();

		// Tried using reflection, but it failed :(
		actionButtons.put(myResources.getString("addAction"), Utilities.makeButton(myResources.getString("addAction"), e -> addAction()));
		actionButtons.put(myResources.getString("deleteAction"), Utilities.makeButton(myResources.getString("deleteAction"), e -> deleteAction()));
		
		for (Button button: actionButtons.values())
		{
			buttonSpace.getChildren().add(button);
		}
	
		pane.getChildren().add(buttonSpace);
	}

	private void makeTables()
	{
		
		HBox container = new HBox(GUISize.EVENT_EDITOR_HBOX_PADDING.getSize());
		VBox internalBox = new VBox();
	
		pane.getChildren().add(tableManager.getContainer());
	/*
		internalBox.setMinWidth(GUISize.EVENT_EDITOR_TABLE_WIDTH.getSize());
		
		makeEntityPanes();
		
		internalBox.getChildren().addAll(entitySubPanes.values());		
		entityPane = Utilities.makeTitledPane(myResources.getString("entityPane"), internalBox, false);
		
		container.getChildren().add(entityPane);
		*/
		container.getChildren().add(makeGroovySide());
		
		pane.getChildren().add(container);
		
		
	}
	
	public void populateLayout() 
	{
		makeTables();
		makeBottomButtons();
	}
	
	private void addAction()
	{
		
	}
	
	private void deleteAction()
	{
		
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