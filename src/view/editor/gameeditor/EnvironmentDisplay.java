package view.editor.gameeditor;

import java.util.ResourceBundle;

import api.IEditor;
import api.ISerializable;
import enums.DefaultStrings;
import enums.GUISize;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.entity.EntitySystem;
import view.Authoring;
import view.Utilities;
import view.editor.EditorEnvironment;
import view.editor.EditorFactory;

public class EnvironmentDisplay {
	private VBox container;
	private ObservableList<ISerializable> masterEntList, masterEnvList;
	private final EditorFactory editFact = new EditorFactory();
	private Authoring authEnv;
	private String language;
	private ResourceBundle myResources;
	
	public EnvironmentDisplay(String language, ObservableList<ISerializable> masterEnvList, ObservableList<ISerializable> masterEntList, Authoring authEnv){
		this.masterEnvList = masterEnvList;
		this.masterEntList = masterEntList;
		this.authEnv = authEnv;
		this.language = language;
		this.myResources = ResourceBundle.getBundle(language);
		

	}
	
	
	private void addListeners() {
		masterEnvList.addListener(new ListChangeListener<ISerializable>() {
			@Override
			public void onChanged(@SuppressWarnings("rawtypes") ListChangeListener.Change change) {
				updateEnvironments();
			}
		});
		
	}


	public ScrollPane init() {
		ScrollPane scroll = new ScrollPane();
		container = new VBox(GUISize.SCROLL_PAD.getSize());
		updateEnvironments();
		scroll.setContent(container);
		addListeners();
		return scroll;
	}
	
	public void updateEnvironments() {
		container.getChildren().remove(container.getChildren());
		masterEnvList.stream().forEach(e-> addEnvironmentToScroll((EntitySystem) e, container));
	}

	private void addEnvironmentToScroll(EntitySystem eSystem, VBox container) {
		container.getChildren().add(Utilities.makeButton(eSystem.getName(), f->createEditor(EditorEnvironment.class, eSystem, masterEnvList )));
	}
	
	private void createEditor(Class<?> editName, ISerializable toEdit, ObservableList<ISerializable> otherList) {
		IEditor editor = editFact.createEditor(editName, language, toEdit, masterEntList, otherList);
		editor.populateLayout();
		authEnv.createTab(editor.getPane(), editName.getSimpleName(), true);
	}
	
	public Button envButton(){
		return Utilities.makeButton(myResources.getString(DefaultStrings.ENVIRONMENT_EDITOR_NAME.getDefault()), 
			e->createEditor(EditorEnvironment.class, new EntitySystem(), masterEnvList));
	}
}
