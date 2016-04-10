package view;

	import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
	import javafx.event.ActionEvent;
	import javafx.event.EventHandler;
	import javafx.scene.control.Alert;
	import javafx.scene.control.Button;
	import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
	
	
	public class Utilities {
		/**
		 * Show an error with a certain message.
		 * @param error message
		 */
		
		public static void showError(String message, ResourceBundle displayStrings) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle(displayStrings.getString("error"));
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
		
		
	    
	}
