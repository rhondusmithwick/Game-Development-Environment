package animation.animation;

import api.IEntity;
import javafx.animation.Animation;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.component.visual.ImagePath;

import java.util.List;
import java.util.ResourceBundle;

import static animation.utility.StringParser.convertStringToDoubleList;

public class Animator {
    public Animator() {


    }

    public Animation createAnimation(String move, IEntity entity) {
        ImagePath imagePath = entity.getComponent(ImagePath.class);
        ResourceBundle myResources = ResourceBundle.getBundle(imagePath.getSpriteProperties());
        ImageView imageView = imagePath.getImageView();
        Duration duration = Duration.millis(Double.parseDouble(myResources.getString(move + "duration")));
        int count = Integer.parseInt(myResources.getString(move + "count"));
        List<Double> xList = convertStringToDoubleList(myResources.getString(move + "xList"));
        List<Double> yList = convertStringToDoubleList(myResources.getString(move + "yList"));
        List<Double> widthList = convertStringToDoubleList(myResources.getString(move + "width"));
        List<Double> heightList = convertStringToDoubleList(myResources.getString(move + "height"));
        Animation animation = new CustomAnimation(imageView, duration, count, xList, yList, widthList, heightList);
        animation.setCycleCount(Animation.INDEFINITE);
        return animation;
    }

}
