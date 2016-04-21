package view.editor.gameeditor;

import java.util.ResourceBundle;

import api.IEditor;
import api.IEntity;
import api.ISerializable;
import enums.DefaultStrings;
import enums.GUISize;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.entity.Entity;
import view.Authoring;
import view.Utilities;
import view.editor.EditorEntity;
import view.editor.EditorFactory;
import view.editor.eventeditor.EditorEvent;

public class EventDisplay extends ObjectDisplay
{

	private VBox container;
	private Authoring authoringEnvironment;
	private String language;
	private ResourceBundle myResources;
	private ObservableList<IEntity> masterEntityList;
	private final EditorFactory editFact = new EditorFactory();
	private ObservableList<ISerializable> masterEnvironmentList;
	

	public EventDisplay(String language,ObservableList<IEntity> masterEntityList, Authoring authoringEnvironment)
	{
		super(language, authoringEnvironment,masterEntityList);
		this.language=language;
		this.masterEntityList = masterEntityList;
		this.authoringEnvironment = authoringEnvironment;
		this.language = language;
		this.myResources = ResourceBundle.getBundle(language);
	}	
	@Override
	protected void addNewObjects(VBox container) 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public Node makeNewObject() {
		return Utilities.makeButton(myResources.getString(DefaultStrings.EVENT_EDITOR_NAME.getDefault()), 
				e -> createEventEditor());//createEditor(EditorEvent.class, new Entity(), masterEnvironmentList));
		}
	
	private void createEventEditor()
	{
		EditorEvent editor = new EditorEvent(language,  masterEntityList);
		editor.populateLayout();
		authoringEnvironment.createTab(editor.getPane(), editor.getClass().getSimpleName(), true);
	}
}
