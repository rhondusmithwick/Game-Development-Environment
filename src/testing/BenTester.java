package testing;

import api.IComponent;
import api.IEntity;
import api.IEntitySystem;
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
import model.entity.EntitySystem;
import model.physics.PhysicsEngine;

import java.io.File;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BenTester extends Application {

    private static final int MILLISECOND_DELAY = 10;
    private static final double SECOND_DELAY = 0.01;
    private static final String IMAGE_PATH = "resources/RhonduSmithwick.JPG";

    private static ImageView testSprite; // BAD PRACTICE: USE final WHENEVER
    // POSSIBLE, DON'T BE LIKE ME!
    private static IPhysicsEngine physics; // TODO: remove these things

    private final Vooga vooga;
    private final Stage stage;

    public BenTester() {
        stage = new Stage();
        vooga = new Vooga(stage);
    }

    public void start(Stage s) {
        Scene scene = vooga.init();
        s.setScene(scene);
        s.show();
        List<IEntity> spriteList = buildSprites();

        IEntitySystem system = new EntitySystem();
        system.addEntities(spriteList);
        // TODO: don't lazy-initialize!
        physics = new PhysicsEngine(system);

        // TODO: seriously, don't lazy-initialize
        IEntity character = spriteList.get(0);
        ImagePath imagePath = character.getComponent(ImagePath.class);
        Position position = character.getComponent(Position.class);
        testSprite = createImage(imagePath, position);
        vooga.draw(testSprite);

        // sets the game's loop
        Timeline animation = buildLoop(system);
        animation.play();

    }

    private void step(double dt, IEntitySystem system) {
        physics.update(system, dt);
    }

    private List<IEntity> buildSprites() {
        IEntity character = new Entity(0);
        List<IComponent> components = Arrays.asList(new Health(100.0),
                new Score(100), new Position(250.0, 250.0),
                new ImagePath(IMAGE_PATH), new Velocity(10.0, 10.0));
        character.forceAddComponents(components, true);
        return Collections.singletonList(character);
    }

    private ImageView createImage(ImagePath path, Position pos) {
        URI resource = new File(path.getImagePath()).toURI();
        Image image = new Image(resource.toString());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(true);
        imageView.translateXProperty().bind(pos.xProperty());
        imageView.translateYProperty().bind(pos.yProperty());
        return imageView;
    }

    public static void main(String[] args) {
        launch(args);
    }

    private Timeline buildLoop(IEntitySystem system) {
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY, system));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        return animation;
    }
}
