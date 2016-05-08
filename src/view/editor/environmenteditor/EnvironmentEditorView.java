// This entire file is part of my masterpiece.
// Bruna Liborio

// My chosen masterpiece was a complete refactoring of the View class and Environment Editor class pair. The two are now connected
// through an explicit MVC triad, with methods from both scattered across the view, model, and controller. This view class contains most of
// the original and refactored code and thus was chosen as the representation of the refactor and the masterpiece. 

// The refactoring created a more conventional connection between the view and environment. It cut down on the lengths of these two classes
// by dividing the functionality and adding helper classer which previously did not exist. The new setup allows the buttons and canvas to 
// be updating whenever required rather than at every predetermined cycle. 

// The code demonstrates a use of MVC, an appreciation of alternate design, and the ability to take two classes that were originally very 
// unfocused and outsource methods to make them more focused to achieving and implementing a very specific goal. 

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
import model.component.hud.HUD;
import model.component.movement.Position;
import view.ConsoleTextArea;
import view.enums.DefaultEntities;
import view.enums.DefaultStrings;
import view.enums.GUISize;
import view.utilities.Alerts;
import view.utilities.TitledPaneFactory;
import view.utilities.UserInputBoxFactory;

/**
 * @author Bruna Liborio
 *
 */
public class EnvironmentEditorView {

	private ResourceBundle myResources;
	private EnvironmentButtonUtilites myButtonsUtility;
	private PopUpUtility myPopUpUtility;

	private BorderPane borderPane = new BorderPane();
	private ScrollPane scrollPane = new ScrollPane(borderPane);

	private VBox leftBox = new VBox();
	private VBox rightBox = new VBox();
	private VBox centerBox = new VBox();
	private VBox bottomBox = new VBox();
	private HBox buttonBox = new HBox();

	private VBox masterEntitiesBox = new VBox();
	private VBox levelEntitiesBox = new VBox();

	private TextField nameField = new TextField();
	private ConsoleTextArea console = new ConsoleTextArea();

	private Group myRoot = new Group();
	private DragAndResize DandR = new DragAndResize();

	public EnvironmentEditorView(String language, EnvironmentButtonUtilites myButtons, PopUpUtility myPopUps) {
		myResources = ResourceBundle.getBundle(language);
		myButtonsUtility = myButtons;
		myPopUpUtility = myPopUps;
	}

	/**
	 * Populates the pane with the necessary view elements.
	 * 
	 * @param name:
	 *            name of the level
	 * @param entities:
	 *            master list of available entities
	 */
	public void populateLayout(String name, List<IEntity> entities) {
		setLeftView(name, entities);
		setRightView();
		setCenterView();
		setBottomView();
		borderPane.setLeft(leftBox);
		borderPane.setRight(rightBox);
		borderPane.setCenter(centerBox);
		borderPane.setBottom(bottomBox);
	}

	/**
	 * Sets up the left view of the border pane
	 * 
	 * @param name:
	 *            name of the level
	 * @param entities:
	 *            master list of available entities
	 */
	private void setLeftView(String name, List<IEntity> entities) {
		leftBox.getChildren().add(setNameDisplay(name));
		leftBox.getChildren().add(setMasterEntitiesDisplay(entities));
	}

	/**
	 * Sets up the name display with the level name if there is one or a
	 * prompting message if there isn't
	 * 
	 * @param name:
	 *            name of the level
	 * @return nameField: text field with added text according to name
	 *         availability
	 */
	private TextField setNameDisplay(String name) {
		if (name.equals("")) {
			nameField.setText(myResources.getString("environmentName"));
		} else {
			nameField.setText(name);
		}
		return nameField;
	}

	/**
	 * Sets up the master entities display with functional buttons to add
	 * entities
	 * 
	 * @param entities:
	 *            master list of available entities
	 * @return ScrollPane: a new scroll pane with a titled box containing newly
	 *         created buttons based of the entities in the master list
	 */
	private ScrollPane setMasterEntitiesDisplay(List<IEntity> entities) {
		if (entities.isEmpty()) {
			loadDefaults(entities);
		}
		myButtonsUtility.populateVbox(masterEntitiesBox, entities,
				DefaultStrings.CREATE_ADD_ENTITY_BUTTON_METHOD.getDefault());
		return (new ScrollPane(
				TitledPaneFactory.makeTitledPane(myResources.getString("masterTemplates"), masterEntitiesBox, true)));
	}

	/**
	 * Loads some default entities that are predefined for the user's use
	 * 
	 * @param entities:
	 *            master list of available entities
	 */
	private void loadDefaults(List<IEntity> entities) {
		if (Alerts.showAlert(myResources.getString("addDefaults"), myResources.getString("addDefaultsQuestion"),
				myResources.getString("defaultsMessage"), AlertType.CONFIRMATION)) {
			entities.add(DefaultEntities.BACKGROUND.getDefault());
			entities.add(DefaultEntities.CHAR_1.getDefault());
			entities.add(DefaultEntities.CHAR_2.getDefault());
		}
	}

	/**
	 * Sets up the right view of the border pane
	 * 
	 */
	private void setRightView() {
		rightBox.getChildren().add(new ScrollPane(TitledPaneFactory
				.makeTitledPane(myResources.getString("environmentInstances"), levelEntitiesBox, true)));
	}

