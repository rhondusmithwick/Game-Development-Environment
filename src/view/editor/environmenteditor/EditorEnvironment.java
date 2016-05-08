// This entire file is a subpart of my masterpiece just to show the full MVC triad.

package view.editor.environmenteditor;

import api.IEntity;
import api.IEntitySystem;
import api.ILevel;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import view.editor.Editor;
import view.enums.NecessaryIntegers;

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

	void saveEnvironment() {
		environmentModel.getLevel().getEntitySystem().setName(environmentView.getName());
		allLevelsList.remove(environmentModel.getLevel());
		allLevelsList.add(environmentModel.getLevel());
		environmentView.clearPane(saveMessage(myResources.getString("saveMessage")));
	}

	public void saveToMasterList(IEntity entity) {
		masterEntityList.add(entity);
	}

	void evaluateConsole() {
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

	public Pane getBorderPane() {
		return environmentView.getBorderPane();
	}

	public void createLoopManager() {
		environmentModel.createLoopManager();
	}

	public void play() {
		environmentModel.play();
	}

	public void pauseLoop() {
		environmentModel.pauseLoop();
	}

}
