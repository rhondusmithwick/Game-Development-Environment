package remote_client;

import java.io.File;
import java.net.URI;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

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

public class BasicRMIClient extends Application{
	private final ScriptEngine engine = new ScriptEngineManager().getEngineByName("groovy");
	private static final String IMAGE_PATH = "resources/RhonduSmithwick.JPG";
	private static ImageView testSprite; 
	private static IPhysicsEngine physics; 
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
		
		try { 
			System.setSecurityManager(new RMISecurityManager());
			EntitySystem universe = (EntitySystem) Naming.lookup("rmi://localhost/entitysystem_server");
			universe.addEntities(list);
			physics = new PhysicsEngine();
			testSprite = createImage(character.getComponent(ImagePath.class), character.getComponent(Position.class));

			int MILLISECOND_DELAY = 10;
			double SECOND_DELAY = 0.01;
			KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> this.step(SECOND_DELAY, list, universe));
			Timeline animation = new Timeline();
			animation.setCycleCount(Timeline.INDEFINITE);
			animation.getKeyFrames().add(frame);
			animation.play();
		} catch (Exception e) { 
			System.out.println("unable to access server");
		}
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

	private void refreshDraw(ImageView img, double x, double y) {
		root.getChildren().clear();
		System.out.println(x + " " + y);
		root.getChildren().add(img);
	}
	
	public static void main(String[] args) { 
		launch(args);
	}

}
