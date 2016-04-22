package view.editor.gameeditor;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import view.Utilities;
import view.enums.DefaultStrings;
import view.enums.FileExtensions;
import view.enums.GUISize;
import view.enums.Indexes;

public class GameDetails {
	
	private ResourceBundle myResources;
	private HBox nameBox, descriptionBox, iconBox;
	private TextField name, desc;
	private String iconPath;
	private ImageView icon;

	public GameDetails(String language){
		myResources = ResourceBundle.getBundle(language);
		createDetails();
		
	}

	private void createDetails() {
		nameBox = createTextEntry("gName");
		name = (TextField) nameBox.getChildren().get(1);
		descriptionBox = createTextEntry("gDesc");
		desc = (TextField) descriptionBox.getChildren().get(1);
		showIcon();
		
	}

	private HBox createTextEntry(String name){
		HBox container = new HBox(GUISize.GAME_EDITOR_HBOX_PADDING.getSize());
		Label title = new Label(myResources.getString(name));
		title.setMinWidth(GUISize.LABEL_MIN_WIDTH.getSize());
		TextField tArea = Utilities.makeTextArea(myResources.getString(name));
		container.getChildren().addAll(title, tArea);
		return container;
	}
	
	
	private HBox showIcon() {
		iconBox = new HBox(GUISize.GAME_EDITOR_HBOX_PADDING.getSize());
		iconBox.setAlignment(Pos.CENTER_LEFT);
		Label iconTitle = new Label(myResources.getString("gIcon"));
		icon = new ImageView();
		setIconPicture(new File(DefaultStrings.DEFAULT_ICON.getDefault()));
		iconBox.getChildren().addAll(iconTitle, icon, Utilities.makeButton(myResources.getString("cIcon"), e->updateIcon()));
		return iconBox;
	}

	private void setIconPicture(File file) {
		iconPath = file.toURI().toString();
		setImage();
	}

	private void setImage() {
		icon.setImage(new Image(iconPath));
		icon.setFitHeight(GUISize.ICON_SIZE.getSize());
		icon.setFitWidth(GUISize.ICON_SIZE.getSize());
	}

	private void updateIcon() {
		Stage s = new Stage();
		FileChooser fChoose = new FileChooser();
		fChoose.setTitle(myResources.getString("cIcon"));
		fChoose.getExtensionFilters().addAll(FileExtensions.GIF.getFilter(), FileExtensions.JPG.getFilter(), FileExtensions.PNG.getFilter());
		File file = fChoose.showOpenDialog(s);
		if(file!=null){
			setIconPicture(file);
		}
	}
	
	
	public void setDetails(List<String> list){
		name.setText(list.get(Indexes.GAME_NAME.getIndex()));
		desc.setText(list.get(Indexes.GAME_DESC.getIndex()));
		iconPath = list.get(Indexes.GAME_ICON.getIndex());
		setImage();
	}

	
	
	public List<Node> getElements(){
		return Arrays.asList(nameBox, descriptionBox, iconBox);
	}
	
	public List<String> getGameDetails(){
		return Arrays.asList(name.getText(), desc.getText(), iconPath);
	}
}
