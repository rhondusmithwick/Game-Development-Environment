package view.editor.eventeditor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import api.ILevel;
import view.enums.GUISize;
import view.enums.ViewInsets;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class LevelPicker 
{
	private ObservableList<ILevel> levelList;
	private ScrollPane pane;
	private VBox vbox;
	private String language;
	private CheckBox allBox;
	private final HashMap<CheckBox, ILevel> checkBoxMap;
	private ArrayList<ILevel> selectedLevels;
	private final EventEditorTab eventAuthoring;
	private ResourceBundle myResources;
	
	public LevelPicker(String language, ObservableList<ILevel> levelList, EventEditorTab eventAuthoring)
	{
		checkBoxMap = new HashMap<CheckBox, ILevel>();
		this.eventAuthoring = eventAuthoring;
		this.levelList = levelList;
		selectedLevels = new ArrayList<ILevel>();
		myResources = ResourceBundle.getBundle(language);

		vbox = new VBox(GUISize.EVENT_EDITOR_PADDING.getSize());
		vbox.setPadding(ViewInsets.GAME_EDIT.getInset());
		vbox.setAlignment(Pos.TOP_LEFT);
		
		populatePane();
		pane = new ScrollPane(vbox);
	}
	
	private void populatePane()
	{
		allBox = new CheckBox(myResources.getString("all"));
		allBox.setSelected(true);
		allBox.setOnAction(e -> allBoxCheck(allBox.isSelected()));
		vbox.getChildren().add(allBox);
		
		if ( levelList != null )
		{
			for ( ILevel level: levelList )
			{
				CheckBox newCheckbox = new CheckBox(level.getName());
				newCheckbox.setSelected(true);
				newCheckbox.setOnAction(e -> levelCheck());
				checkBoxMap.put(newCheckbox, level);
				vbox.getChildren().add(newCheckbox);
			}
		}
	}
	
	private void allBoxCheck(boolean isSelected)
	{
		for ( CheckBox checkbox: checkBoxMap.keySet() )
		{
			checkbox.setSelected(isSelected);
		}
		
		levelCheck();
	}
	
	private void levelCheck()
	{
		selectedLevels.clear();
		boolean allAreSelected = true;
		for ( CheckBox checkbox: checkBoxMap.keySet() )
		{
			if ( checkbox.isSelected() )
			{
				selectedLevels.add(checkBoxMap.get(checkbox));
			}
			else
			{
				allAreSelected = false;
			}
		}
		
		allBox.setSelected(allAreSelected);
		eventAuthoring.choseLevels(selectedLevels);
	}
	
	public ScrollPane getPane()
	{
		return pane;
	}
}
