package view.editor.environmenteditor;

import static view.utilities.SpriteUtilities.isSprite;

import java.util.List;
import java.util.ResourceBundle;

import api.IEntity;
import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.component.movement.Position;
import view.ConsoleTextArea;
import view.DragAndResizeDynamic;
import view.enums.DefaultEntities;
import view.enums.DefaultStrings;
import view.enums.GUISize;
import view.utilities.Alerts;
import view.utilities.SpriteUtilities;
import view.utilities.TitledPaneFactory;
import view.utilities.UserInputBoxFactory;

public class EnvironmentEditorView {

	private ResourceBundle myResources;
	private EnvironmentButtonUtilites myButtonsClass;
	private PopUpUtility myPopUpUtility;
	
	private BorderPane environmentPane = new BorderPane();
	private ScrollPane scrollPane = new ScrollPane(environmentPane);
	
	private VBox leftPane = new VBox();
	private VBox rightPane = new VBox();
	private BorderPane centerPane = new BorderPane();
	private HBox buttonBox = new HBox();
	
	private VBox masterEntityButtonsBox = new VBox();
	private VBox environmentEntityButtonsBox = new VBox();
	
	private TextField nameField = new TextField();
	private ConsoleTextArea console = new ConsoleTextArea();
	
	private Group myRoot = new Group();
	private DragAndResizeDynamic DandR = new DragAndResizeDynamic();
	
	public EnvironmentEditorView(String language, EnvironmentButtonUtilites myButtons, PopUpUtility myPopUps) {
		myResources = ResourceBundle.getBundle(language);
		myButtonsClass = myButtons; 
		myPopUpUtility = myPopUps;
	}
	
	public void populateLayout(String name, List<IEntity> entities) {
		setLeftPane(name, entities);
		setRightPane();
		setMiddlePane();
		environmentPane.setRight(rightPane);
		environmentPane.setLeft(leftPane);
		environmentPane.setCenter(centerPane);
	}
	
	private void setMiddlePane() {
		myRoot.setManaged(false);
		centerPane.setCenter(createSubScene(myRoot, GUISize.THREE_FOURTHS_OF_SCREEN.getSize(),
				GUISize.HEIGHT_MINUS_TAB.getSize()));
		centerPane.setBottom(setUpInputPane());		
	}

	private SubScene createSubScene(Group root, double width, double height) {
		DandR.makeRootDragAndResize(myRoot);
		root.setManaged(false);
		SubScene subScene = new SubScene(root, width, height);
		subScene.setFill(Color.WHITE);
		subScene.setOnMouseClicked(myPopUpUtility::deletePopUps);
		return subScene;
	}

	private VBox setUpInputPane() {
		VBox box = new VBox();
		myButtonsClass.makeMainButtons(buttonBox);
		initConsole();
		box.getChildren().add(buttonBox);
		box.getChildren().add(console);
		return box;
	}

	private void setLeftPane(String name, List<IEntity> entities) {
		leftPane.getChildren().add(setNameDisplay(name));
		leftPane.getChildren().add(setEntityOptionsDisplay(entities));
	}

	private TextField setNameDisplay(String name) {
		if (name.equals("")) {
			nameField.setText(myResources.getString("environmentName"));
		} else {
			nameField.setText(name);
		}
		return nameField;
	}

	private ScrollPane setEntityOptionsDisplay(List<IEntity> entities) {
		if (entities.isEmpty()) {
			loadDefaults(entities);
		}
		myButtonsClass.populateVbox(masterEntityButtonsBox, entities,
				DefaultStrings.CREATE_ADD_ENTITY_BUTTON_METHOD.getDefault());
		return (new ScrollPane(TitledPaneFactory.makeTitledPane(myResources.getString("masterTemplates"),
				masterEntityButtonsBox, true)));
	}

	private void setRightPane() {
		rightPane.getChildren().add(new ScrollPane(TitledPaneFactory
				.makeTitledPane(myResources.getString("environmentInstances"), environmentEntityButtonsBox, true)));
	}

	private void loadDefaults(List<IEntity> entities) {
		if (Alerts.showAlert(myResources.getString("addDefaults"), myResources.getString("addDefaultsQuestion"),
				myResources.getString("defaultsMessage"), AlertType.CONFIRMATION)) {
			entities.add(DefaultEntities.BACKGROUND.getDefault());
			entities.add(DefaultEntities.CHAR_1.getDefault());
			entities.add(DefaultEntities.CHAR_2.getDefault());
		}
	}

	public void addToView(IEntity entity) { // add to view
		String newName = UserInputBoxFactory.userInputBox(myResources.getString("noName"),
				myResources.getString("addEntityName"));
		if (newName != null) {
			entity.setName(newName);
		}
		try {
			if (!entity.hasComponent(Position.class) || !isSprite(entity)) {
				ComponentAdderUtility.addViewComponents(entity, myResources);
			}
			DandR.makeEntityDragAndResize(entity);
			ImageView imageView = ViewFeatureMethods.getUpdatedImageView(entity);
			imageView.setOnContextMenuRequested(event -> myPopUpUtility.showPopUp(entity, event));
			myRoot.getChildren().add(imageView);
			environmentEntityButtonsBox.getChildren().add(myButtonsClass.createEntityButton(entity));
		} catch (Exception e) {
			Alerts.showAlert(myResources.getString("error"), null, myResources.getString("unableToAddEntity"),
					AlertType.ERROR);
		}
	}

	public void updateView(List<IEntity> master, List<IEntity> instances) {
		myButtonsClass.populateVbox(masterEntityButtonsBox, master,
				DefaultStrings.CREATE_ADD_ENTITY_BUTTON_METHOD.getDefault());
		myButtonsClass.populateVbox(environmentEntityButtonsBox, instances,
				DefaultStrings.CREATE_ENTITY_BUTTON.getDefault());
	}

	public String getName() {
		String returnName = null;
		if (nameField.getText().equals(myResources.getString("environmentName"))) {
			returnName = UserInputBoxFactory.userInputBox(myResources.getString("noName"),
					myResources.getString("noNameMessage"));
		} else {
			returnName = nameField.getText();
		}
		return returnName;
	}

	public void update(List<IEntity> passedEntities) {
		myRoot.getChildren().clear();
		List<IEntity> entities = passedEntities;
		for (IEntity e : entities) {
			if (SpriteUtilities.getSpriteComponent(e) != null && e.hasComponent(Position.class)) {
				myRoot.getChildren().addAll(ViewFeatureMethods.getCollisionShapes(e));
				DandR.makeEntityDragAndResize(e);
				ImageView imageView = ViewFeatureMethods.getUpdatedImageView(e);
				imageView.setOnContextMenuRequested(event -> myPopUpUtility.showPopUp(e, event));
				if (!myRoot.getChildren().contains(imageView)) {
					myRoot.getChildren().add(imageView);
				}
			}
		}
	}

	private void initConsole() {
		console.setText(myResources.getString("enterCommands"));
		console.appendText("\n\n");
	}

	public void resetPane(Text text) {
		environmentPane.getChildren().clear();
		environmentPane.setCenter(text);
	}
	
	public ScrollPane getPane() {
		return scrollPane;
	}

	public Pane getBorderPane() {
		return environmentPane;
	}
	
}