	/**
	 * Sets up the center view of the border pane
	 * 
	 */
	private void setCenterView() {
		myRoot.setManaged(false);
		centerBox.getChildren().add(
				createSubScene(myRoot, GUISize.THREE_FOURTHS_OF_SCREEN.getSize(), GUISize.HEIGHT_MINUS_TAB.getSize()));
	}

	/**
	 * Creates the new subscene and manipulates/sets up the root for future use
	 * 
	 * @param root:
	 *            main group to use for the subscene
	 * @param width:
	 *            width for the subscene
	 * @param height:
	 *            height for the subscene
	 * @return subScene: the newly created and configure subScene
	 */
	private SubScene createSubScene(Group root, double width, double height) {
		DandR.makeRootDragAndResize(myRoot);
		root.setManaged(false);
		SubScene subScene = new SubScene(root, width, height);
		subScene.setFill(Color.WHITE);
		subScene.setOnMouseClicked(myPopUpUtility::deletePopUps);
		return subScene;
	}

	/**
	 * Sets up the bottom view of the border pane
	 * 
	 */
	private void setBottomView() {
		myButtonsUtility.makeMainButtons(buttonBox);
		initConsole();
		bottomBox.getChildren().add(buttonBox);
		bottomBox.getChildren().add(console);
	}

	/**
	 * Initializes the console for user input by adding prompting text
	 * 
	 */
	private void initConsole() {
		console.setText(myResources.getString("enterCommands"));
		console.appendText("\n\n");
	}

	/**
	 * Adds a specified entity to the root of the subscene so it will appear and
	 * to the level entity box so that it can be manipulated
	 * 
	 * @param entity:
	 *            entity to add to the root and level entity box
	 */
	public void addToView(IEntity entity) {
		String newName = UserInputBoxFactory.userInputBox(myResources.getString("noName"),
				myResources.getString("addEntityName"));
		if (newName != null) {
			entity.setName(newName);
		}
		if (configureAndAdd(entity)) {
			levelEntitiesBox.getChildren().add(myButtonsUtility.createEntityButton(entity));
		}

	}

	/**
	 * Updates the root by clearing and redrawing the image view attached to the
	 * entities
	 * 
	 * @param passedEntities:
	 *            level entities to be updated by displaying the new image views
	 */
	public void update(List<IEntity> passedEntities) {
		myRoot.getChildren().clear();
		for (IEntity e : passedEntities) {
			configureAndAdd(e);
			if (e.hasComponent(HUD.class) && e.hasComponent(Position.class)){
				myRoot.getChildren().add(HUDupdateUtility.updateHUD(e, myResources));
			}
		}
	}

	/**
	 * Updates the buttons for the master entity box and the level entity box to
	 * reflect changes in the lists
	 * 
	 * @param master:
	 *            master list of available entities
	 * @param instances:
	 *            entity instances currently in the level
	 */
	public void updateView(List<IEntity> master, List<IEntity> instances) {
		myButtonsUtility.populateVbox(masterEntitiesBox, master,
				DefaultStrings.CREATE_ADD_ENTITY_BUTTON_METHOD.getDefault());
		myButtonsUtility.populateVbox(levelEntitiesBox, instances, DefaultStrings.CREATE_ENTITY_BUTTON.getDefault());
	}

	/**
	 * Returns the level name specified by the user; if a name was not given,
	 * the user is prompted to input one
	 * 
	 * @return returnName: level name
	 */
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

	/**
	 * Reconfigures the specified entity for view's use and adds the entity to
	 * the root of the subscene to be displayed
	 * 
	 * @param entity:
	 *            entity to be reconfigured for use and to be added to the root
	 * @return bool: boolean indicating if the entity was successfully
	 *         configured and added (true) or not (false)
	 */
	private boolean configureAndAdd(IEntity entity) {
		Boolean bool = true;
		try {
			if (!entity.hasComponent(Position.class) || !isSprite(entity)) {
				ComponentAdderUtility.addViewComponents(entity, myResources);
			}
			DandR.makeEntityDragAndResize(entity);
			ImageView imageView = EnvironmentHelperMethods.getUpdatedImageView(entity);
			imageView.setOnContextMenuRequested(event -> myPopUpUtility.showPopUp(entity, event));
			myRoot.getChildren().add(imageView);
		} catch (Exception e) {
			Alerts.showAlert(myResources.getString("error"), null, myResources.getString("unableToAddEntity"),
					AlertType.ERROR);
			bool = false;
		}
		return bool;
	}

	/**
	 * Clears the border pane and displays the given text
	 * 
	 * @param text:
	 *            message to be displayed upon clearing the pane
	 */
	public void clearPane(Text text) {
		borderPane.getChildren().clear();
		borderPane.setCenter(text);
	}

	/**
	 * Return the scrollPane containing all essential views; necessary for
	 * display
	 * 
	 * @return scrollPane: the scrollPane containing all the essential views
	 */
	public ScrollPane getPane() {
		return scrollPane;
	}

	/**
	 * Return the borderPane containing all essential views; necessary for
	 * display, but not scrollable
	 * 
	 * @return borderPane: the borderPane containing all the essential views
	 */
	public Pane getBorderPane() {
		return borderPane;
	}

	/**
	 * Prints the given string to the console
	 * 
	 * @param toPrint:
	 *            sting to print to the console
	 */
	public void printToConsole(String toPrint) {
		console.println("\n----------------");
		console.println(toPrint);
		console.println();
	}

	/**
	 * Returns the current text present in the console as a string
	 * 
	 * @return String: string with current console text
	 */
	public String getConsoleText() {
		return console.getText();
	}

}
