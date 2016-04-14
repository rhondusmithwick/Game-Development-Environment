package testing;

import api.IEntity;
import api.IEventListener;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.component.movement.Velocity;
import model.entity.Entity;
import model.events.EventSystem;
import model.events.MoveEntityLeft;

public class TestEvents extends Application {
	private Group root = new Group();
	private EventSystem eSystem = new EventSystem();

	public static void main(String[] args) {
		launch(args);
	}

	public void test(Node n) {
		IEntity e = new Entity();
		e.forceAddComponent(new Velocity(), true);
		System.out.println(e.getComponent(Velocity.class)); // before 'a' key is
															// pressed

		IEventListener eListener = new MoveEntityLeft(e);
		eSystem.registerTrigger(new KeyEvent(n, n, KeyEvent.ANY, "a", "a", KeyCode.A, false, false, false, false),
				eListener);
	}

	@Override
	public void start(Stage s) {
		// boiler plate
		BorderPane splash = new BorderPane();
		// splash.setCenter(root);
		splash.getChildren().add(root);
		// splash.setCenter(root);
		Scene scene = new Scene(splash, 500, 500);
		s.setScene(scene);
		s.show();

		// magic happens in here
		this.test(splash);

		// more boiler-plate
		int MILLISECOND_DELAY = 10;
		double SECOND_DELAY = 0.01;
		// sets the game's loop
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> this.step(SECOND_DELAY));
		Timeline animation = new Timeline();
		animation.setCycleCount(Animation.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}

	private void step(double dt) {
		// System.out.println("x");
	}

}
