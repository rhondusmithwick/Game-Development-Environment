package view;

import api.IEntity;
import view.utilities.SpriteUtilities;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Tom
 */

@SuppressWarnings("ALL")
public class ViewUtilities {

    private static final String SELECT_EFFECT = "-fx-effect: dropshadow(three-pass-box, rgba(22, 0, 255, 0.8), 10, 0, 0, 0)",
            NO_SELECT_EFFECT = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0), 0, 0, 0, 0)";
    private Set<IEntity> selectedSprites = new HashSet<>();

    public ViewUtilities () {
    }

    public Set<IEntity> getSelected () {
        return selectedSprites;
    }

    public void dehighlight (IEntity e) {
        selectedSprites.remove(e);
        SpriteUtilities.getImageView(e).setStyle(NO_SELECT_EFFECT);
    }

    public void highlight (IEntity e) {
        selectedSprites.add(e);
        SpriteUtilities.getImageView(e).setStyle(SELECT_EFFECT);
    }

    public void toggleHighlight (IEntity entity) {
        if (!selectedSprites.contains(entity)) {
            System.out.println("highlight");
            this.highlight(entity);
        } else {
            System.out.println("dehighlight");
            this.dehighlight(entity);
        }
    }

}
