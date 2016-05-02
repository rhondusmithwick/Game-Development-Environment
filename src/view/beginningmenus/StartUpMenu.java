package view.beginningmenus;

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

import java.io.File;
import java.util.List;

public abstract class StartUpMenu {

    private final Stage myStage;
    private Group root;
    private VBox myVBox;

    public StartUpMenu (Stage myStage) {
        this.myStage = myStage;
    }

    public void init () {
        Scene myScene = new Scene(createDisplay(), GUISize.MAIN_SIZE.getSize(), GUISize.MAIN_SIZE.getSize());
        myScene.getStylesheets()
                .add(new File(DefaultStrings.CSS_LOCATION.getDefault() + DefaultStrings.MAIN_CSS.getDefault()).toURI()
                        .toString());
        setMusic(DefaultStrings.MUSIC.getDefault() + DefaultStrings.THEME.getDefault());
        myStage.setScene(myScene);
        myStage.show();
    }

    protected ScrollPane createDisplay () {
        root = new Group();
        createVBox();
        return  new ScrollPane(root);
    }

    private void setMusic (String file) {
        Media media = new Media(new File(file).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        // mediaPlayer.play(); TODO: rm
    }

    private void createVBox () {
        myVBox = new VBox(GUISize.ORIG_MENU_PADDING.getSize());
        myVBox.prefHeightProperty().bind(myStage.heightProperty());
        myVBox.prefWidthProperty().bind(myStage.widthProperty());
        myVBox.setAlignment(Pos.CENTER);
        myVBox.getStyleClass().add("vbox");
        root.getChildren().add(myVBox);
    }

    public void addNodesToVBox (List<Node> toAdd) {
        myVBox.getChildren().addAll(toAdd);
    }
}
