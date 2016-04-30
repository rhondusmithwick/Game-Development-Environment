package view.editor.environmenteditor;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

import api.IEntity;
import api.IView;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import view.editor.EditorFactory;
import view.editor.entityeditor.EditorEntity;
import view.enums.GUISize;
import view.utilities.ButtonFactory;
import view.utilities.ContextMenuFactory;
import view.utilities.EntityCopier;
import view.utilities.PopUp;
import voogasalad.util.reflection.Reflection;

public class EnvironmentUtilites {
	
	private EditorEnvironment myEditor;
	private ResourceBundle myResources;
	private String myLanguage;
	private IView view;
	private ObservableList<IEntity> masterEntityList;
	private VBox environmentEntityButtonsBox;
	
	EnvironmentUtilites (IView view, VBox box, ObservableList<IEntity> masterList, EditorEnvironment editor, String language){
		myLanguage = language;
		myEditor = editor;
		this.view = view;
		masterEntityList = masterList;
		environmentEntityButtonsBox = box;
		myResources = ResourceBundle.getBundle(language);
	}
	
	public void populateVbox(VBox vbox, Collection<IEntity> collection, String methodName) {
		vbox.getChildren().clear();
		for (IEntity entity : collection) {
			Button button = (Button) Reflection.callMethod(this, methodName, entity);
			(button).setMaxWidth(Double.MAX_VALUE);
			vbox.getChildren().add(button);
		}
	}

	public Button createAddEntityButton(IEntity entity) {
		return ButtonFactory.makeButton((entity).getName(), e -> myEditor.addToSystem(EntityCopier.copyEntity(entity)));
	}

	public Button createEntityButton(IEntity entity) {
		Button entityInButton = new Button(entity.getName());
		entityInButton.setMaxWidth(Double.MAX_VALUE);
		entityInButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				MouseButton button = event.getButton();
				if (button == MouseButton.PRIMARY) {
					entityLeftClicked(entity);
				} else if (button == MouseButton.SECONDARY) {
					entityRightClicked(entity, entityInButton, event);
				}
			}
		});
		return entityInButton;
	}
	
	private void entityLeftClicked(IEntity entity) {
		ObservableList<IEntity> entityList = FXCollections.observableArrayList();
		EditorEntity entityEditor = (EditorEntity) new EditorFactory().createEditor(EditorEntity.class.getName(),
				myLanguage, entity, entityList);
		entityEditor.populateLayout();
		entityList.addListener((ListChangeListener<IEntity>) c -> {myEditor.updateEditor();});
		PopUp myPopUp = new PopUp(GUISize.ENTITY_EDITOR_WIDTH.getSize(), GUISize.ENTITY_EDITOR_HEIGHT.getSize());
		myPopUp.show(entityEditor.getPane());
	}

	private void entityRightClicked(IEntity entity, Button entityButton, MouseEvent event) {
		entityButton.setContextMenu(ContextMenuFactory.createContextMenu(makeMenuMap(entity, entityButton, event)));
	}
	
	public Map<String,EventHandler<ActionEvent>> makeMenuMap(IEntity entity, Button entityButton, MouseEvent event) {
		Map<String, EventHandler<ActionEvent>> menuMap = new LinkedHashMap<String, EventHandler<ActionEvent>>();
		menuMap.put(myResources.getString("remove"), e -> removeFromDisplay(entity, entityButton));
		menuMap.put(myResources.getString("sendBack"), e -> sendToBack(entity));
		menuMap.put(myResources.getString("sendFront"), e -> sendToFront(entity));
		menuMap.put(myResources.getString("sendBackOne"), e ->sendBackward(entity));
		menuMap.put(myResources.getString("sendForwardOne"), e -> sendForward(entity));
		menuMap.put(myResources.getString("saveAsMasterTemplate"), e -> saveToMasterList(entity));
		menuMap.put(myResources.getString("toggleHighlight"), e -> highlight(entity));
		return menuMap;
	}

	private void highlight(IEntity entity) {
		view.toggleHighlight(entity);
	}

	public void saveToMasterList(IEntity entity) {
		masterEntityList.add(entity);
	}

	public void sendToFront(IEntity e) {
		if (view.getEntitySystem().removeEntity(e.getID()) != null) {
			view.getEntitySystem().addEntity(e);
		}
		populateVbox(environmentEntityButtonsBox, view.getLevel().getAllEntities(), "createEntityButton");
	}

	public void sendToBack(IEntity e) {
		if (view.getEntitySystem().removeEntity(e.getID()) != null) {
			view.getEntitySystem().getAllEntities().add(0, e);
		}
		populateVbox(environmentEntityButtonsBox, view.getLevel().getAllEntities(), "createEntityButton");
	}

	public void sendForward(IEntity e) {
		int index = view.getEntitySystem().getAllEntities().indexOf(e) + 1;
		if (view.getEntitySystem().removeEntity(e.getID()) != null) {
			if (index < view.getEntitySystem().getAllEntities().size()) {
				view.getEntitySystem().getAllEntities().add(index, e);
			} else {
				view.getEntitySystem().getAllEntities().add(e);
			}
		}
		populateVbox(environmentEntityButtonsBox, view.getLevel().getAllEntities(), "createEntityButton");
	}
	
	public void removeFromDisplay(IEntity entity, Button entityButton) {
		view.getEntitySystem().removeEntity(entity.getID());
		environmentEntityButtonsBox.getChildren().remove(entityButton);
	}
	
	public void sendBackward(IEntity e) {
		int index = view.getEntitySystem().getAllEntities().indexOf(e) - 1;
		if (view.getEntitySystem().removeEntity(e.getID()) != null) {
			if (index >= 0) {
				view.getEntitySystem().getAllEntities().add(index, e);
			} else {
				view.getEntitySystem().getAllEntities().add(0, e);
			}
		}
		populateVbox(environmentEntityButtonsBox, view.getLevel().getAllEntities(), "createEntityButton");
	}
	

}
