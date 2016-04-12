package view.editor.gameeditor;

import java.util.ResourceBundle;

import api.IEditor;
import api.ISerializable;
import enums.DefaultStrings;
import enums.GUISize;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.entity.Entity;
import view.Authoring;
import view.Utilities;
import view.editor.EditorEntity;
import view.editor.EditorFactory;

public class EntityDisplay {

	private VBox container;
	private Authoring authEnv;
	private String language;
	private ResourceBundle myResources;
	private ObservableList<ISerializable> masterEntList;
	private final EditorFactory editFact = new EditorFactory();
	
	public EntityDisplay(String language,ObservableList<ISerializable> masterEntList, Authoring authEnv){
		this.masterEntList = masterEntList;
		this.authEnv = authEnv;
		this.language = language;
		this.myResources = ResourceBundle.getBundle(language);
		

	}
	
	private void addListeners() {
		masterEntList.addListener(new ListChangeListener<ISerializable>() {
			@Override
			public void onChanged(@SuppressWarnings("rawtypes") ListChangeListener.Change change) {
			
				updateEntities();
			}
		});
		
	}

	public ScrollPane init() {
		ScrollPane scroll = new ScrollPane();
		container = new VBox(GUISize.SCROLL_PAD.getSize());
		updateEntities();
		scroll.setContent(container);
		addListeners();
		return scroll;
	}

	private void updateEntities() {
		container.getChildren().remove(container.getChildren());
		masterEntList.stream().forEach(e-> addEntityToScroll(e, container));
	}

	private void addEntityToScroll(ISerializable entity, VBox container) {
		container.getChildren().add(Utilities.makeButton(((Entity) entity).getName(), f->createEditor(EditorEntity.class, entity, FXCollections.observableArrayList())));

	}
	
	private void createEditor(Class<?> editName, ISerializable toEdit, ObservableList<ISerializable> otherList) {
		IEditor editor = editFact.createEditor(editName, language, toEdit, masterEntList, otherList);
		editor.populateLayout();
		authEnv.createTab(editor.getPane(), editName.getSimpleName(), true);
	}
	
	public Button getButton(){
		return Utilities.makeButton(myResources.getString(DefaultStrings.ENTITY_EDITOR_NAME.getDefault()), 
				e->createEditor(EditorEntity.class, new Entity(), FXCollections.observableArrayList()));
	}
	
	
	
}
