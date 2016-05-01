package view;

import api.IEntity;
import javafx.scene.image.ImageView;
import model.component.visual.AnimatedSprite;
import model.component.visual.Sprite;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Tom
 *
 */

public class ViewUtilities {

	private static final String SELECT_EFFECT = "-fx-effect: dropshadow(three-pass-box, rgba(22, 0, 255, 0.8), 10, 0, 0, 0)",
			NO_SELECT_EFFECT = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0), 0, 0, 0, 0)";
	private Set<IEntity> selectedSprites = new HashSet<>();

	public ViewUtilities() {
		
	}

	public Set<IEntity> getSelected(){
		return selectedSprites;
	}
	
	private ImageView getImageView(IEntity e) {
		if (e.hasComponent(AnimatedSprite.class)) {
			return e.getComponent(AnimatedSprite.class).getImageView();
		}
		return e.getComponent(Sprite.class).getImageView();
	}

	public void dehighlight(IEntity e) {
		selectedSprites.remove(e); 
		getImageView(e).setStyle(NO_SELECT_EFFECT);
	}

	public void highlight(IEntity e) {
		selectedSprites.add(e);
		getImageView(e).setStyle(SELECT_EFFECT);
	}

	public void toggleHighlight(IEntity entity) {
		if (!selectedSprites.contains(entity)) {
			System.out.println("highlight");
			this.highlight(entity);
		} else {
			System.out.println("dehighlight");
			this.dehighlight(entity);
		}
	}

}