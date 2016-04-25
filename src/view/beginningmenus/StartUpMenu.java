package view.beginningmenus;

import java.io.File;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import view.enums.DefaultStrings;
import view.enums.GUISize;

public abstract class StartUpMenu {

	private Scene myScene;
	private Stage myStage;
	private Group root;
	private VBox myVBox;
	private Media media;
	private MediaPlayer mediaPlayer;

	private ScrollPane scrollPane;

	public StartUpMenu(Stage myStage) {
		this.myStage = myStage;
	}

	public void init() {
		myScene = new Scene(createDisplay(), GUISize.MAIN_SIZE.getSize(), GUISize.MAIN_SIZE.getSize());
		myScene.getStylesheets()
				.add(new File(DefaultStrings.CSS_LOCATION.getDefault() + DefaultStrings.MAIN_CSS.getDefault()).toURI()
						.toString());
		setMusic(DefaultStrings.MUSIC.getDefault() + DefaultStrings.THEME.getDefault());
		myStage.setScene(myScene);
		myStage.show();
	}

	protected ScrollPane createDisplay() {
		root = new Group();
		createVBox();
		scrollPane = new ScrollPane(root);
		return scrollPane;
	}

	private void setMusic(String file) {
		media = new Media(new File(file).toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		// mediaPlayer.play(); TODO: rm
	}

	private void createVBox() {
		myVBox = new VBox(GUISize.ORIG_MENU_PADDING.getSize());
		myVBox.prefHeightProperty().bind(myStage.heightProperty());
		myVBox.prefWidthProperty().bind(myStage.widthProperty());
		myVBox.setAlignment(Pos.CENTER);
		myVBox.getStyleClass().add("vbox");
		root.getChildren().add(myVBox);
	}

	public void addNodesToVBox(List<Node> toAdd) {
		myVBox.getChildren().addAll(toAdd);
	}
}
