package view.editor.eventeditor;

import java.io.File;
import java.util.ResourceBundle;

import api.ISerializable;
import events.Action;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import view.Utilities;
import view.editor.Editor;
import view.enums.GUISize;
import view.enums.ViewInsets;

public class KeyBindingEditor extends Editor 
{
	private boolean keyListenerIsActive;
	private ScrollPane scrollPane;
	private HBox pane;
	
	private VBox inputBox;
	private KeyCode currentKey;
	private Button listenToKey;
	private Button chooseFileButton;
	private Text keyInputText;
	private final ResourceBundle myResources;
	private Action currentAction;
	
	public KeyBindingEditor(String language, ObservableList<ISerializable> masterEnvironmentList)
	{
		scrollPane = new ScrollPane();
		myResources = ResourceBundle.getBundle(language);
		keyListenerIsActive = false;
		pane = new HBox(GUISize.EVENT_EDITOR_PADDING.getSize());
		pane.setPadding(ViewInsets.GAME_EDIT.getInset());
		pane.setAlignment(Pos.TOP_LEFT);
		inputBox = new VBox(GUISize.EVENT_EDITOR_PADDING.getSize());
		
		pane.setOnKeyPressed(e -> keyWasPressed(e.getCode()));
		
		populateLayout();
	}
	
	private void keyWasPressed(KeyCode code)
	{
		if (!keyListenerIsActive)
			return;
		
		currentKey = code;
		keyInputText.setText("Key: " + code.getName());	// TODO: resource
		keyListenerIsActive = false;
	}
	
	private void makeInputBox()
	{
		listenToKey = Utilities.makeButton("Press a Key!", e -> listenButtonPress()); // TODO: resource
		
		keyInputText = new Text("No Key Pressed!");	// TODO: resource
		
		inputBox.getChildren().addAll(listenToKey, keyInputText);
		
		pane.getChildren().add(inputBox);
	}
	
	private void makeGroovyBox()
	{
		VBox container = new VBox(GUISize.EVENT_EDITOR_HBOX_PADDING.getSize());
		// Adding now the Groovy Table
		chooseFileButton = Utilities.makeButton("Choose Groovy Script", e -> getFile());	// TODO resource
		
		container.getChildren().addAll(chooseFileButton);
		
		pane.getChildren().add(container);
	}
	

	private void getFile()
	{
		File groovyFile = null;
		
		groovyFile = Utilities.promptAndGetFile(new FileChooser.ExtensionFilter("groovy", "*.groovy"), "Select your groovy script!");
		if ( groovyFile != null )
		{
			currentAction = new Action(groovyFile.getAbsolutePath());
		}
	}
	
	private void listenButtonPress()
	{
		keyListenerIsActive = true;
		keyInputText.setText("Listening....");
	}
	

	@Override
	public ScrollPane getPane() 
	{
		return scrollPane;
	}

	@Override
	public void populateLayout() 
	{
		makeInputBox();
		makeGroovyBox();
		
		scrollPane.setContent(pane);
	}


	@Override
	public void updateEditor() {}

}
