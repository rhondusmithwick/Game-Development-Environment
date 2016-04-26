package animation;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.List;

public class ComplexAnimation extends Transition {
    private final ImageView imageView;
    private final int numFrames;
    private final List<Double> xList;
    private final List<Double> yList;
    private final List<Double> widthList;
    private final List<Double> heightIndex;

    private int lastIndex;

    public ComplexAnimation(
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

    @Override
    protected void interpolate(double k) {
        final int index = Math.min((int) Math.floor(k * numFrames), numFrames - 1);
        if (index != lastIndex) {
            final double x = xList.get(index);
            final double y = yList.get(index);
            imageView.setViewport(new Rectangle2D(x, y, widthList.get(index), heightIndex.get(index)));
            lastIndex = index;
        }

    }
}
