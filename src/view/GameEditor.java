package view;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
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
	
	private VBox vBox;
	private List<Node> entryList;
	private String iconPath;
	private ImageView icon;
	private ResourceBundle myResources;
	
	GameEditor(){
		vBox = new VBox(20);
		vBox.setPadding(ViewInsets.GAME_EDIT.getInset());
		vBox.setAlignment(Pos.TOP_CENTER);
		entryList = new ArrayList<>();
		myResources = ResourceBundle.getBundle("english");
	}

	@Override
	public void loadDefaults() {
		// TODO Auto-generated method stub

	}

	@Override
	public Pane getPane() {
		populateLayout(vBox);
		return vBox;
	}

	@Override
	public void populateLayout(Pane pane) {
		createTextEntries(pane);
		showIcon(pane);

	}

	private void showIcon(Pane pane) {
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

	private void createTextEntries(Pane pane) {
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
		populateLayout(vBox);

	}

}
