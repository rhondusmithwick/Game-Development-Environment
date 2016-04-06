package testing;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import api.IPhysicsEngine;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.Vooga;
import model.component.character.Health;
import model.component.character.Score;
import model.component.movement.Position;
import model.component.movement.Velocity;
import model.component.visual.ImagePath;
import model.entity.Entity;
import api.IEntity;

public class BenTester extends Application {
	private Vooga vooga;
	private Stage stage;
	private static final String IMAGE_PATH = "resources/RhonduSmithwick.JPG";
	private static ImageView testSprite; // BAD PRACTICE: USE final WHENEVER
											// POSSIBLE, DON'T BE LIKE ME!
	private static IPhysicsEngine physics; // TODO: remove these things

	public BenTester() {
		stage = new Stage();
		vooga = new Vooga(stage);
	}

	public void start(Stage s) {
		Scene scene = vooga.init();
		s.setScene(scene);
		s.show();

		List<IEntity> list = new ArrayList<IEntity>();
		IEntity character = new Entity(0);
		character.forceAddComponent(new Health((double) 100), true);
		character.forceAddComponent(new Score((double) 100), true);
		Position pos = new Position(250.0, 250.0);
		character.forceAddComponent(pos, true);
		character.forceAddComponent(new ImagePath(IMAGE_PATH), true);
		character.forceAddComponent(new Velocity(10.0, 10.0), true);
		list.add(character);

		IEntitySystem system = new EntitySystem();
		system.addEntities(list);
		// TODO: don't lazy-initialize!
		physics = new PhysicsEngine(system);

		// TODO: seriously, don't lazy-initialize
		testSprite = createImage(character.getComponent(ImagePath.class), character.getComponent(Position.class));

		int MILLISECOND_DELAY = 10;
		double SECOND_DELAY = 0.01;
		// sets the game's loop
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> this.step(SECOND_DELAY, list, system));
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();

	}

	private void step(double dt, List<IEntity> list, IEntitySystem system) {
		physics.update(system, dt);
		drawEntities(list);
	}

	// private void test(IPhysicsEngine physics, IEntitySystem system, Position
	// pos) {
	// physics.update(system, 1);
	// System.out.println(pos);
	// }

	private void drawEntities(List<IEntity> list) {
		for (IEntity entity : list) {
			Position pos = entity.getComponent(Position.class);
			// ImagePath imagePath = entity.getComponent(ImagePath.class);
			// ImageView image = createImage(imagePath, pos);
			// testSprite.setTranslateX(pos.getX());
			// testSprite.setTranslateY(pos.getY());
			// testSprite.relocate(pos.getX(), pos.getY());
			vooga.refreshDraw(testSprite, pos.getX(), pos.getY());
		}
	}

	private ImageView createImage(ImagePath path, Position pos) {
		URI resource = new File(path.getImagePath()).toURI();
		Image image = new Image(resource.toString());
		ImageView imageView = new ImageView(image);
		// imageView.setTranslateX(pos.getX());
		// imageView.setTranslateY(pos.getY());
		imageView.setFitHeight(100);
		imageView.setPreserveRatio(true);
		imageView.xProperty().bind(pos.xProperty());
		imageView.yProperty().bind(pos.yProperty());
		return imageView;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
