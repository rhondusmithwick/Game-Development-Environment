package view.utilities;

import javafx.scene.control.TextField;

public class TextFieldFactory {
	private TextFieldFactory(){
		
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
}
