package animation.animation;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.List;
import java.util.ResourceBundle;

import static animation.utility.StringParser.convertStringToDoubleList;
import static java.util.ResourceBundle.getBundle;

/**
 * Factory for animations.
 *
 * @author Rhondu Smithwick
 */
public class AnimationFactory {

    private AnimationFactory() {
    }

    /**
     * Create an animation from a bundle.
     *
     * @param imageView  the imageView
     * @param bundlePath the bundle's filePath
     * @param move       the move
     * @return the animation
     */
    public static Animation createAnimationFromBundle(ImageView imageView, String bundlePath, String move) {
        ResourceBundle bundle = getBundle(bundlePath);
        String duration = bundle.getString(move + "duration");
        String numFrames = bundle.getString(move + "numFrames");
        String xList = bundle.getString(move + "xList");
        String yList = bundle.getString(move + "yList");
        String widthList = bundle.getString(move + "widthList");
        String heightList = bundle.getString(move + "heightList");
        return createAnimationFromStrings(imageView, duration, numFrames, xList, yList, widthList, heightList);
    }

    /**
     * Create an animation from strings.
     * <p>
     * ALL LISTS IN FORM [x, y, z, ., ., .,]
     * </p>
     *
     * @param imageView        the imageView
     * @param durationString   string representing the duration
     * @param numFramesString  string representing the number of frames
     * @param xListString      string representing the different x-values
     * @param yListString      string representing the different y-values
     * @param widthListString  string representing the different widths
     * @param heightListString string representing the different heights
     * @return the Complex Animation
     */
    public static Animation createAnimationFromStrings(ImageView imageView,
                                                String durationString, String numFramesString,
                                                String xListString, String yListString,
                                                String widthListString, String heightListString) {
        Duration duration = Duration.millis(Double.parseDouble(durationString));
        int numFrames = Integer.parseInt(numFramesString);
        List<Double> xList = convertStringToDoubleList(xListString);
        List<Double> yList = convertStringToDoubleList(yListString);
        List<Double> widthList = convertStringToDoubleList(widthListString);
        List<Double> heightList = convertStringToDoubleList(heightListString);
        return new CustomAnimation(imageView, duration, numFrames, xList, yList, widthList, heightList);
    }


}
