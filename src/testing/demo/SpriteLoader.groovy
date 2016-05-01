package testing.demo

import api.IEntity
import model.component.audio.SoundEffect
import model.component.character.Score
import model.component.movement.Position
import model.component.movement.Velocity
import model.component.physics.Collision
import model.component.physics.Mass
import model.component.physics.RestitutionCoefficient
import model.component.visual.AnimatedSprite
import model.component.visual.Sprite
import model.entity.Entity
/**
 * Created by Tom on 4/25/2016.
 */
public class SpriteLoader {

    public static IEntity createBall(String name, Position pos) {
        IEntity e = new Entity(name);
        Sprite disp = new Sprite();
//        ImageView img = disp.getImageView();
        disp.setImageWidth(20);
        e.addComponents(pos, disp, new Velocity(20.0, 0.0), new Collision("ball"),
                new RestitutionCoefficient(1.0), new Mass(1));
        return e;
    }

    public static IEntity createPaddle(String name, Position pos) {
        IEntity e = new Entity(name);
        e.addComponents(pos, new Sprite(Pong.PATH+"sprites/red_paddle.png"),
                new Collision("paddle"), new RestitutionCoefficient(1.0), new Mass(20),
                new Score(0), new Velocity(0,0));
        return e;
    }

    public static IEntity createPlatform(String name, Position pos) {
        IEntity e = new Entity(name);
        e.addComponents(pos, new Sprite(),
                new Collision("platform"), new RestitutionCoefficient(1.1), new Mass(1000));
        return e;
    }

    public static IEntity createAnimatedSprite(String name, Position pos){
        IEntity e = new Entity(name);
//        Sprite sprite = new Sprite();
//        ImageView img = sprite.getImageView();
        AnimatedSprite animated = new AnimatedSprite();

        SoundEffect soundfx = new SoundEffect();
        e.addComponents(pos, animated, soundfx);
        return e;
    }

}
