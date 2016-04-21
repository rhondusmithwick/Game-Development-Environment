package view;

import javafx.scene.control.TextArea;

public class ConsoleTextArea extends TextArea {

	// Reference:
	// http://stackoverflow.com/questions/29699040/javafx-how-to-restrict-manipulation-of-textarea-to-last-row
	@Override
	public void replaceText(int start, int end, String text) {
		String current = getText();
		// only insert if no new lines after insert position:
		if (!current.substring(start).contains("\n")) {
			super.replaceText(start, end, text);
		}
	}

	@Override
	public void replaceSelection(String text) {
		String current = getText();
		int selectionStart = getSelection().getStart();
		if (!current.substring(selectionStart).contains("\n")) {
			super.replaceSelection(text);
		}
	}

	public void print(String text) {
		this.appendText(text);
	}

	public void println(String text) {
		this.print(text + "\n");
	}

	public void println() {
		this.println("");
	}

}
