package gui;

import java.io.File;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.function.BiConsumer;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.Agent;

public class GuiObjectInputBox extends GuiObject{

	private TextField userInputFileString;
	private Button initializeButton;
	private Labeled fileErrorLabel;
	private boolean boolInit;
	private static final String FILE_DIRECTORY = "images/";
	private static final String FILE_TYPE = ".png";
	private static final double MAXWIDTH = 150;
	private BiConsumer<Observable,String> setValueFunction;
	private ResourceBundle cssResources = ResourceBundle.getBundle("CSSClasses");
	
	public GuiObjectInputBox(String name, 
			String resourceBundle, Agent agent, BiConsumer<Observable, String> myFunction) {
		super(name,resourceBundle, agent);
		setValueFunction = myFunction;
	}

	@Override
	public Object createObjectAndReturnObject() {
		ResourceBundle resources = getResourceBundle();
		fileErrorLabel = new Label();
		fileErrorLabel.setVisible(false);
		fileErrorLabel.setMaxWidth(MAXWIDTH);
		fileErrorLabel.setWrapText(true);
		userInputFileString = new TextField(((Agent) getSerializable()).getImagePath());
		userInputFileString.setMaxWidth(MAXWIDTH);
		initializeButton = new Button(resources.getString(getObjectName()+"BUTTON"));
		initializeButton.setOnAction(evt -> {if (checkIfValid(resources,userInputFileString.getText())){
			setValueFunction.accept(getSerializable(),userInputFileString.getText());
		}});
		initializeButton.setMaxWidth(MAXWIDTH);
		
		VBox XMLControls = new VBox();
		XMLControls.getStyleClass().add(cssResources.getString("VBOX"));
		XMLControls.getChildren().addAll(userInputFileString, initializeButton,fileErrorLabel);
		
		return XMLControls;
	}

	private boolean checkIfValid(ResourceBundle resources, String filePath) {
		File f = new File(FILE_DIRECTORY+filePath);
		
		if (f.isFile()){
			fileErrorLabel.setText(resources.getString("ValidFileType"));
			fileErrorLabel.setVisible(true);
			return true;
		}else{
			if (filePath.length()< 4 || !filePath.substring(filePath.length()-4,filePath.length()).equals(FILE_TYPE)){
				
			fileErrorLabel.setText(resources.getString("NotValidFileType"));
		}else{
			fileErrorLabel.setText(resources.getString("FileNotFound"));
		}
		}
		fileErrorLabel.setVisible(true);
		return false;
		
	}

	public boolean getInitValue(){
		return boolInit;
	}

	public void setValue(boolean b) {
		boolInit = b;
	
	}
	public String getChosenFileString(){
		return userInputFileString.getText();
	}



}
