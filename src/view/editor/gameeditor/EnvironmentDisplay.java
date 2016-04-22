package view.editor.gameeditor;

import java.util.ResourceBundle;

import api.IEntity;
import api.ILevel;
import api.ISerializable;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.entity.Level;
import view.Authoring;
import view.Utilities;
import view.editor.EditorEnvironment;
import view.enums.DefaultStrings;

public class EnvironmentDisplay extends ObjectDisplay{
	private ObservableList<ILevel> masterEnvList;
	private ResourceBundle myResources;
	
	public EnvironmentDisplay(String language, ObservableList<ILevel> masterEnvList, ObservableList<IEntity> masterEntList, Authoring authEnv){
		
		super(language, authEnv, masterEntList);
		this.masterEnvList = masterEnvList;
		this.myResources = ResourceBundle.getBundle(language);
		

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ScrollPane init(){
		ScrollPane scroll = super.init();
		addListeners( (ObservableList<ISerializable>) ((ObservableList<?>) masterEnvList));
		return scroll;
	}

	@Override
	protected void addNewObjects(VBox container) {
		masterEnvList.stream().forEach(e-> addEnvironmentToScroll(e, container));
	}

	@SuppressWarnings("unchecked")
	private void addEnvironmentToScroll(ILevel eSystem, VBox container) {
		container.getChildren().add(Utilities.makeButton(eSystem.getName(), f->createEditor(EditorEnvironment.class, eSystem, (ObservableList<ISerializable>) ((ObservableList<?>) masterEnvList ))));
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public Node makeNewObject(){
		return Utilities.makeButton(myResources.getString(DefaultStrings.ENVIRONMENT_EDITOR_NAME.getDefault()), 
			e->createEditor(EditorEnvironment.class, new Level(), (ObservableList<ISerializable>) ((ObservableList<?>) masterEnvList )));
	}


}
