package animation.animation;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.List;

import static animation.utility.StringParser.convertStringToDoubleList;

/**
 * {@inheritDoc}
 * This class defines a custom animation with a number of frames.
 * <p>
 * Credits: http://blog.netopyr.com/2012/03/09/creating-a-sprite-animation-with-javafx/
 * </p>
 *
 * @author Melissa Zhang, Rhondu Smithwick
 */
public class CustomAnimation extends Transition {
    private final ImageView imageView;
    private final int numFrames;
    private final List<Double> xList;
    private final List<Double> yList;
    private final List<Double> widthList;
    private final List<Double> heightIndex;
    private int lastIndex;

    /**
     * Sole constructor
     *
     * @param imageView   the imageView
     * @param duration    the duration of the animation
     * @param numFrames   te number of frames
     * @param xList       the x-values
     * @param yList       the y-values
     * @param widthList   the widths
     * @param heightIndex the heights
     */
    public CustomAnimation(
            ImageView imageView,
            Duration duration,
            int numFrames,
            List<Double> xList, List<Double> yList,
            List<Double> widthList, List<Double> heightIndex) {
        this.imageView = imageView;
        this.numFrames = numFrames;
        this.xList = xList;
        this.yList = yList;
        this.widthList = widthList;
        this.heightIndex = heightIndex;
        setCycleDuration(duration);
        setInterpolator(Interpolator.LINEAR);
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
    public static CustomAnimation reateAnimationFromStrings(ImageView imageView,
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

    /**
     * {@inheritDoc}
     *
     * @param frac defines current position in animation
     */
    @Override
    protected void interpolate(double frac) {
        final int index = Math.min((int) Math.floor(frac * numFrames), numFrames - 1);
        if (index != lastIndex) {
            final double x = xList.get(index);
            final double y = yList.get(index);
            imageView.setViewport(new Rectangle2D(x, y, widthList.get(index), heightIndex.get(index)));
            lastIndex = index;
        }
    }

}
