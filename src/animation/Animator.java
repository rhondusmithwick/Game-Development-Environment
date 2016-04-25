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
        //imageView.setViewport(new Rectangle2D(Double.parseDouble(myResources.getString(move+"OffsetX")), Double.parseDouble(myResources.getString(move+"OffsetY")), imagePath.getImageWidth(), imagePath.getImageHeight()));

//        final Animation animation = new SpriteAnimation(
//                imageView,
//                Duration.millis(Double.parseDouble(myResources.getString(move + "Duration"))),
//                Integer.parseInt(myResources.getString(move + "Count")), Integer.parseInt(myResources.getString(move + "Columns")),
//                Double.parseDouble(myResources.getString(move + "OffsetX")), Double.parseDouble(myResources.getString(move + "OffsetY")),
//                Double.parseDouble(myResources.getString(move + "Width")), Double.parseDouble(myResources.getString(move + "Height"))
//        );
        
        StringParser stringParser = new StringParser();
        Animation animation = new ComplexAnimation(imageView, Duration.millis(Double.parseDouble(myResources.getString(move + "Duration"))), Integer.parseInt(myResources.getString(move + "Count")), stringParser.convertStringToDoubleList(myResources.getString(move + "X")), stringParser.convertStringToDoubleList(myResources.getString(move + "Y")), stringParser.convertStringToDoubleList(myResources.getString(move + "Width")), stringParser.convertStringToDoubleList(myResources.getString(move + "Height")));
        animation.setCycleCount(Animation.INDEFINITE);
        return animation;
    }

}
