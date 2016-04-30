package view.editor.gameeditor;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import api.IEntity;
import api.ISerializable;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.entity.Entity;
import view.Authoring;
import view.editor.entityeditor.EditorEntity;
import view.enums.DefaultStrings;
import view.enums.GUISize;
import view.utilities.ButtonFactory;
import view.utilities.FileUtilities;

public class EntityDisplay extends ObjectDisplay{

	private ResourceBundle myResources, myTemplates;
	private ObservableList<IEntity> masterEntList;
	private final EntityFactory entFact = new EntityFactory();
	private String language;
	
	public EntityDisplay(String language,ObservableList<IEntity> masterEntList, Authoring authEnv){
		super(authEnv);
		this.language=language;
		this.masterEntList = masterEntList;
		this.myResources = ResourceBundle.getBundle(language);
		this.myTemplates = ResourceBundle.getBundle(language + DefaultStrings.TEMPLATE_LANG.getDefault());
		

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ScrollPane init() {
		ScrollPane scroll = super.init();
		addListeners( (ObservableList<ISerializable>) ((ObservableList<?>) masterEntList) );
		return scroll;
	}

	@Override
	protected void addNewObjects(VBox container) {
		masterEntList.stream().forEach(e-> addEntityToScroll(e, container));
	}

	private void addEntityToScroll(ISerializable entity, VBox container) {
		container.getChildren().add(ButtonFactory.makeButton(((Entity) entity).getName(), f->createEditor(EditorEntity.class.getName(), language,entity, masterEntList)));

	}
	
	@Override
	public Node makeNewObject(){
		HBox container = new HBox(GUISize.GAME_EDITOR_HBOX_PADDING.getSize());
		container.getChildren().add(ButtonFactory.makeButton(myResources.getString(DefaultStrings.ENTITY_EDITOR_NAME.getDefault()), 
				e->entityWithTemplate()));
		
		return container;
	}



	private void entityWithTemplate(){
		List<String> titles = new ArrayList<>();
		FileUtilities.getAllFromDirectory(DefaultStrings.TEMPLATE_DIREC_LOC.getDefault()).forEach(e-> titles.add(myTemplates.getString(e)));
		ChoiceDialog<String> templates = new ChoiceDialog<>(myResources.getString("None"), titles);
		templates.setTitle(myResources.getString("entityType"));
		templates.showAndWait();
		String choice = templates.getSelectedItem();
		IEntity newEntity = null;
		if(choice.equals(myResources.getString("None"))){
			newEntity = entFact.createEntity();
		}else{
			newEntity = entFact.createEntity(language, myTemplates.getString(choice));
		}
		createEditor(EditorEntity.class.getName(), language, newEntity, masterEntList);
	}
	
	
	
}
