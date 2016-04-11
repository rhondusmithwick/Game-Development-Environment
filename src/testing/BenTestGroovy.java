package testing;

import api.IEntity;
import api.IEntitySystem;
import api.IPhysicsEngine;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.component.movement.Position;
import model.component.visual.ImagePath;
import model.entity.EntitySystem;
import model.physics.PhysicsEngine;

import testing.BenTestCharacter;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class BenTestGroovy extends Application {
	private final ScriptEngine engine = new ScriptEngineManager().getEngineByName("groovy");
	private static final String IMAGE_PATH = "resources/RhonduSmithwick.JPG";
	private static ImageView testSprite; // BAD PRACTICE: USE final WHENEVER
											// POSSIBLE, DON'T BE LIKE ME!
	private static IPhysicsEngine physics; // TODO: remove these things
	private Group root;

	public void start(Stage s) {
		root = new Group();
		BorderPane splash = new BorderPane();
		splash.getChildren().add(root);
		Scene scene = new Scene(splash, 500, 500);
		s.setScene(scene);
		s.show();

		List<IEntity> list = new ArrayList<IEntity>();
		IEntity character = (new BenTestCharacter()).run(IMAGE_PATH);
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

	private void drawEntities(List<IEntity> list) {
		for (IEntity entity : list) {
			Position pos = entity.getComponent(Position.class);
			refreshDraw(testSprite, pos.getX(), pos.getY());
		}
	}

	private ImageView createImage(ImagePath path, Position pos) {
		URI resource = new File(path.getImagePath()).toURI();
		Image image = new Image(resource.toString());
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(100);
		imageView.setPreserveRatio(true);
		imageView.xProperty().bind(pos.xProperty());
		imageView.yProperty().bind(pos.yProperty());
		return imageView;
	}

	public static void main(String[] args) {
		launch(args);
	}

	// TODO: for BenTest only, remove asap
	private void refreshDraw(ImageView img, double x, double y) {
		root.getChildren().clear();
		System.out.println(x + " " + y);
		root.getChildren().add(img);
	}

}
