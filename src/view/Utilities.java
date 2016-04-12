package view;

	import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
	import javafx.event.ActionEvent;
	import javafx.event.EventHandler;
	import javafx.scene.control.Alert;
	import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
	
	
	public class Utilities {
		/**
		 * Show an error with a certain message.
		 * @param error message
		 */
		
		public static void showError(String title,String message) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle(title);
			alert.setContentText(message);
			alert.show();
		}
		
		/**
		 * Makes a button with a name and event.
		 * @param name of button
		 * @param event to occur when button is pressed
		 * @return the button node with a name and event
		 */
		
		public static Button makeButton(String nameProperty, EventHandler<ActionEvent> handler) {
			Button button = new Button();
			button.setText(nameProperty);
			button.setOnAction(handler);
			return button;
		}
		
		/**
		 * Creates a combo box with the following features:
		 * @param prompt (the name of the combo box)
		 * @param choices in the combo box
		 * @param event to occur when the combo box is selected
		 * @return the combo box with the above features.
		 */
		
		public static ComboBox<String> makeComboBox(String prompt, List<String> choices, EventHandler<ActionEvent> event) {
			ComboBox<String> comboBox = new ComboBox<>();
			comboBox.getItems().addAll(choices);
			comboBox.setEditable(false);
			comboBox.setPromptText(prompt);
			comboBox.setOnAction(event); 
			return comboBox;
		}
		
		public static TextArea makeTextArea(String prompt){
			TextArea field = new TextArea();
			field.setPromptText(prompt);
			return field;
		}
		
		/**
		 * Prompts a file chooser box for the user to choose a file @ param
		 * ExtensionFilter extension: file extension that the user can choose from,
		 * all others are not allowed @ param String prompt: prompt for the file
		 * chooser box
		 */
		public static File promptAndGetFile(ExtensionFilter extension, String prompt) {
			List<ExtensionFilter> filters = new ArrayList<ExtensionFilter>();
			filters.add(extension);
			return promptAndGetFile(filters,prompt);
		}
		
		public static File promptAndGetFile(List<ExtensionFilter> filters, String prompt) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle(prompt);
			fileChooser.getExtensionFilters().addAll(filters);
			fileChooser.setInitialDirectory(getLocalDir());
			File file = fileChooser.showOpenDialog(new Stage());
			return file;
		}
		
		/**
		 * Directs file chooser box to the appropriate directory to use the files we
		 * have available.
		 */
		private static File getLocalDir() {
			ProtectionDomain pd = Utilities.class.getProtectionDomain();
			CodeSource cs = pd.getCodeSource();
			URL localDir = cs.getLocation();
			File dir;
			try {
				dir = new File(localDir.toURI());
			} catch (URISyntaxException e) {
				dir = new File(localDir.getPath());
			}
			return dir;
		}
		
		/**
		 * Shows a text input dialog where user can enter in text of their choosing to update a value. Errors messages are thrown if the user input is invalid. 
		 */
		public String userInputBox(String title, String prompt) {
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
		 * Shows a choice box where the user can pick among preset options to update a value or change a setting. 
		 */
		public String choiceBox(List<String> choices, ResourceBundle myResources) {
			ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
			dialog.setTitle(myResources.getString("choose"));
			dialog.setHeaderText(myResources.getString("promptChoice"));
			dialog.setContentText(myResources.getString("selectOption"));

			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()) {
				return result.get();
			}
			return null;
		}
		
		public static ButtonType confirmationBox (String title,String header, String message){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(message);

		Optional<ButtonType> result = alert.showAndWait();
		return result.get(); // check against result == ButtonType.OK
		}

				
	    /**
	     * Gets all file names from a given directory.
	     * Is static so that it can be accessed as the actual class is never instantiated,
	     * also so that function can be accessed without this object being passed.
	     *
	     * @param directoryLocation String path to a file directory
	     * @return List of Strings of all file names within given directory
	     */
	    public static List<String> getAllFromDirectory(String directoryLocation) {

	        ArrayList<String> files = new ArrayList<>();
	        File directory = new File(directoryLocation);
	        File[] fileList = directory.listFiles();
	        for (File file : fileList) {
	            String name = file.getName();
	            files.add(name.substring(0, name.lastIndexOf('.')));
	        }
	        return files;


	    }
		
	}
