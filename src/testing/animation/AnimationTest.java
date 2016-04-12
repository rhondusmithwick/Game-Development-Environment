package testing.animation;

import java.util.ArrayList;
import java.util.List;

import animation.AnimationEngine;
import api.IEntity;
import api.IEntitySystem;
import api.IPhysicsEngine;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.component.character.Health;
import model.component.character.Score;
import model.component.movement.Position;
import model.component.visual.ImagePath;
import model.entity.Entity;
import model.entity.EntitySystem;
import model.physics.PhysicsEngine;

public class AnimationTest extends Application {
	private static final String IMAGE_PATH = "resources/RhonduSmithwick.JPG";
	private static ImageView testSprite; // BAD PRACTICE: USE final WHENEVER
											// POSSIBLE, DON'T BE LIKE ME!
	private static IPhysicsEngine physics; // TODO: remove these things
	private Group root;

	public void start(Stage s) {
		root = new Group();
		BorderPane splash = new BorderPane();
		// splash.setCenter(root);
		splash.getChildren().add(root);
		// splash.setCenter(root);
		Scene scene = new Scene(splash, 500, 500);
		s.setScene(scene);
		s.show();

		List<IEntity> list = new ArrayList<IEntity>();
		IEntity character = new Entity();
		character.forceAddComponent(new Health((double) 100), true);
		character.forceAddComponent(new Score((double) 100), true);
		Position pos = new Position(250.0, 250.0);
		character.forceAddComponent(pos, true);
		character.forceAddComponent(
				new ImagePath("resources/spriteSheets/spritesheet.png", 0.0, 0.0,
						"resources/spriteSheets/spritesheet.png", new Rectangle2D(0, 0, 125, 125), true, 0.01, 4.0, 4),
				true);
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
		AnimationEngine a = new AnimationEngine();
		a.update(system, dt);
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
			ImagePath imagePath = entity.getComponent(ImagePath.class);
			Rectangle2D viewport = imagePath.getViewport(); // TODO: for some reason, setting viewport internally fails
			// ImageView image = createImage(imagePath, pos);
			// testSprite.relocate(pos.getX(), pos.getY());
			refreshDraw(testSprite, viewport, pos.getX(), pos.getY());
		}
	}

	private ImageView createImage(ImagePath path, Position pos) {
		// File resource = new File(path.getImagePath());
		// Image image = new Image(resource.toURI().toString());
		// ImageView imageView = new ImageView(image);
		ImageView imageView = path.getImageView();
		imageView.setFitHeight(100);
		imageView.setPreserveRatio(true);
		imageView.xProperty().bind(pos.xProperty());
		imageView.yProperty().bind(pos.yProperty());
		return imageView;
	}

	public static void main(String[] args) {
		launch(args);
	}

	private void refreshDraw(ImageView img, Rectangle2D viewport, double x, double y) {
		root.getChildren().clear();
		img.setViewport(viewport);
		// System.out.println(x + " " + y);
		root.getChildren().add(img);
	}

}
