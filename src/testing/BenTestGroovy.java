package testing;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
import model.component.physics.Collision;
import model.component.visual.ImagePath;
import model.entity.EntitySystem;
import model.physics.PhysicsEngine;

public class BenTestGroovy extends Application {
	// private final ScriptEngine engine = new
	// ScriptEngineManager().getEngineByName("groovy");
	private static final String IMAGE_PATH = "resources/RhonduSmithwick.JPG";
	private static final String IMAGE_PATH2 = "resources/images/blastoise.png";
	private static final double MILLISECOND_DELAY = 10;
	private static final double SECOND_DELAY = MILLISECOND_DELAY / 1000;
	private static final Group ROOT = new Group();
	private Map<IEntity, ImageView> entityToImage;

	public void start(Stage s) {
		entityToImage = new HashMap<>();
		BorderPane splash = new BorderPane();
		splash.getChildren().add(ROOT);
		Scene scene = new Scene(splash, 500, 500);
		s.setScene(scene);
		s.show();

		// Sprites
		IEntity rhonduEntity = BenTestCharacter.run(IMAGE_PATH);
		IEntity rhonduEntity2 = BenTestSecondCharacter.run(IMAGE_PATH2);
		
		// IEntity ball =
		IEntitySystem system = new EntitySystem();
		system.addEntities(rhonduEntity, rhonduEntity2);
		
		IPhysicsEngine physics = new PhysicsEngine(system);
		ImageView testSprite = createImage(rhonduEntity.getComponent(ImagePath.class),
				rhonduEntity.getComponent(Position.class));
		rhonduEntity.addComponent(new Collision(testSprite.getBoundsInParent(), null));
		entityToImage.put(rhonduEntity, testSprite);
		
		ImageView testSprite2 = createImage(rhonduEntity2.getComponent(ImagePath.class),
				rhonduEntity2.getComponent(Position.class));
		rhonduEntity2.addComponent(new Collision(testSprite2.getBoundsInParent(), null));
		entityToImage.put(rhonduEntity2, testSprite2);
		

		// sets the game's loop
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
				e -> this.step(SECOND_DELAY, system, physics));
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}

	private void step(double dt, IEntitySystem system, IPhysicsEngine physics) {
		physics.update(system, dt);
		drawEntities(system);
	}

	private void drawEntities(IEntitySystem s) {
		Collection<IEntity> list = s.getAllEntities();
		for (IEntity entity : list) {
			Position pos = entity.getComponent(Position.class);
			refreshDraw(entityToImage.get(entity), pos.getX(), pos.getY());
		}
	}

	private ImageView createImage(ImagePath path, Position pos) {
		File resource = new File(path.getImagePath());
		Image image = new Image(resource.toURI().toString());
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(100);
		imageView.setPreserveRatio(true);
		imageView.xProperty().bind(pos.xProperty());
		imageView.yProperty().bind(pos.yProperty());
		return imageView;
	}

	private void refreshDraw(ImageView img, double x, double y) {
		ROOT.getChildren().remove(img);
		ROOT.getChildren().add(img);
	}

	public static void main(String[] args) {
		launch(args);
	}

}
