package animation;

import api.IEntity;
import javafx.animation.Animation;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.component.visual.ImagePath;

import java.util.List;
import java.util.ResourceBundle;

public class Animator {
    public Animator() {
    }

    public Animation createAnimation(String move, IEntity entity) {
        ImagePath imagePath = entity.getComponent(ImagePath.class);
        ResourceBundle myResources = ResourceBundle.getBundle(imagePath.getSpriteProperties());
        ImageView imageView = imagePath.getImageView();
        Duration duration = Duration.millis(Double.parseDouble(myResources.getString(move + "duration")));
        int count = Integer.parseInt(myResources.getString(move + "count"));
        List<Double> xList = StringParser.convertStringToDoubleList(myResources.getString(move + "xList"));
        List<Double> yList = StringParser.convertStringToDoubleList(myResources.getString(move + "yList"));
        List<Double> widthList = StringParser.convertStringToDoubleList(move + "widthList");
        List<Double> heightList = StringParser.convertStringToDoubleList(move + "heightList");
        return new ComplexAnimation(imageView, duration, count, xList, yList, widthList, heightList);
    }

}
