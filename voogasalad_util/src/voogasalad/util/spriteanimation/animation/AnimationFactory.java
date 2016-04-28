package voogasalad.util.spriteanimation.animation;

import javafx.animation.Animation;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.stream.Collector;

import static java.util.ResourceBundle.getBundle;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static voogasalad.util.spriteanimation.utility.StringParser.convertStringToDoubleList;

/**
 * Factory for animations.
 *
 * @author Rhondu Smithwick, Melissa Zhang
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
        Map<String, String> stringMap = getStringMap(bundlePath, move);
        return createAnimationFromStrings(imageView, stringMap.get("duration"), stringMap.get("numFrames"),
                stringMap.get("xList"), stringMap.get("yList"),
                stringMap.get("widthList"), stringMap.get("heightList"));
    }

    private static Map<String, String> getStringMap(String bundlePath, String move) {
        List<String> keys = Arrays.asList("duration", "numFrames", "xList", "yList", "widthList", "heightList");
        ResourceBundle bundle = getBundle(bundlePath);
        Function<String, String> getValueFromBundle = (s -> bundle.getString(move + s));
        Collector<String, ?, Map<String, String>> collector = toMap(identity(), getValueFromBundle);
        return keys.stream().collect(collector);
    }

    /**
     * Returns an animation based on a map of strings generated froma  properties file.
     *
     * @param imageView     te imageView to act on
     * @param animationName the name of the animation
     * @param map           the map
     * @return the animation
     */
    public static Animation createAnimationFromMap(ImageView imageView, String animationName, Map<String, String> map) {
        return createAnimationFromStrings(imageView, map.get(animationName + "duration"), map.get(animationName + "numFrames"),
                map.get(animationName + "xList"), map.get(animationName + "yList"),
                map.get(animationName + "widthList"), map.get(animationName + "heightList"));
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
