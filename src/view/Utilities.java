package view;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
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
		
		public static void showError(String message, ResourceBundle displayStrings) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle(displayStrings.getString("error"));
			alert.setContentText(displayStrings.getString(message));
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
		

		public static TableView<String> makeSingleColumnTable( String title )
		{
			TableView<String> table = new TableView<String>();
			TableColumn column = new TableColumn(title);
			column.prefWidthProperty().bind(table.widthProperty()); 

			table.getColumns().setAll(column);
			
			return table;
		}
		/**
		 * Prompts a file chooser box for the user to choose a file @ param
		 * ExtensionFilter extension: file extension that the user can choose from,
		 * all others are not allowed @ param String prompt: prompt for the file
		 * chooser box
		 */
		private String promptAndGetFile(ExtensionFilter extension, String prompt) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle(prompt);
			fileChooser.getExtensionFilters().add(extension);
			fileChooser.setInitialDirectory(getLocalDir());
			File file = fileChooser.showOpenDialog(new Stage());
			return file.getName();
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
	}
