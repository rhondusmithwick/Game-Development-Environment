package view;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import api.IComponent;
import api.IEntity;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TitledPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import model.component.movement.Position;
import model.component.visual.ImagePath;
import model.entity.Entity;

public class Utilities {

	/**
	 * This class has all static methods so that the methods can be accessed
	 * without the actual class being instantiated and so that class function
	 * can be accessed without this object being passed.
	 */

	/**
	 * Show an alert with a certain message and a given type.
	 * 
	 * @param String
	 *            title: title of alert, could be null
	 * @param String
	 *            header: header for the alert, could be null
	 * @param String
	 *            message: longer message further describing the alert, could be
	 *            null (though all three above shouldn't be null at the same
	 *            time or the alert is useless)
	 * @param AlertType
	 *            type: type of alert to display, i.e. ERROR, INFORMATION,
	 *            CONFIRMATION
	 * @return boolean result.get() == ButtonType.OK: returns true is the user
	 *         clicked the OK button, returns false otherwise
	 */

	public static boolean showAlert(String title, String header, String message, AlertType type) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(message);
		Optional<ButtonType> result = alert.showAndWait();
		return (result.get() == ButtonType.OK);
	}

	/**
	 * Show an error with a certain message.
	 * 
	 * @param error
	 *            message
	 */

	public static void showError(String title, String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.show();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static TableView<String> makeSingleColumnTable(String title, double width) {
		TableView<String> table = new TableView<String>();
		table.setPrefWidth(width);
		TableColumn column = new TableColumn(title);
		column.minWidthProperty().bind(table.prefWidthProperty());
		column.maxWidthProperty().bind(table.prefWidthProperty());
		table.getColumns().add(column);

		table.setEditable(true);

		return table;
	}

	/**
	 * Makes a button with a given name and event.
	 * 
	 * @param String
	 *            name: name of button
	 * @param EventHandler<ActionEvent>
	 *            handler: action/method to occur when button is pressed
	 * @return Button button: the button node tied to its name and event
	 * 
	 */

	public static Button makeButton(String name, EventHandler<ActionEvent> handler) {
		Button button = new Button();
		button.setText(name);
		button.setOnAction(handler);
		return button;
	}

	/**
	 * Creates and returns a string combo box with the following features:
	 * 
	 * @param String
	 *            prompt the name of the combo box
	 * @param List<String>
	 *            choices choices in the combo box
	 * @param EventHandler<ActionEvent>
	 *            event event/method to occur when the combo box is selected
	 * @return ComboBox<String> comboBox the combo box with the above features.
	 */

	public static ComboBox<String> makeComboBox(String prompt, List<String> choices, EventHandler<ActionEvent> event) {
		ComboBox<String> comboBox = new ComboBox<>();
		comboBox.getItems().addAll(choices);
		comboBox.setEditable(false);
		comboBox.setPromptText(prompt);
		comboBox.setOnAction(event);
		return comboBox;
	}

	/**
	 * Creates and returns a text area with the following features:
	 * 
	 * @param String
	 *            prompt writing in text area
	 * @return TextArea field the new text area with above features
	 */

	public static TextField makeTextArea(String prompt) {
		TextField field = new TextField();
		field.setPromptText(prompt);
		return field;
	}

	/**
	 * Prompts a file chooser box for the user to choose a file with ONE
	 * extension filter
	 * 
	 * @param ExtensionFilter
	 *            extension: file extension that the user can choose from, all
	 *            others are not allowed
	 * @param String
	 *            prompt: prompt for the file chooser box
	 * @return File: return file selected by the user Note: this method works
	 *         with promptAndGetFile(List<ExtensionFilter> filters, String
	 *         prompt), which allows the addition of multiple extension filters,
	 *         whereas this method is called with only one extension filter is
	 *         needed; pairing the methods reduces replicated code
	 */

	public static File promptAndGetFile(ExtensionFilter extension, String prompt) {
		List<ExtensionFilter> filters = new ArrayList<ExtensionFilter>();
		filters.add(extension);
		return promptAndGetFile(filters, prompt);
	}

	/**
	 * Prompts a file chooser box for the user to choose a file with MULTIPLE
	 * extension filters
	 * 
	 * @param List<ExtensionFilter>
	 *            filters: file extensions that the user can choose from, all
	 *            others are not allowed
	 * @param String
	 *            prompt: prompt for the file chooser box
	 * @return File file: return file selected by the user
	 */
	public static File promptAndGetFile(List<ExtensionFilter> filters, String prompt) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(prompt);
		fileChooser.getExtensionFilters().addAll(filters);
		File dir = new File("resources/");
		fileChooser.setInitialDirectory(dir);
		File file = fileChooser.showOpenDialog(new Stage());
		return file;
	}

	/**

	 * Directs file chooser box to the appropriate directory to use files from
	 * this project
	 * 
	 * @return File directory: local directory being returned
	 */

	@SuppressWarnings("unused")
	private static File getLocalDir() {
		ProtectionDomain pd = Utilities.class.getProtectionDomain();
		CodeSource cs = pd.getCodeSource();
		URL localDir = cs.getLocation();
		File directory;
		try {
			directory = new File(localDir.toURI());
		} catch (URISyntaxException e) {
			directory = new File(localDir.getPath());
		}
		return directory;
	}

	/**
	 * Shows a text input dialog where user can enter in text of their choosing.
	 * User response is returned, i.e. the entered text
	 * 
	 * @param String
	 *            title: title for input dialog
	 * @param Sting
	 *            prompt: detailed message to the user about the input box
	 * @return String result: user response if there is one, null if there isn't
	 */

	public static String userInputBox(String title, String prompt) {
		TextInputDialog input = new TextInputDialog("");
		input.setTitle(title);
		input.setContentText(prompt);
		Optional<String> response = input.showAndWait();
		if (response.isPresent()) {
			return response.get();
		}
		return null;
	}

	/**
	 * Shows a choice box where the user can pick among preset options. User
	 * response/choice is returned as a string.
	 * 
	 * @param List<String>
	 *            choices: choices given to the user in the dialogue
	 * @param String
	 *            title: title of dialogue, could be null
	 * @param String
	 *            header: header for the dialogue, could be null
	 * @param String
	 *            content: longer message further describing the dialogue, could
	 *            be null (all three shouldn't be null at once or dialogue is
	 *            useless)
	 * @return String result: user choice as a string from the given choice
	 *         options
	 */

	public String choiceBox(List<String> choices, String title, String header, String content) {
		ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
		dialog.setTitle(title);
		dialog.setHeaderText(header);
		dialog.setContentText(content);
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			return result.get();
		}

		return null;
	}

	/**
	 * Creates a Titled Pane, with all content already inside.
	 * 
	 * @param String
	 *            title: Title that will appear on the top of the Pane
	 * @param Node
	 *            content: Content to be added. It can be a Group or a VBox or
	 *            HBox.
	 * @param boolean
	 *            collapsable: If it's collapsable, the pane will not be
	 *            expanded. If it isn't, it will.
	 * @return TitledPane pane, already initialized.
	 */
	
	public static TitledPane makeTitledPane(String title, Node content, boolean collapsable) {
		TitledPane pane = new TitledPane(title, content);
		pane.setCollapsible(collapsable);
		pane.setExpanded(!collapsable);
		return pane;
	}

	public static ButtonType confirmationBox(String title, String header, String message) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(message);

		return null;
	}

	/**
	 * Gets all file names from a given directory. Is static so that it can be
	 * accessed as the actual class is never instantiated, also so that function
	 * can be accessed without this object being passed.
	 *
	 * @param directoryLocation
	 *            String path to a file directory
	 * @return List of Strings of all file names within given directory
	 */
	public static List<String> getAllFromDirectory(String directoryLocation) {

		ArrayList<String> files = new ArrayList<>();
		File directory = new File(directoryLocation);
		File[] fileList = directory.listFiles();
		for (File file : fileList) {
			String name = file.getName();
			if(name.contains(".")){
				files.add(name.substring(0, name.lastIndexOf('.')));
			}
			else{
				files.add(name);
			}
		}
		return files;
	}

	/**
	 * Creates an IEntity copy of the given IEntity with the same specs,
	 * components, and component values.
	 * 
	 * @param IEntity
	 *            entity: given IEntity to copy
	 * @return IEntity newEntity: returned copy of the given IEntity
	 */

	public static IEntity copyEntity(IEntity entity) {
		IEntity newEntity = new Entity(entity.getName());
		newEntity.setSpecs(entity.getSpecs());
		for (IComponent component : entity.getAllComponents()) {
			newEntity.addComponent(component.clone(component.getClass()));
			componentInitialization(newEntity, entity);
		}
		return newEntity;
	}

	private static void componentInitialization(IEntity newEntity, IEntity oldEntity) {
		if (newEntity.hasComponent(Position.class)) {
			newEntity.removeComponent(Position.class);
			Position newPos = new Position();
			newEntity.forceAddComponent(newPos, true);
		}
		if (newEntity.hasComponent(ImagePath.class)) {
			newEntity.removeComponent(ImagePath.class);
			ImagePath newPath = new ImagePath(oldEntity.getComponent(ImagePath.class).getImagePath());
			newEntity.forceAddComponent(newPath, true);
		}
	}

	public static List<ExtensionFilter> getImageFilters() {
		List<ExtensionFilter> filters = new ArrayList<ExtensionFilter>();
		filters.add(new FileChooser.ExtensionFilter("All Images", "*.*"));
		filters.add(new FileChooser.ExtensionFilter("JPG", "*.jpg"));
		filters.add(new FileChooser.ExtensionFilter("PNG", "*.png"));
		return filters;
	}

	public static ContextMenu createContextMenu(Map<String, EventHandler<ActionEvent>> menuMap) {
		ContextMenu context = new ContextMenu();
		List<MenuItem> menuItems = new ArrayList<MenuItem>();
		for (Entry<String, EventHandler<ActionEvent>> entry : menuMap.entrySet()) {
			MenuItem item = new MenuItem(entry.getKey());
			item.setOnAction(entry.getValue());
			menuItems.add(item);
		}
		context.getItems().addAll(menuItems);
		return context;
	}
	
	   /**
	    * Creates a new slider, setting all necessary intervals and locations. 
	    *
	    * @param ChangeListener<Number>				the changeImage the slider is listening to and event/method it calls when the changeImage occurs
	    * @param start 							the start of the slider range
	    *  @param end 							the end of the slider range
	    *   @param incr 							the increment of the slider range
	    * @return slider 							the new slider
	    * 
	    */
	    public static Slider makeSlider(ChangeListener<Number> listener, double start, double end, double currVal){
	    	Slider slider = new Slider(start, end, currVal);
	     	slider.setShowTickMarks(true);
	        ChangeListener<Number> changer = listener;
	        slider.valueProperty().addListener(changer);
	        return slider;
	    }

}
