package view.editor.gameeditor;

import java.util.ResourceBundle;
import api.ISerializable;
import enums.DefaultStrings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.entity.Entity;
import view.Authoring;
import view.Utilities;
import view.editor.EditorEntity;

public class EntityDisplay extends ObjectDisplay{

	private ResourceBundle myResources;
	private ObservableList<ISerializable> masterEntList;
	
	public EntityDisplay(String language,ObservableList<ISerializable> masterEntList, Authoring authEnv){
		super(language, authEnv,masterEntList);
		this.masterEntList = masterEntList;
		this.myResources = ResourceBundle.getBundle(language);
		

	}
	
	
	@Override
	public ScrollPane init() {
		ScrollPane scroll = super.init();
		addListeners(masterEntList);
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
		return Utilities.makeButton(myResources.getString(DefaultStrings.ENTITY_EDITOR_NAME.getDefault()), 
				e->createEditor(EditorEntity.class, new Entity(), FXCollections.observableArrayList()));
	}
	
	
	
}
