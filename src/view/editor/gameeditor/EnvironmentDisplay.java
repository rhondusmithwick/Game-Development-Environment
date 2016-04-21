package view.editor.gameeditor;

import java.util.ResourceBundle;

import api.IEntity;
import api.IEntitySystem;
import api.ISerializable;
import enums.DefaultStrings;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.entity.EntitySystem;
import view.Authoring;
import view.Utilities;
import view.editor.EditorEnvironment;

public class EnvironmentDisplay extends ObjectDisplay{
	private ObservableList<IEntitySystem> masterEnvList;
	private ResourceBundle myResources;
	
	public EnvironmentDisplay(String language, ObservableList<IEntitySystem> masterEnvList, ObservableList<IEntity> masterEntList, Authoring authEnv){
		
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
	private void addEnvironmentToScroll(IEntitySystem eSystem, VBox container) {
		container.getChildren().add(Utilities.makeButton(eSystem.getName(), f->createEditor(EditorEnvironment.class, eSystem, (ObservableList<ISerializable>) ((ObservableList<?>) masterEnvList ))));
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public Node makeNewObject(){
		return Utilities.makeButton(myResources.getString(DefaultStrings.ENVIRONMENT_EDITOR_NAME.getDefault()), 
			e->createEditor(EditorEnvironment.class, new EntitySystem(), (ObservableList<ISerializable>) ((ObservableList<?>) masterEnvList )));
	}


}
