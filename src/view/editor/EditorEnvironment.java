package view.editor;

<<<<<<< HEAD
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import api.IEntity;
import api.ILevel;
import api.ISystemManager;
import api.IView;
=======
import api.*;
>>>>>>> master
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.component.movement.Position;
import model.component.visual.Sprite;
import model.core.SystemManager;
import model.entity.Level;
import update.GameLoopManager;
import view.Utilities;
import view.View;
import view.enums.DefaultEntities;
import view.enums.GUISize;

import java.io.File;
import java.util.*;

//import view.DragAndResize;

public class EditorEnvironment extends Editor {

	// TODO: resources
	private static final int SINGLE = 1;
	private BorderPane environmentPane = new BorderPane();
	private ILevel myEntitySystem;
	private Group gameRoot = new Group();
	private ResourceBundle myResources;
	private ObservableList<IEntity> masterEntityList;
	private ObservableList<ILevel> allEnvironmentsList;
	private VBox leftPane = new VBox();
	private VBox rightPane = new VBox();
	private VBox masterEntityButtons = new VBox();
	private VBox environmentEntityButtons = new VBox();
	private TextField nameField = new TextField();
	private ScrollPane scrollPane = new ScrollPane(environmentPane);
	private ISystemManager game;
	private IView view;
	private SubScene gameScene;
	private GameLoopManager manager;

	public EditorEnvironment(String language, ILevel toEdit, ObservableList<IEntity> masterList,
			ObservableList<ILevel> addToList) {
		myResources = ResourceBundle.getBundle(language);
		masterList.addListener((ListChangeListener<IEntity>) c -> {
			this.updateDisplay(masterList);
		});
		masterEntityList = masterList;
		this.myEntitySystem = toEdit; // TODO: casting check

		game = new SystemManager(gameRoot, this.myEntitySystem);
		view = new View(game, gameRoot, (GUISize.TWO_THIRDS_OF_SCREEN.getSize()), GUISize.HEIGHT_MINUS_TAB.getSize(),
				scrollPane); // TODO: remove this last arg once we
										// figure out why keypresses aren't
										// working

		allEnvironmentsList = addToList;
		addLayoutComponents();
		manager = new GameLoopManager(language, game);
	}

	private void addLayoutComponents() {
		setLeftPane();
		setRightPane();
		setGameScene();
	}

	private void setLeftPane() {
		leftPane.getChildren().add(setNameDisplay());
		leftPane.getChildren().add(setEntityOptionsDisplay());
	}

	private TextField setNameDisplay() {
		if (myEntitySystem.getName().equals("")) {
			nameField.setText(myResources.getString("environmentName"));
		} else {
			nameField.setText(myEntitySystem.getName());
		}
		return nameField;
	}

	private ScrollPane setEntityOptionsDisplay() {
		if (masterEntityList.isEmpty()) {
			loadDefaults();
		}
		populateVbox(masterEntityButtons, masterEntityList);
		return (new ScrollPane(masterEntityButtons));
	}

	private void populateVbox(VBox vbox, ObservableList<IEntity> masterEntityList2) {
		vbox.getChildren().clear();
		for (IEntity entity : masterEntityList2) {
			Button addEntityButton = Utilities.makeButton(( entity).getName(),
					e -> addToSystemAndScene(Utilities.copyEntity( entity)));
			(addEntityButton).setMaxWidth(Double.MAX_VALUE);
			vbox.getChildren().add(addEntityButton);
		}
	}

	private void addToSystemAndScene(IEntity entity) {
		this.myEntitySystem.addEntities(entity);
		addToScene(entity);
	}

	private void loadDefaults() {
		if (Utilities.showAlert(myResources.getString("addDefaults"), myResources.getString("addDefaultsQuestion"),
				myResources.getString("defaultsMessage"), AlertType.CONFIRMATION)) {
			masterEntityList.add(DefaultEntities.BACKGROUND.getDefault());
			// entitiesToDisplay.add(DefaultsMaker.loadPlatformDefault(entitiesToDisplay));
			masterEntityList.add(DefaultEntities.CHAR_1.getDefault());
			masterEntityList.add(DefaultEntities.CHAR_2.getDefault());
		}
	}

	private void setRightPane() {
		rightPane.getChildren().add(setSaveButton());
		rightPane.getChildren().add(new ScrollPane(environmentEntityButtons));
		rightPane.getChildren().add(setLoopButton());
	}

	private Button setSaveButton() {
		return Utilities.makeButton(myResources.getString("saveEnvironment"), e -> saveEnvironment());
	}
	
	private Button setLoopButton() {
		return Utilities.makeButton(myResources.getString("loopManager"), e -> createLoopManager());
	}
	
	private void createLoopManager() {
		manager.show();
		
	}

	private void setGameScene() {
		gameScene = this.view.getSubScene();
		gameScene.setFill(Color.WHITE);
		for (IEntity entity : myEntitySystem.getAllEntities()) {
			addToScene(entity);
		}
	}

