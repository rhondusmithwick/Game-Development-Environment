package view;

import javafx.scene.layout.Pane;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import api.IEditor;
import enums.DefaultStrings;
import enums.FileExtensions;
import enums.GUISize;
import enums.ViewInsets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class EventEditor extends Editor
{
	private VBox pane;
	private List<Node> entryList;
	private String iconPath;
	private ImageView icon;
	private ResourceBundle myResources;
	private EditorFactory factory;
	private Authoring authoringEnvironment;
	
	EventEditor(Authoring authoringEnvironment, String language)
	{
		pane = new VBox(GUISize.EVENT_EDITOR_PADDING.getSize());
		pane.setPadding(ViewInsets.GAME_EDIT.getInset());
		pane.setAlignment(Pos.TOP_LEFT);
		entryList = new ArrayList<>();
		myResources = ResourceBundle.getBundle(language);
		factory = new EditorFactory();
		this.authoringEnvironment = authoringEnvironment;
	}
	
	private void makeButtons()
	{
		pane.getChildren().add(Utilities.makeButton(myResources.getString("createTrigger"), 
				e-> createTrigger()));
	}
	
	private void createTrigger()
	{
		System.out.println("Creating Trigger!");
	}
	
	@Override
	public void loadDefaults() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Pane getPane() 
	{
		populateLayout();
		return pane;
	}

	@Override
	public void populateLayout() 
	{
		makeButtons();
	}

	@Override
	public void updateEditor() {
		// TODO Auto-generated method stub
		
	}

}
