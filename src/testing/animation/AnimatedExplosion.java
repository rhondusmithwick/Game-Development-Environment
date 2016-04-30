package testing.animation;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

@Deprecated
public class AnimatedExplosion extends ImageView {
    private Rectangle2D[] frames;
    private int numFrames;
    private static final Duration FRAME_DURATION = Duration.seconds(0.1);

    public AnimatedExplosion (Image explosionImage, int numFrames) {
        super(explosionImage);
        this.numFrames = numFrames;

        double width = explosionImage.getWidth() / numFrames;
        double height = explosionImage.getHeight();
        frames = new Rectangle2D[numFrames];
        for (int i = 0; i < numFrames; i++) {
            frames[i] = new Rectangle2D(i * width, 0, width, height);
        }
        setImage(explosionImage);
        setViewport(frames[0]);
    }

    public void explode () {
        SimpleIntegerProperty frameIndex = new SimpleIntegerProperty(0);
        Timeline timeline = new Timeline(new KeyFrame(FRAME_DURATION, event -> {
            frameIndex.set((frameIndex.get() + 1) % numFrames);
            setViewport(frames[frameIndex.get()]);
        }));
        timeline.setCycleCount(numFrames);
        timeline.play();
    }
}
