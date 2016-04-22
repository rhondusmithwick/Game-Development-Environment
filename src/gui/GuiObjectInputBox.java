package gui;

import java.util.ResourceBundle;

import javafx.beans.property.Property;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class GuiObjectInputBox extends GuiObject{

	private TextField userInputFileString;
	private Button initializeButton;
	private Label fileErrorLabel;


	
	public GuiObjectInputBox(String name, String resourceBundle,EventHandler<ActionEvent> event, Property<?> property, Object object) {
		super(name,resourceBundle);
		fileErrorLabel = new Label();
		fileErrorLabel.setVisible(false);
		fileErrorLabel.setWrapText(true);
		userInputFileString = new TextField(object + "");
		initializeButton = new Button(getResourceBundle().getString(getObjectName()+"Button"));
		addHandler(event);

	}

	private void addHandler(EventHandler<ActionEvent> event) {
		initializeButton.setOnAction(event);
	}


	@SuppressWarnings("unused")
	private boolean checkIfValid(ResourceBundle resources, String filePath) {
		//error handling
		return true;
	}


	@Override
	public Object getCurrentValue() {
		// TODO Auto-generated method stub
		return userInputFileString.getText();
	}


	@Override
	public Object getGuiNode() {
		VBox XMLControls = new VBox();

		XMLControls.getChildren().addAll(userInputFileString, initializeButton,fileErrorLabel);
		
		return XMLControls;
	}



}
