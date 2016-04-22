package view.editor.gameeditor;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import api.IEntity;
import api.ISerializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.entity.Entity;
import view.Authoring;
import view.Utilities;
import view.editor.EditorEntity;
import view.enums.DefaultStrings;
import view.enums.GUISize;

public class EntityDisplay extends ObjectDisplay{

	private ResourceBundle myResources;
	private ObservableList<IEntity> masterEntList;
	private ComboBox<String> templateBox;
	private final EntityFactory entFact = new EntityFactory();
	private String language;
	
	public EntityDisplay(String language,ObservableList<IEntity> masterEntList, Authoring authEnv){
		super(language, authEnv,masterEntList);
		this.language=language;
		this.masterEntList = masterEntList;
		this.myResources = ResourceBundle.getBundle(language);
		

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
		container.getChildren().add(Utilities.makeButton(((Entity) entity).getName(), f->createEditor(EditorEntity.class, entity, FXCollections.observableArrayList())));

	}
	
	@Override
	public Node makeNewObject(){
		HBox container = new HBox(GUISize.GAME_EDITOR_HBOX_PADDING.getSize());
		List<String> titles = new ArrayList<>();
		Utilities.getAllFromDirectory(DefaultStrings.TEMPLATE_DIREC_LOC.getDefault()).forEach(e-> titles.add(myResources.getString(e)));
		templateBox = Utilities.makeComboBox(myResources.getString("entType"), titles, null);
		container.getChildren().add(templateBox);
		
		container.getChildren().add(Utilities.makeButton(myResources.getString(DefaultStrings.ENTITY_EDITOR_NAME.getDefault()), 
				e->entityWithTemplate()));
		
		return container;
	}



	private void entityWithTemplate(){
		String template = myResources.getString(templateBox.getSelectionModel().getSelectedItem());
		templateBox.getSelectionModel().clearSelection();
		if(template == null){
			return;
		}
		IEntity newEntity = entFact.createEntity(template, language);
		createEditor(EditorEntity.class, newEntity, FXCollections.observableArrayList());
	}
	
	
	
}
