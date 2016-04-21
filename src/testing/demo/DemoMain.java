
package testing.demo;

import enums.GUISize;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.component.audio.SoundEffect;
import model.component.audio.ThemeMusic;

public class DemoMain extends Application {

	private Stage myStage;
	private ThemeMusic music;

	/**
	 * Sets up a stage to launch our window and initializes the splash screen.
	 * 
	 * @param stage
	 */
	public void start(Stage stage) {
		myStage = stage;
		myStage.setTitle("VOOGA");
		myStage.setWidth(GUISize.MAIN_SIZE.getSize());
		myStage.setHeight(GUISize.MAIN_SIZE.getSize());

		View view = new View();
		Pane pane = view.getPane();
		Group root = new Group();
		Scene scene = new Scene(root, 500, 500);
		music = new ThemeMusic("resources/music/finalCountdown.mp3");
		music.play();
		Button button = new Button("Mute");
		button.setOnAction(e -> shoot());
		root.getChildren().add(button);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Launches our program.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	private void mute() {
		if(music.isPlaying()) {
			music.pause();
		} else {
			music.play();
		}
	}
	
	private void shoot() {
		SoundEffect effect = new SoundEffect("resources/soundfx/laser.mp3");
		effect.play();
	}

}