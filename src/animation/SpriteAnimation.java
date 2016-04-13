package animation;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SpriteAnimation extends Transition {

    private final ImageView imageView;
    private final int count;
    private final int columns;
    private final double offsetX;
    private final double offsetY;
    private final double width;
    private final double height;

    private int lastIndex;

    public SpriteAnimation(
            ImageView imageView, 
            Duration duration, 
            int count,   int columns,
            double offsetX, double offsetY,
            double width,   double height) {
        this.imageView = imageView;
        this.count     = count;
        this.columns   = columns;
        this.offsetX   = offsetX;
        this.offsetY   = offsetY;
        this.width     = width;
        this.height    = height;
        setCycleDuration(duration);
        setInterpolator(Interpolator.LINEAR);
    }

    @Override
	protected void interpolate(double k) {
        final int index = Math.min((int) Math.floor(k * count), count - 1);
        if (index != lastIndex) {
            final double x = (index % columns) * width  + offsetX;
            final double y = (index / columns) * height + offsetY;
            imageView.setViewport(new Rectangle2D(x, y, width, height));
            lastIndex = index;
        }
    }
}