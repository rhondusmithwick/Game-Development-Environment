package view.utilities;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ChoiceDialog;

public class ChoiceDialogFactory {
	private ChoiceDialogFactory(){
		
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
}
