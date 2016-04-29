package view.utilities;

import java.util.Optional;

import javafx.scene.control.TextInputDialog;

public class UserInputBoxFactory {
	private UserInputBoxFactory(){
		
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
}
