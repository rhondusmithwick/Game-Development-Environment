import api.ILevel
import model.component.audio.SoundEffect
import model.component.visual.AnimatedSprite

ILevel level = universe;
String keyStr = key;

AnimatedSprite animatedSprite = level.getEntitiesWithName("ryu").get(0);
SoundEffect soundfx = level.getEntitiesWithName("ryu").get(0);
switch (keyStr) {
    case "W":
        animatedSprite.createAndPlayAnimation("LeftPunch");
        soundfx.play();
        println("hi");
        break;
    case "S":
        animatedSprite.createAndPlayAnimation("RightPunch");
        soundfx.play();
        println("hi");
        break;
}


