package animation;

import api.IEntity;
import javafx.animation.Animation;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.component.visual.ImagePath;

import java.util.ResourceBundle;

public class Animator {
    public Animator() {


    }

    public Animation createAnimation(String move, IEntity entity) {
        ImagePath imagePath = entity.getComponent(ImagePath.class);
        ResourceBundle myResources = ResourceBundle.getBundle(imagePath.getSpriteProperties());
        ImageView imageView = imagePath.getImageView();
        StringParser stringParser = new StringParser();
        Animation animation = new ComplexAnimation(imageView, Duration.millis(Double.parseDouble(myResources.getString(move + "duration"))), Integer.parseInt(myResources.getString(move + "count")), stringParser.convertStringToDoubleList(myResources.getString(move + "xList")), stringParser.convertStringToDoubleList(myResources.getString(move + "yList")), stringParser.convertStringToDoubleList(myResources.getString(move + "width")), stringParser.convertStringToDoubleList(myResources.getString(move + "height")));
        animation.setCycleCount(Animation.INDEFINITE);
        return animation;
    }

}
