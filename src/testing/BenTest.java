package testing;

import api.IEntity;
import api.ILevel;
import api.IPhysicsEngine;
import javafx.animation.Animation;
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
import model.component.character.Health;
import model.component.character.Score;
import model.component.movement.Position;
import model.component.movement.Velocity;
import model.component.visual.Sprite;
import model.entity.Entity;
import model.entity.Level;
import model.physics.PhysicsEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BenTest extends Application {
    private static ImageView testSprite; // BAD PRACTICE: USE final WHENEVER
    // POSSIBLE, DON'T BE LIKE ME!
    private static IPhysicsEngine physics; // TODO: remove these things
    private Group root;

    public static void main (String[] args) {
        launch(args);
    }

    @Override
    public void start (Stage s) {
        root = new Group();
        BorderPane splash = new BorderPane();
        // splash.setCenter(root);
        splash.getChildren().add(root);
        // splash.setCenter(root);
        Scene scene = new Scene(splash, 500, 500);
        s.setScene(scene);
        s.show();

        List<IEntity> list = new ArrayList<>();
        IEntity character = new Entity();
        character.forceAddComponent(new Health(100), true);
        character.forceAddComponent(new Score(100), true);
        Position pos = new Position(250.0, 250.0);
        character.forceAddComponent(pos, true);
        character.forceAddComponent(new Sprite(), true);
        character.forceAddComponent(new Velocity(20.0, 50.0), true);
        character.removeComponents(Score.class);
        list.add(character);

        ILevel system = new Level();
        system.addEntities(list);
        // TODO: don't lazy-initialize!
        physics = new PhysicsEngine();

        // TODO: seriously, don't lazy-initialize
        testSprite = createImage(character.getComponent(Sprite.class), character.getComponent(Position.class));

        int MILLISECOND_DELAY = 10;
        double SECOND_DELAY = 0.01;
        // sets the game's loop
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> this.step(SECOND_DELAY, list, system));
        Timeline animation = new Timeline();
        animation.setCycleCount(Animation.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();

    }

    // private void test(IPhysicsEngine physics, IEntitySystem system, Position
    // pos) {
    // physics.update(system, 1);
    // System.out.println(pos);
    // }

    private void step (double dt, List<IEntity> list, ILevel system) {
        physics.update(system, dt);
        System.out.println("dt=" + dt);
        drawEntities(list);
    }

    private void drawEntities (List<IEntity> list) {
        for (IEntity entity : list) {
            Position pos = entity.getComponent(Position.class);
            // ImagePath imagePath = entity.getComponent(ImagePath.class);
            // ImageView image = createImage(imagePath, pos);
            // testSprite.setTranslateX(pos.getX());
            // testSprite.setTranslateY(pos.getY());
            // testSprite.relocate(pos.getX(), pos.getY());
            refreshDraw(testSprite, pos.getX(), pos.getY());
        }
    }

    private ImageView createImage (Sprite path, Position pos) {
        File resource = new File(path.getImagePath());
        Image image = new Image(resource.toURI().toString());
        ImageView imageView = new ImageView(image);
        // imageView.setTranslateX(pos.getX());
        // imageView.setTranslateY(pos.getY());
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(true);
        imageView.xProperty().bind(pos.xProperty());
        imageView.yProperty().bind(pos.yProperty());
        return imageView;
    }

    // TODO: for BenTest only, remove asap
    private void refreshDraw (ImageView img, double x, double y) {
        root.getChildren().clear();
        System.out.println(x + " " + y);
        root.getChildren().add(img);
    }

}
