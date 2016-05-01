package view.utilities;

import api.IEntity;
import javafx.scene.image.ImageView;
import model.component.visual.AnimatedSprite;
import model.component.visual.Sprite;

/**
 * Used for handling inheritance in the Sprites.
 *
 * @author Rhondu Smithwick
 */
public class SpriteUtilities {
    private SpriteUtilities () {
    }

    public static <T extends Sprite> T getSpriteComponent (IEntity entity, Class<T> spriteClass) {
        if (entity.hasComponent(AnimatedSprite.class)) {
            return spriteClass.cast(entity.getComponent(AnimatedSprite.class));
        }
        return entity.getComponent(spriteClass);
    }

    public static ImageView getImageView (IEntity entity) {
        return getSpriteComponent(entity, Sprite.class).getImageView();
    }
}
