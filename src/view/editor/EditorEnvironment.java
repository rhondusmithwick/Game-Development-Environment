package view.editor;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import api.IEntity;
import api.IEntitySystem;
import api.ISerializable;
import enums.GUISize;
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
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import model.component.movement.Position;
import model.component.visual.ImagePath;
import model.entity.EntitySystem;
import view.DefaultsMaker;
import view.DragAndResize;
import view.Dragger;
import view.Utilities;

public class EditorEnvironment extends Editor {

	private BorderPane environmentPane;
	private IEntitySystem myEntitySystem;
	private SubScene gameScene;
	private Group gameRoot;
	private ResourceBundle myResources;
	private ObservableList<ISerializable> masterEntityList;
	private ObservableList<ISerializable> allEnvironmentsList;
	private VBox leftPane;
	private VBox rightPane;
	private VBox masterEntityButtons;
	private VBox environmentEntityButtons;
	private TextField nameField;

	public EditorEnvironment(String language, ISerializable toEdit, ObservableList<ISerializable> masterList,
			ObservableList<ISerializable> addToList) {
		myResources = ResourceBundle.getBundle(language);
		masterList.addListener((ListChangeListener<? super ISerializable>) c -> {
			this.updateDisplay(masterList);
		});
		masterEntityList = masterList;
		System.out.println("First Entity: " + ((IEntity) masterEntityList.get(0)).getID());
		myEntitySystem = (IEntitySystem) toEdit;
		allEnvironmentsList = addToList;
		addLayoutComponents();

		/*
		 * // TODO: don't hard code double MILLISECOND_DELAY = 10; double
		 * SECOND_DELAY = MILLISECOND_DELAY/1000; KeyFrame frame = new
		 * KeyFrame(Duration.millis(MILLISECOND_DELAY), e ->
		 * this.step(SECOND_DELAY)); Timeline animation = new Timeline();
		 * animation.setCycleCount(Timeline.INDEFINITE);
		 * animation.getKeyFrames().add(frame); animation.play();
		 */
	}

	/*
	 * private void step(double dt) { IPhysicsEngine p = new
	 * PhysicsEngine(null); p.update(getEntitySystem(), dt); for(IEntity e:
	 * getEntitySystem().getEntitiesWithComponent(Position.class)) { ImagePath
	 * imagePath = e.getComponent(ImagePath.class); ImageView imageView =
	 * imagePath.getImageView(); Position pos = e.getComponent(Position.class);
	 * imageView.setTranslateX(pos.getX()); imageView.setTranslateY(pos.getY());
	 * } }
	 */

	private void addLayoutComponents() {
		environmentPane = new BorderPane();
		setLeftPane();
		setRightPane();
		setGameScene();
	}

	private void setLeftPane() {
		leftPane = new VBox();
		leftPane.getChildren().add(setNameDisplay());
		leftPane.getChildren().add(setEntityOptionsDisplay());
	}

	private TextField setNameDisplay() {
		nameField = new TextField();
		if (myEntitySystem.getName().equals("")) {
			nameField.setText(myResources.getString("environmentName"));
		} else {
			nameField.setText(myEntitySystem.getName());
		}
		return nameField;
	}

	private ScrollPane setEntityOptionsDisplay() {
		masterEntityButtons = new VBox();
		if (masterEntityList.isEmpty()) {
			loadDefaults();
		}
		populateVbox(masterEntityButtons, masterEntityList);
		return (new ScrollPane(masterEntityButtons));
	}

	private void populateVbox(VBox vbox, ObservableList<ISerializable> entityPopulation) {
		vbox.getChildren().clear();
		for (ISerializable entity : entityPopulation) {
			Button addEntityButton = Utilities.makeButton(((IEntity) entity).getName(),
					e -> addToSystemAndScene(Utilities.copyEntity((IEntity) entity)));
			(addEntityButton).setMaxWidth(Double.MAX_VALUE);
			vbox.getChildren().add(addEntityButton);
		}
	}

	private void addToSystemAndScene(IEntity entity) {
		myEntitySystem.addEntity(makeResizable(entity));
		addToScene(entity);
	}

	@Override
	public void loadDefaults() {
		if (Utilities.showAlert(myResources.getString("addDefaults"), myResources.getString("addDefaultsQuestion"),
				myResources.getString("defaultsMessage"), AlertType.CONFIRMATION)) {
			masterEntityList.add(DefaultsMaker.loadBackgroundDefault());
			// entitiesToDisplay.add(DefaultsMaker.loadPlatformDefault(entitiesToDisplay));
			masterEntityList.add(DefaultsMaker.loadCharacter1Default());
			masterEntityList.add(DefaultsMaker.loadCharacter2Default());
		}
	}

	private void setRightPane() {
		rightPane = new VBox();
		rightPane.getChildren().add(setSaveButton());
		rightPane.getChildren().add(setEntitiesInEnvironmentDisplay());
	}

	private Button setSaveButton() {
		return Utilities.makeButton(myResources.getString("saveEnvironment"), e -> saveEnvironment());
	}

	private ScrollPane setEntitiesInEnvironmentDisplay() {
		environmentEntityButtons = new VBox();
		return (new ScrollPane(environmentEntityButtons));
	}

