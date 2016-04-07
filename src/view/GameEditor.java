package view;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import api.IEditor;
import enums.FileExtensions;
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

public class GameEditor extends Editor {
	
	private VBox pane;
	private List<Node> entryList;
	private String iconPath;
	private ImageView icon;
	private ResourceBundle myResources;
	private EditorFactory editFact;
	private Authoring authEnv;
	
	GameEditor(Authoring authEnv){
		pane = new VBox(20);
		pane.setPadding(ViewInsets.GAME_EDIT.getInset());
		pane.setAlignment(Pos.TOP_LEFT);
		entryList = new ArrayList<>();
		myResources = ResourceBundle.getBundle("english");
		editFact = new EditorFactory();
		this.authEnv=authEnv;
		
	}

	@Override
	public void loadDefaults() {
		// TODO Auto-generated method stub

	}

	@Override
	public Pane getPane() {
		populateLayout();
		return pane;
	}

	@Override
	public void populateLayout() {
		createTextEntries();
		showIcon();
		editorButtons();
		pane.getChildren().add(Utilities.makeButton("Save Game", e->saveGame()));

	}

	private void saveGame() {
		// TODO Auto-generated method stub
	}

	private void editorButtons() {
		pane.getChildren().add(Utilities.makeButton("Create Entity", e->createEditor("EditorEntity")));
		
	}

	private void createEditor(String editName) {
		IEditor editor = editFact.createEditor(editName);
		editor.populateLayout();
		authEnv.createTab(editor.getPane(), editName, true);
		
	}

	private void showIcon() {
		HBox iconBox = new HBox(50);
		Label iconTitle = new Label("Game Icon:");
		icon = new ImageView();
		setIconPicture(new File("resources/default_icon.png"));
		iconBox.getChildren().addAll(iconTitle, icon, Utilities.makeButton("Choose Icon", e->updateIcon()));
		pane.getChildren().add(iconBox);
	}

	private void setIconPicture(File file) {
		iconPath = file.toURI().toString();
		icon.setImage(new Image(iconPath));
		icon.setFitHeight(50);
		icon.setFitWidth(50);
	}

	private void updateIcon() {
		Stage s = new Stage();
		FileChooser fChoose = new FileChooser();
		fChoose.setTitle("Choose Icon");
		fChoose.getExtensionFilters().addAll(FileExtensions.GIF.getFilter(), FileExtensions.JPG.getFilter(), FileExtensions.PNG.getFilter());
		File file = fChoose.showOpenDialog(s);
		setIconPicture(file);
		
	}

	private void createTextEntries() {
		HBox name = new HBox(50);
		Label nTitle = new Label("Game Name:");
		TextArea nameBox = Utilities.makeTextArea("Game Name");
		name.getChildren().addAll(nTitle, nameBox);
		HBox.setHgrow(nameBox, Priority.SOMETIMES);
		HBox desc = new HBox(50);
		Label dTitle = new Label("Game Description:");
		TextArea description = Utilities.makeTextArea("Game Description");
		HBox.setHgrow(description, Priority.SOMETIMES);
		desc.getChildren().addAll(dTitle, description);
		pane.getChildren().addAll(name, desc);
		entryList.addAll(Arrays.asList(nameBox, description));
	}

	@Override
	public void updateEditor() {
		populateLayout();

	}

}
