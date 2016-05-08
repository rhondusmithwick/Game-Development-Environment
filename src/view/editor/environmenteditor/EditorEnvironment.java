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
import view.utilities.ToMainMenu;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Bruna
 */
public class EditorEnvironment extends Editor {

	private final double MILLISECOND_DELAY = 10;
    private final double SECOND_DELAY = MILLISECOND_DELAY / 1000;
    private final ResourceBundle myResources;
    private final ObservableList<IEntity> masterEntityList;
    private final ObservableList<ILevel> allEnvironmentsList;
	private ILevel myLevel;
	private EnvironmentEditorView environmentView;
	private EnvironmentEditorModel environmentModel;
    
    public EditorEnvironment (String language, ILevel toEdit, ObservableList<IEntity> masterList,
                              ObservableList<ILevel> addToList, Scene myScene) {
        myResources = ResourceBundle.getBundle(language);
        
        masterList.addListener((ListChangeListener<IEntity>) c -> this.updateEditor());
       
        masterEntityList = masterList;
        myLevel = toEdit;
        allEnvironmentsList = addToList;

        EnvironmentButtonUtilites buttonsClass = new EnvironmentButtonUtilites(this, language); 
		PopUpUtility popUpUtility = new PopUpUtility(this, language);
        
        environmentView = new EnvironmentEditorView(language, buttonsClass, popUpUtility);
        environmentModel = new EnvironmentEditorModel(language, myScene, toEdit);
        ViewFeatureMethods.startTimeline(MILLISECOND_DELAY, e -> step(SECOND_DELAY));
    }

    private void step(double dt) {
		environmentModel.step(dt);
		environmentView.update(environmentModel.getLevel().getAllEntities());
	}

	@Override
    public void updateEditor () {
    		environmentView.updateView(masterEntityList, myLevel.getAllEntities());
    	
    }
    
    public void update(){
    		environmentView.update(myLevel.getAllEntities());
    }

    @Override
    public void populateLayout () {
    		environmentView.populateLayout(myLevel.getName(), myLevel.getAllEntities());
    		myLevel.getAllEntities().stream().forEach(environmentView::addToView);

    }

    private void saveEnvironment () {
    		myLevel.getEntitySystem().setName(environmentView.getName());
        allEnvironmentsList.remove(myLevel);
        allEnvironmentsList.add(myLevel);
        environmentView.resetPane(saveMessage(myResources.getString("saveMessage")));

    }

    private void saveToMasterList (IEntity entity) {
        masterEntityList.add(entity);
    }
    
    public Map<String, EventHandler<ActionEvent>> makeButtonMap () {
        Map<String, EventHandler<ActionEvent>> buttonMap = new LinkedHashMap<>();
		buttonMap.put(myResources.getString("evaluate"), e -> this.evaluateConsole());
		buttonMap.put(myResources.getString("loopManager"), e -> environmentModel.createLoopManager());
		buttonMap.put(myResources.getString("startGameLoop"), e -> environmentModel.play());
		buttonMap.put(myResources.getString("pauseGameLoop"), e -> environmentModel.pauseLoop());
		buttonMap.put(myResources.getString("mainMenu"), e -> ToMainMenu.toMainMenu(environmentView.getBorderPane()));
		buttonMap.put(myResources.getString("saveEnvironment"), e -> saveEnvironment());
        return buttonMap;
    }
    
    private Object evaluateConsole() {
    		//	console.println("\n----------------");
    		// console.println();
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, EventHandler<ActionEvent>> makeMenuMap (IEntity entity, Button entityButton, MouseEvent event) {
        Map<String, EventHandler<ActionEvent>> menuMap = new LinkedHashMap<>();
        menuMap.put(myResources.getString("remove"), e -> ViewFeatureMethods.removeFromDisplay(entity, myLevel.getEntitySystem()));
        menuMap.put(myResources.getString("sendBack"), e -> ViewFeatureMethods.sendToBack(entity, myLevel.getEntitySystem()));
        menuMap.put(myResources.getString("sendFront"), e -> ViewFeatureMethods.sendToFront(entity, myLevel.getEntitySystem()));
        menuMap.put(myResources.getString("sendBackOne"), e -> ViewFeatureMethods.sendBackward(entity, myLevel.getEntitySystem()));
        menuMap.put(myResources.getString("sendForwardOne"), e -> ViewFeatureMethods.sendForward(entity, myLevel.getEntitySystem()));
        menuMap.put(myResources.getString("saveAsMasterTemplate"), e -> this.saveToMasterList(entity));
        menuMap.put(myResources.getString("toggleHighlight"), e -> ViewFeatureMethods.toggleHighlight(entity));
        return menuMap;
    }

    @Override
    public ScrollPane getPane () {
        return environmentView.getPane();
    }

	public void addEntityToBoth(IEntity copyEntity) {
		environmentModel.addEntity(copyEntity);
		environmentView.addToView(copyEntity);
	}

	public IEntitySystem getEntitySystem() {
		return environmentModel.getEntitySystem();
	}

}
