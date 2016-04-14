package view.beginingmenus;

import java.io.File;
import java.util.List;

import enums.GUISize;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public abstract class StartUpMenu {

	private Scene myScene;
	private Stage myStage;
	private Group root;
	private VBox myVBox;
	private Media media;
	private MediaPlayer mediaPlayer;
	private static final String MUSIC = "resources/music/";
	private static final String MAIN_THEME = "finalCountdown.mp3";
	
	public StartUpMenu(Stage myStage){
		this.myStage = myStage;
	}

	public void init() {
		myScene = new Scene(createDisplay(), GUISize.MAIN_SIZE.getSize(), GUISize.MAIN_SIZE.getSize());
		setMusic(MUSIC + MAIN_THEME);
		myStage.setScene(myScene);
		myStage.show();
	}
	
	protected Group createDisplay(){
		root = new Group();
		createVBox();
		return root;
	}
	
	private void setMusic(String file) {
        media = new Media(new File(file).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();	
	}

	private void createVBox() {
		myVBox = new VBox(GUISize.ORIG_MENU_PADDING.getSize());
		myVBox.prefHeightProperty().bind(myStage.heightProperty());
		myVBox.prefWidthProperty().bind(myStage.widthProperty());
		myVBox.setAlignment(Pos.CENTER);
		root.getChildren().add(myVBox);
	}
	
	public void addNodesToVBox(List<Node> toAdd){
		myVBox.getChildren().addAll(toAdd);
	}
}
