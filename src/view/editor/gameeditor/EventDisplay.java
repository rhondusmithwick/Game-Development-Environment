package view.editor.gameeditor;

import java.util.ResourceBundle;

import api.IEditor;
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
	private ObservableList<ISerializable> masterEntityList;
	private ObservableList<String> actions;
	private final EditorFactory editFact = new EditorFactory();
	
	public EventDisplay(String language,ObservableList<ISerializable> masterEntityList, Authoring authoringEnvironment, ObservableList<String> actions)
	{
		super(language, authoringEnvironment,masterEntityList);
		this.language=language;
		this.masterEntityList = masterEntityList;
		this.authoringEnvironment = authoringEnvironment;
		this.language = language;
		this.myResources = ResourceBundle.getBundle(language);
		this.actions = actions;
	}

	@Override
	public void createEditor(Class<?> editName, ISerializable toEdit, ObservableList<ISerializable> otherList) 
	{
		IEditor editor = editFact.createEditor(editName, language, toEdit, masterEntityList, otherList);
		editor.populateLayout();
		((EditorEvent)editor).setActions(actions);
		authoringEnvironment.createTab(editor.getPane(), editName.getSimpleName(), true);
	}
	
	@Override
	protected void addNewObjects(VBox container) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Node makeNewObject() {
		return Utilities.makeButton(myResources.getString(DefaultStrings.EVENT_EDITOR_NAME.getDefault()), 
				e->createEditor(EditorEvent.class, new Entity(), FXCollections.observableArrayList()));
		}
}
