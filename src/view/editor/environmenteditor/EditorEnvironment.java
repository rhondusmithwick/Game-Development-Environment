// This entire file is a subpart of my masterpiece just to show the full MVC triad.

package view.editor.environmenteditor;

import api.IEntity;
import api.IEntitySystem;
import api.ILevel;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import view.editor.Editor;
import view.enums.NecessaryIntegers;
import view.utilities.ToMainMenu;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Bruna Liborio
 */
public class EditorEnvironment extends Editor {

	private final ResourceBundle myResources;
	private final ObservableList<IEntity> masterEntityList;
	private final ObservableList<ILevel> allLevelsList;
	private EnvironmentEditorView environmentView;
	private EnvironmentEditorModel environmentModel;

	public EditorEnvironment(String language, ILevel toEdit, ObservableList<IEntity> masterList,
			ObservableList<ILevel> addToList, Scene myScene) {
		myResources = ResourceBundle.getBundle(language);
		masterList.addListener((ListChangeListener<IEntity>) c -> this.updateEditor());
		masterEntityList = masterList;
		allLevelsList = addToList;
		EnvironmentButtonUtilites buttonsClass = new EnvironmentButtonUtilites(this, language);
		PopUpUtility popUpClass = new PopUpUtility(this, language);
		environmentView = new EnvironmentEditorView(language, buttonsClass, popUpClass);
		environmentModel = new EnvironmentEditorModel(language, myScene, toEdit);
		EnvironmentHelperMethods.startTimeline(NecessaryIntegers.MILLISECOND_DELAY.getNumber(),
				e -> step(NecessaryIntegers.SECOND_DELAY.getNumber()));
	}

	private void step(double dt) {
		environmentModel.step(dt);
		environmentView.update(environmentModel.getLevel().getAllEntities());
	}

	@Override
	public void updateEditor() {
		environmentView.updateView(masterEntityList, environmentModel.getLevel().getAllEntities());

	}

	@Override
	public void populateLayout() {
		environmentView.populateLayout(environmentModel.getLevel().getName(),
				environmentModel.getLevel().getAllEntities());
		environmentModel.getLevel().getAllEntities().stream().forEach(environmentView::addToView);
	}

	@Override
	public ScrollPane getPane() {
		return environmentView.getPane();
	}

	private void saveEnvironment() {
		environmentModel.getLevel().getEntitySystem().setName(environmentView.getName());
		allLevelsList.remove(environmentModel.getLevel());
		allLevelsList.add(environmentModel.getLevel());
		environmentView.clearPane(saveMessage(myResources.getString("saveMessage")));
	}

	private void saveToMasterList(IEntity entity) {
		masterEntityList.add(entity);
	}

	private void evaluateConsole() {
		String toPrint = environmentModel.evaluate(environmentView.getConsoleText());
		environmentView.printToConsole(toPrint);
	}

	public void addEntityToBoth(IEntity copyEntity) {
		environmentModel.addEntity(copyEntity);
		environmentView.addToView(copyEntity);
	}

	public IEntitySystem getEntitySystem() {
		return environmentModel.getEntitySystem();
	}

	public Map<String, EventHandler<ActionEvent>> makeButtonMap() {
		Map<String, EventHandler<ActionEvent>> buttonMap = new LinkedHashMap<>();
		buttonMap.put(myResources.getString("evaluate"), e -> this.evaluateConsole());
		buttonMap.put(myResources.getString("loopManager"), e -> environmentModel.createLoopManager());
		buttonMap.put(myResources.getString("startGameLoop"), e -> environmentModel.play());
		buttonMap.put(myResources.getString("pauseGameLoop"), e -> environmentModel.pauseLoop());
		buttonMap.put(myResources.getString("mainMenu"), e -> ToMainMenu.toMainMenu(environmentView.getBorderPane()));
		buttonMap.put(myResources.getString("saveEnvironment"), e -> saveEnvironment());
		return buttonMap;
	}

	public Map<String, EventHandler<ActionEvent>> makeMenuMap(IEntity entity, Button entityButton, MouseEvent event) {
		Map<String, EventHandler<ActionEvent>> menuMap = new LinkedHashMap<>();
		menuMap.put(myResources.getString("remove"),
				e -> EnvironmentHelperMethods.removeFromDisplay(entity, getEntitySystem()));
		menuMap.put(myResources.getString("sendBack"),
				e -> EnvironmentHelperMethods.sendToBack(entity, getEntitySystem()));
		menuMap.put(myResources.getString("sendFront"),
				e -> EnvironmentHelperMethods.sendToFront(entity, getEntitySystem()));
		menuMap.put(myResources.getString("sendBackOne"),
				e -> EnvironmentHelperMethods.sendBackward(entity, getEntitySystem()));
		menuMap.put(myResources.getString("sendForwardOne"),
				e -> EnvironmentHelperMethods.sendForward(entity, getEntitySystem()));
		menuMap.put(myResources.getString("saveAsMasterTemplate"), e -> this.saveToMasterList(entity));
		menuMap.put(myResources.getString("toggleHighlight"), e -> EnvironmentHelperMethods.toggleHighlight(entity));
		return menuMap;
	}

}
