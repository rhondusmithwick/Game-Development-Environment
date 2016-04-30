package view.editor.environmenteditor;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

import api.IEntity;
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

public class EnvironmentButtons {
	
	private EditorEnvironment myEditor;
	private ResourceBundle myResources;
	private String myLanguage;

	EnvironmentButtons (EditorEnvironment editor, String language){
		myLanguage = language;
		myEditor = editor;
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
		myEditor.toggleHighlight(entity);
		ObservableList<IEntity> entityList = FXCollections.observableArrayList();
		EditorEntity entityEditor = (EditorEntity) new EditorFactory().createEditor(EditorEntity.class.getName(),
				myLanguage, entity, entityList);
		entityEditor.populateLayout();
		entityList.addListener((ListChangeListener<IEntity>) c -> {myEditor.updateEditor();});
		PopUp myPopUp = new PopUp(GUISize.ENTITY_EDITOR_WIDTH.getSize(), GUISize.ENTITY_EDITOR_HEIGHT.getSize());
		myPopUp.show(entityEditor.getPane());
	}

	private void entityRightClicked(IEntity entity, Button entityButton, MouseEvent event) {
		Map<String, EventHandler<ActionEvent>> menuMap = new LinkedHashMap<String, EventHandler<ActionEvent>>();
		menuMap.put(myResources.getString("remove"), e -> myEditor.removeFromDisplay(entity, entityButton));
		menuMap.put(myResources.getString("sendBack"), e -> myEditor.sendToBack(entity));
		menuMap.put(myResources.getString("sendFront"), e -> myEditor.sendToFront(entity));
		menuMap.put(myResources.getString("sendBackOne"), e -> myEditor.sendBackward(entity));
		menuMap.put(myResources.getString("sendForwardOne"), e -> myEditor.sendForward(entity));
		menuMap.put(myResources.getString("saveAsMasterTemplate"), e -> myEditor.saveToMasterList(entity));
		entityButton.setContextMenu(ContextMenuFactory.createContextMenu(menuMap));
	}
	

}
