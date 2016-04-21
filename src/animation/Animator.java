package animation;

import java.util.ResourceBundle;

import api.IEntity;
import model.component.visual.ImagePath;
import javafx.animation.Animation;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Animator {
	public Animator(){

		
	}
	public Animation createAnimation(String move, IEntity entity){
		ImagePath imagePath = entity.getComponent(ImagePath.class);
		ResourceBundle myResources = ResourceBundle.getBundle(imagePath.getSpriteProperties());
        ImageView imageView = imagePath.getImageView();
        //imageView.setViewport(new Rectangle2D(Double.parseDouble(myResources.getString(move+"OffsetX")), Double.parseDouble(myResources.getString(move+"OffsetY")), imagePath.getImageWidth(), imagePath.getImageHeight()));

        final Animation animation = new SpriteAnimation(
                imageView,
                Duration.millis(Double.parseDouble(myResources.getString(move+"Duration"))),
                Integer.parseInt(myResources.getString(move+"Count")), Integer.parseInt(myResources.getString(move+"Columns")),
                Double.parseDouble(myResources.getString(move+"OffsetX")), Double.parseDouble(myResources.getString(move+"OffsetY")),
                Double.parseDouble(myResources.getString(move +"Width")), Double.parseDouble(myResources.getString(move +"Height"))
        );
		animation.setCycleCount(Animation.INDEFINITE);
		return animation;
	}

}
