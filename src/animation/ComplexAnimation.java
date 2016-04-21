package animation;

import java.util.List;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class ComplexAnimation extends Transition{
    private final ImageView imageView;
    private final int count;
    private final int columns;
    private final List<Double> offsetX;
    private final List<Double> offsetY;
    private final List<Double> width;
    private final List<Double> height;

    private int lastIndex;

    public ComplexAnimation(
            ImageView imageView, 
            Duration duration, 
            int count,   int columns,
            List<Double> offsetX, List<Double> offsetY,
            List<Double> width,   List<Double> height) {
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
            final double x = (index % columns) * width.get(index)  + offsetX.get(index);
            final double y = (index / columns) * height.get(index) + offsetY.get(index);
            imageView.setViewport(new Rectangle2D(x, y, width.get(index), height.get(index)));
            lastIndex = index;		
        }	

}
}
