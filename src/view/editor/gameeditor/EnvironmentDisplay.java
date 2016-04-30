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
import view.editor.environmenteditor.EditorEnvironment;
import view.enums.DefaultStrings;
import view.utilities.ButtonFactory;

public class EnvironmentDisplay extends ObjectDisplay{
	private ObservableList<ILevel> masterEnvList;
	private ObservableList<IEntity> masterEntList;
	private ResourceBundle myResources;
	private String language;
	
	public EnvironmentDisplay(String language, ObservableList<ILevel> masterEnvList, ObservableList<IEntity> masterEntList, Authoring authEnv){
		
		super(authEnv);
		this.masterEntList=masterEntList;
		this.masterEnvList = masterEnvList;
		this.myResources = ResourceBundle.getBundle(language);
		this.language=language;
		

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

	private void addEnvironmentToScroll(ILevel eSystem, VBox container) {
		container.getChildren().add(ButtonFactory.makeButton(eSystem.getName(), f->createEditor(EditorEnvironment.class.getName(), language, eSystem, masterEntList, masterEnvList )));
	}
	

	@Override
	public Node makeNewObject(){
		return ButtonFactory.makeButton(myResources.getString(DefaultStrings.ENVIRONMENT_EDITOR_NAME.getDefault()), 
			e->createEditor(EditorEnvironment.class.getName(), language,  new Level(), masterEntList,  masterEnvList ));
	}


}
