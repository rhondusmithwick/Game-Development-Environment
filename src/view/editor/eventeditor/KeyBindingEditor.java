package view.editor.eventeditor;

import java.util.ResourceBundle;

import api.ISerializable;
import enums.GUISize;
import enums.ViewInsets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import view.editor.Editor;

public class KeyBindingEditor extends Editor 
{
	private boolean keyListenerIsActive;
	private HBox pane;
	
	private VBox inputBox;
	private KeyCode currentKey;
	private Button listenToKey;
	private Text keyInputText;
	private final ResourceBundle myResources;
	
	public KeyBindingEditor(String language)
	{
		myResources = ResourceBundle.getBundle(language);
		keyListenerIsActive = false;
		pane = new HBox(GUISize.EVENT_EDITOR_PADDING.getSize());
		pane.setPadding(ViewInsets.GAME_EDIT.getInset());
		pane.setAlignment(Pos.TOP_LEFT);
		inputBox = new VBox(GUISize.EVENT_EDITOR_PADDING.getSize());
		makeInputBox();
		pane.getChildren().add(inputBox);
		pane.setOnKeyPressed(e -> keyWasPressed(e.getCode()));	
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
		listenToKey = new Button("Press a Key!");	// TODO: resource
		listenToKey.setOnAction(e -> listenButtonPress());
		
		keyInputText = new Text("No Key Pressed!");	// TODO: resource
		
		inputBox.getChildren().addAll(listenToKey, keyInputText);
	}
	
	private void listenButtonPress()
	{
		keyListenerIsActive = true;
		keyInputText.setText("Listening....");
	}
	
	@Override
	public void loadDefaults() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Pane getPane() 
	{
		return pane;
	}

	@Override
	public void populateLayout() {}

	@Override
	public void addSerializable(ISerializable serialize) {}

	@Override
	public void updateEditor() {}

}