	private void addToScene(IEntity entity) {
		try {
			if (!entity.hasComponent(Position.class) || !entity.hasComponent(Sprite.class)) {
				addComponents(entity);
			}
			// TODO: rm
			// Rectangle rectangle = new Rectangle(200,200);
			// rectangle.setFill(Color.BLUE);
			// makeDraggable(rectangle);
			environmentEntityButtons.getChildren().add(createEntityButton(entity));
			// TODO: rm
			// gameRoot.getChildren().add(rectangle);
		} catch (Exception e) {
			Utilities.showAlert(myResources.getString("error"), null, myResources.getString("unableToAdd"),
					AlertType.ERROR);
		}
	}

	private Button createEntityButton(IEntity entity) {
		Button entityInButton = new Button(entity.getName());
		entityInButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				MouseButton button = event.getButton();
				if (button == MouseButton.PRIMARY) {
					// entityLeftClicked(entity, entityInButton);
					game.getEntitySystem().removeEntity(entity.getID());
				} else if (button == MouseButton.SECONDARY) {
					entityRightClicked(entity, entityInButton, event);
				}
			}
		});
		return entityInButton;
	}

	// TODO: LOOK HERE FOR MAKING A SHAPE DRAGGABLE
	// private Shape makeDraggable(Rectangle rectangle) {
	// DragAndResize.makeResizable(rectangle);
	// return rectangle;
	// }

	private void entityRightClicked(IEntity entity, Button entityButton, MouseEvent event) {
		// highlight(entity, true);
		Map<String, EventHandler<ActionEvent>> menuMap = new HashMap<String, EventHandler<ActionEvent>>();
		menuMap.put(myResources.getString("remove"), e -> removeFromDisplay(entity, entityButton));
		menuMap.put(myResources.getString("sendBack"), e -> sendToBack(entity));
		menuMap.put(myResources.getString("sendFront"), e -> sendToFront(entity));
		entityButton.setContextMenu(Utilities.createContextMenu(menuMap));
	}

	private void sendToBack(IEntity entity) {
		myEntitySystem = reorder(entity, myEntitySystem);
		environmentEntityButtons.getChildren().clear();
		gameRoot.getChildren().clear();
		for (IEntity addEntity : myEntitySystem.getAllEntities()) {
			addToScene(addEntity);
		}
	}

	private ILevel reorder(IEntity entity, ILevel entitySystem) {
		entitySystem.removeEntity(entity.getID());
		Collection<IEntity> entitiesLeft = entitySystem.getAllEntities();
		List<IEntity> allEntitiesIn = new ArrayList<IEntity>();
		allEntitiesIn.addAll(entitiesLeft);
		entitySystem = new Level();
		entitySystem.addEntity(entity);
		entitySystem.addEntities(allEntitiesIn);
		return entitySystem;
	}

	// private void sendToBack(IEntity e) {
	// e.getComponent(ImagePath.class).setZLevel(-1);
	// }

	private void sendToFront(IEntity e) {
		e.getComponent(Sprite.class).setZLevel(1);
	}

	private void updateDisplay(ObservableList<IEntity> masterList) {
		masterEntityList = masterList;
		populateVbox(masterEntityButtons, masterEntityList);
	}

	@Override
	public void updateEditor() {
		populateVbox(masterEntityButtons, masterEntityList);
	}

	@Override
	public void populateLayout() {
		environmentPane.setRight(rightPane);
		environmentPane.setLeft(leftPane);
		environmentPane.setCenter(gameScene);
	}

	private void saveEnvironment() {
		String name = getName();
		myEntitySystem.setName(name);
		allEnvironmentsList.remove(myEntitySystem);
		allEnvironmentsList.add(myEntitySystem);
		environmentPane.getChildren().clear();
		environmentPane.setCenter(saveMessage(myResources.getString("saveMessage")));
	}

	private String getName() {
		String returnName = null;
		if (nameField.getText().equals(myResources.getString("environmentName"))) {
			returnName = Utilities.userInputBox(myResources.getString("noName"),
					myResources.getString("noNameMessage"));
		} else {
			returnName = nameField.getText();
		}
		return returnName;
	}

	private void addComponents(IEntity entity) {
		if (Utilities.showAlert(myResources.getString("confirm"), myResources.getString("componentsRequired"),
				myResources.getString("addComponentQuestion"), AlertType.CONFIRMATION)) {
			addPositionComponent(entity);
			addImagePathComponent(entity);
		}
	}

	private void addPositionComponent(IEntity entity) {
		entity.setSpec(Position.class, SINGLE);
		Position pos = new Position();
		entity.addComponent(pos);
	}

	private void addImagePathComponent(IEntity entity) {
		File file = Utilities.promptAndGetFile(Utilities.getImageFilters(),
				myResources.getString("pickImagePathImage"));
		entity.setSpec(Sprite.class, SINGLE);
		entity.addComponent(new Sprite(file.getPath()));
	}

	private void removeFromDisplay(IEntity entity, Button entityButton) {
		gameRoot.getChildren().remove(entity.getComponent(Sprite.class).getImageView());
		myEntitySystem.removeEntity(entity.getID());
		environmentEntityButtons.getChildren().remove(entityButton);
	}

	public ILevel getEntitySystem() {
		return myEntitySystem;
	}

	@Override
	public ScrollPane getPane() {
		return scrollPane;
	}

	public boolean displayContains(IEntity checkEntity) {
		return masterEntityList.contains(checkEntity);
	}

	public boolean environmentContains(IEntity checkEntity) {
		return myEntitySystem.containsEntity(checkEntity);
	}

}
