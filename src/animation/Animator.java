package animation;

import java.io.File;
import java.util.ResourceBundle;

import api.IEntity;
import model.component.visual.ImagePath;
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Animator {
	private ImageView imageView;
	public Animator(){

		
	}
	public Animation createAnimation(String move, IEntity entity){
		ImagePath imagePath = entity.getComponent(ImagePath.class);
		ResourceBundle myResources = ResourceBundle.getBundle(imagePath.getSpriteProperties());
        imageView = new ImageView(new Image(new File(imagePath.getSpriteSheet()).toURI().toString()));
        imageView.setViewport(new Rectangle2D(Double.parseDouble(myResources.getString(move+"OffsetX")), Double.parseDouble(myResources.getString(move+"OffsetY")), imagePath.getImageWidth(), imagePath.getImageHeight()));

        final Animation animation = new SpriteAnimation(
                imageView,
                Duration.millis(imagePath.getFrameDuration()),
                Integer.parseInt(myResources.getString(move+"Count")), Integer.parseInt(myResources.getString(move+"Columns")),
                Double.parseDouble(myResources.getString(move+"OffsetX")), Double.parseDouble(myResources.getString(move+"OffsetY")),
                imagePath.getImageWidth(), imagePath.getImageHeight()
        );
		return animation;
	}
	public Node getImageView(IEntity entity) {
		// TODO Auto-generated method stub
		return imageView;
	}
}