	private void setGameScene() {
		gameRoot = new Group();
		gameScene = new SubScene(gameRoot, (GUISize.TWO_THIRDS_OF_SCREEN.getSize()),
				GUISize.HEIGHT_MINUS_TAB.getSize());
		gameScene.setFill(Color.WHITE);
		for (IEntity entity : myEntitySystem.getAllEntities()) {
			addToScene(entity);
		}
	}

	private void addToScene(IEntity entity) {
		try {
			if (!entity.hasComponent(Position.class) || !entity.hasComponent(ImagePath.class)) {
				addComponents(entity);
			}
			//Rectangle rectangle = new Rectangle(200,200);
			//rectangle.setFill(Color.BLUE);
			//makeDraggable(rectangle);
			makeResizable(entity);
			environmentEntityButtons.getChildren().add(createEntityButton(entity));
			gameRoot.getChildren().add(createEntityImageView(entity));
			//gameRoot.getChildren().add(rectangle);
		} catch (Exception e) {
			Utilities.showAlert(myResources.getString("error"), null, myResources.getString("unableToAdd"),
					AlertType.ERROR);
		}
		System.out.println("entity to add to Scene" + entity.getID());
	}

	private ImageView createEntityImageView(IEntity entity) {
		Position pos = entity.getComponent(Position.class);
		ImageView entityView = entity.getComponent(ImagePath.class).getImageView();
		entityView.setTranslateX(pos.getX());
		entityView.setTranslateY(pos.getY());
		return entityView;
	}

	private Button createEntityButton(IEntity entity) {
		Button entityInButton = new Button(entity.getName());
		entityInButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				MouseButton button = event.getButton();
				if (button == MouseButton.PRIMARY) {
					entityLeftClicked(entity, entityInButton);
				} else if (button == MouseButton.SECONDARY) {
					entityRightClicked(entity, entityInButton, event);
				}
			}
		});
		return entityInButton;
	}

	private IEntity makeResizable(IEntity entity) {
		Position pos = entity.getComponent(Position.class);
		DragAndResize.makeResizable(entity.getComponent(ImagePath.class), pos);
		return entity;
	}
	
	//LOOK HERE FOR MAKING A SHAPE DRAGGABLE
	private Shape makeDraggable(Rectangle rectangle) {
		DragAndResize.makeResizable(rectangle);
		return rectangle;
	}
	
	private void entityRightClicked(IEntity entity, Button entityButton, MouseEvent event) {
		highlight(entity, true);
		Map<String, EventHandler<ActionEvent>> menuMap = new HashMap<String, EventHandler<ActionEvent>>();
		menuMap.put(myResources.getString("remove"), e -> removeFromDisplay(entity, entityButton));
		menuMap.put(myResources.getString("sendBack"), e -> sendToBack(entity));
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

	private IEntitySystem reorder(IEntity entity, IEntitySystem entitySystem) {
		entitySystem.removeEntity(entity.getID());
		Collection<IEntity> entitiesLeft = entitySystem.getAllEntities();
		List<IEntity> allEntitiesIn = new ArrayList<IEntity>();
		allEntitiesIn.addAll(entitiesLeft);
		entitySystem = new EntitySystem();
		entitySystem.addEntity(entity);
		entitySystem.addEntities(allEntitiesIn);
		return entitySystem;
	}

	private void entityLeftClicked(IEntity entity, Button entityInButton) {
		highlight(entity, false);
	}

	private void highlight(IEntity entity, boolean alwaysHighlight) {
		ImageView view = entity.getComponent(ImagePath.class).getImageView();
		if (view.getEffect() != null && !alwaysHighlight) {
			view.setEffect(null);
		} else {
			int depth = 70;
			DropShadow borderGlow = new DropShadow();
			borderGlow.setOffsetY(0f);
			borderGlow.setOffsetX(0f);
			borderGlow.setColor(Color.YELLOW);
			borderGlow.setWidth(depth);
			borderGlow.setHeight(depth);
			view.setEffect(borderGlow);
		}
	}

	private void updateDisplay(ObservableList<ISerializable> masterList) {
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
		entity.setSpec(Position.class, 1);
		Position pos = new Position();
		entity.addComponent(pos);
	}

	private void addImagePathComponent(IEntity entity) {
		File file = Utilities.promptAndGetFile(Utilities.getImageFilters(),
				myResources.getString("pickImagePathImage"));
		entity.setSpec(ImagePath.class, 1);
		entity.addComponent(new ImagePath(file.getPath()));
	}

	private void removeFromDisplay(IEntity entity, Button entityButton) {
		gameRoot.getChildren().remove(entity.getComponent(ImagePath.class).getImageView());
		myEntitySystem.removeEntity(entity.getID());
		environmentEntityButtons.getChildren().remove(entityButton);
	}

	public IEntitySystem getEntitySystem() {
		return myEntitySystem;
	}

	@Override
	public Pane getPane() {
		return environmentPane;
	}

	@Override
	public void addSerializable(ISerializable serialize) {
		myEntitySystem.addEntity((IEntity) serialize);
	}

	public boolean displayContains(IEntity checkEntity) {
		return masterEntityList.contains(checkEntity);
	}

	public boolean environmentContains(IEntity checkEntity) {
		return myEntitySystem.containsEntity(checkEntity);
	}

}
