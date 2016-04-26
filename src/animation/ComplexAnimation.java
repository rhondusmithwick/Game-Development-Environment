package animation;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.List;
/**
 * This class is an Animation
 * @author Melissa Zhang
 *
 */
public class ComplexAnimation extends Transition {
    private final ImageView imageView;
    private final int count;
    private final List<Double> offsetX;
    private final List<Double> offsetY;
    private final List<Double> width;
    private final List<Double> height;

    private int lastIndex;

    public ComplexAnimation(
            ImageView imageView,
            Duration duration,
            int count,
            List<Double> offsetX, List<Double> offsetY,
            List<Double> width, List<Double> height) {
        this.imageView = imageView;
        this.count = count;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;
        setCycleDuration(duration);
        setInterpolator(Interpolator.LINEAR);
    }

    @Override
    protected void interpolate(double k) {
        final int index = Math.min((int) Math.floor(k * count), count - 1);
        if (index != lastIndex) {
            final double x = offsetX.get(index);
            final double y = offsetY.get(index);
            imageView.setViewport(new Rectangle2D(x, y, width.get(index), height.get(index)));
            lastIndex = index;
        }

    }
}
