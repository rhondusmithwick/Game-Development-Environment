package model.component.visual;

import java.util.ResourceBundle;
/**
 * This component contains the animated sprite
 * @author Melissa Zhang
 *
 */


public class SpriteAnimated extends Sprite{
	private ResourceBundle animationBundle;
	public SpriteAnimated(String bundle){
		setResourceBundle(bundle);
		//initialize animation container
	}
	public void setResourceBundle(String bundle) {
		animationBundle = ResourceBundle.getBundle(bundle);
	}
	public ResourceBundle getResourceBundle(){
		return animationBundle;
	}
}
