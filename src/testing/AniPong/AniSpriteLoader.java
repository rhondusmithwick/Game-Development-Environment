package testing.AniPong;

import javafx.scene.image.ImageView;
import model.component.character.Score;
import model.component.character.UserControl;
import model.component.movement.Position;
import model.component.movement.Velocity;
import model.component.physics.Collision;
import model.component.physics.Mass;
import model.component.physics.RestitutionCoefficient;
import model.component.visual.Sprite;
import model.entity.Entity;
import api.IEntity;

/**
 * Created by Tom on 4/25/2016.
 */
public class AniSpriteLoader {

    public static IEntity createBall(String name, Position pos) {
        IEntity e = new Entity(name);
        Sprite disp = new Sprite("resources/testing/RhonduSmithwick.JPG", 100, 100);
        ImageView img = disp.getImageView();
        img.setScaleX(0.05);
        img.setScaleY(0.05);
        e.addComponents(pos, disp, new Velocity(100.0, 0.0),
                new Collision("ball"), new RestitutionCoefficient(1.0), new Mass(5));
        return e;
    }

    public static IEntity createPaddle(String name, Position pos) {
        IEntity e = new Entity(name);
        e.addComponents(pos, new Sprite(AniPong.PATH+"sprites/red_paddle.png"),
                new Collision("paddle"), new RestitutionCoefficient(1.0), new Mass(10000),
                new Score(0), new Velocity(0.0, 0.0));
        return e;
    }

    public static IEntity createPlatform(String name, Position pos) {
        IEntity e = new Entity(name);
        e.addComponents(pos, new Sprite("resources/testing/RhonduSmithwick.JPG", 638, 518),
                new Collision("platform"), new RestitutionCoefficient(1.0), new Mass(1000000));
        return e;
    }

}
