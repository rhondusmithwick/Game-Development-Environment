package view.utilities;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;

public class ComboFactory {
	public ComboFactory(){
		
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
}
