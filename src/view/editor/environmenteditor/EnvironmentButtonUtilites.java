package view.editor.environmenteditor;

import api.IEntity;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import view.editor.EditorFactory;
import view.editor.entityeditor.EditorEntity;
import view.enums.GUISize;
import view.utilities.ButtonFactory;
import view.utilities.ContextMenuFactory;
import view.utilities.EntityCopier;
import view.utilities.PopUp;
import view.utilities.ToMainMenu;
import voogasalad.util.reflection.Reflection;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Map.Entry;

/**
 * @author Bruna
 */
public class EnvironmentButtonUtilites {

	private final EditorEnvironment myControl;
	private final String myLanguage;
	private ResourceBundle myResources;

	EnvironmentButtonUtilites(EditorEnvironment control, String language) {
		myResources = ResourceBundle.getBundle(language);
		myLanguage = language;
		myControl = control;
	}

	public HBox makeMainButtons(HBox box) {
		Map<String, EventHandler<ActionEvent>> buttonMap = makeButtonMap();
		for (Entry<String, EventHandler<ActionEvent>> entry : buttonMap.entrySet()) {
			box.getChildren().add(ButtonFactory.makeButton(entry.getKey(), entry.getValue()));
		}
		return box;
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
		return ButtonFactory.makeButton((entity).getName(),
				e -> myControl.addEntityToBoth(EntityCopier.copyEntity(entity)));
	}

	public Button createEntityButton(IEntity entity) {
		Button entityInButton = new Button(entity.getName());
		entityInButton.setMaxWidth(Double.MAX_VALUE);
		entityInButton.setOnMouseClicked(event -> {
			MouseButton button = event.getButton();
			if (button == MouseButton.PRIMARY) {
				entityLeftClicked(entity);
			} else if (button == MouseButton.SECONDARY) {
				entityRightClicked(entity, entityInButton, event);
			}
		});
		entityInButton.setOnMouseEntered(e -> EnvironmentHelperMethods.highlight(entity));
		entityInButton.setOnMouseExited(e -> EnvironmentHelperMethods.dehighlight(entity));
		return entityInButton;
	}

	private void entityLeftClicked(IEntity entity) {
		ObservableList<IEntity> entityList = FXCollections.observableArrayList();
		EditorEntity entityEditor = (EditorEntity) new EditorFactory().createEditor(EditorEntity.class.getName(),
				myLanguage, entity, entityList);
		entityEditor.populateLayout();
		entityList.addListener((ListChangeListener<IEntity>) c -> myControl.updateEditor());
		PopUp myPopUp = new PopUp(GUISize.ENTITY_EDITOR_WIDTH.getSize(), GUISize.ENTITY_EDITOR_HEIGHT.getSize());
		myPopUp.show(entityEditor.getPane());
	}

	private void entityRightClicked(IEntity entity, Button entityButton, MouseEvent event) {
		entityButton.setContextMenu(ContextMenuFactory.createContextMenu(makeMenuMap(entity, entityButton, event)));
	}

	public Map<String, EventHandler<ActionEvent>> makeMenuMap(IEntity entity, Button entityButton, MouseEvent event) {
		Map<String, EventHandler<ActionEvent>> menuMap = new LinkedHashMap<>();
		menuMap.put(myResources.getString("remove"),
				e -> EnvironmentHelperMethods.removeFromDisplay(entity, myControl.getEntitySystem()));
		menuMap.put(myResources.getString("sendBack"),
				e -> EnvironmentHelperMethods.sendToBack(entity, myControl.getEntitySystem()));
		menuMap.put(myResources.getString("sendFront"),
				e -> EnvironmentHelperMethods.sendToFront(entity, myControl.getEntitySystem()));
		menuMap.put(myResources.getString("sendBackOne"),
				e -> EnvironmentHelperMethods.sendBackward(entity, myControl.getEntitySystem()));
		menuMap.put(myResources.getString("sendForwardOne"),
				e -> EnvironmentHelperMethods.sendForward(entity, myControl.getEntitySystem()));
		menuMap.put(myResources.getString("saveAsMasterTemplate"), e -> myControl.saveToMasterList(entity));
		menuMap.put(myResources.getString("toggleHighlight"), e -> EnvironmentHelperMethods.toggleHighlight(entity));
		return menuMap;
	}

	public Map<String, EventHandler<ActionEvent>> makeButtonMap() {
		Map<String, EventHandler<ActionEvent>> buttonMap = new LinkedHashMap<>();
		buttonMap.put(myResources.getString("evaluate"), e -> myControl.evaluateConsole());
		buttonMap.put(myResources.getString("loopManager"), e -> myControl.createLoopManager());
		buttonMap.put(myResources.getString("startGameLoop"), e -> myControl.play());
		buttonMap.put(myResources.getString("pauseGameLoop"), e -> myControl.pauseLoop());
		buttonMap.put(myResources.getString("mainMenu"), e -> ToMainMenu.toMainMenu(myControl.getBorderPane()));
		buttonMap.put(myResources.getString("saveEnvironment"), e -> myControl.saveEnvironment());
		return buttonMap;
	}
	
}
