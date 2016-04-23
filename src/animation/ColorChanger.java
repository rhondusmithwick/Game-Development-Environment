package animation;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Predicate;

/**
 * A class to changeImage the color of an image, with particular emphasis on background colors.
 * <p>
 * <p>Given a point, it will take the color at the point and changeImage all places with that color to the provided new color.</p>
 * <p>The implementation stops when it hits a color that is different, so it is most useful for background colors.</p>
 * <p>This implementation uses a Breadth First Search to change the color of the pixels.</p>
 *
 * @author Rhondu Smithwick
 */
public class ColorChanger {
    private final PixelReader reader;
    private final Color newColor;
    private final Color oldColor;
    private final WritableImage writableImage;
    private final PixelWriter writer;
    private final Pixel rootPixel;
    private final boolean[][] seenMarker;

    public ColorChanger(Image image, double x, double y, Color newColor) {
        this.newColor = newColor;
        this.writableImage = new WritableImage(image.getPixelReader(), (int) image.getWidth(), (int) image.getHeight());
        this.reader = writableImage.getPixelReader();
        this.writer = writableImage.getPixelWriter();
        this.rootPixel = new Pixel((int) x, (int) y);
        this.oldColor = getColor(rootPixel);
        this.seenMarker = new boolean[(int) writableImage.getWidth()][(int) writableImage.getHeight()];
    }

    public Image changeImage() {
        Queue<Pixel> queue = new LinkedList<>();
        queue.add(rootPixel);
        while (!queue.isEmpty()) {
            Pixel current = queue.poll();
            setSeen(current);
            if (shouldChangeColor(current)) {
                changeToNewColor(current);
                Predicate<Pixel> isValidNeighbor = (p) -> (isValid(p) && !haveSeen(p));
                current.getNeighbors().stream().filter(isValidNeighbor).forEach(queue::add);
            }
        }
        return writableImage;
    }

    private void setSeen(Pixel pixel) {
        seenMarker[pixel.getX()][pixel.getY()] = true;
    }

    private boolean isValid(Pixel pixel) {
        int x = pixel.getX();
        int y = pixel.getY();
        return (x >= 0)
                && (x < writableImage.getWidth())
                && (y >= 0)
                && (y < writableImage.getHeight());
    }

    private boolean haveSeen(Pixel pixel) {
        return seenMarker[pixel.getX()][pixel.getY()];
    }

    private Color getColor(Pixel pixel) {
        return reader.getColor(pixel.getX(), pixel.getY());
    }

    private void changeToNewColor(Pixel pixel) {
        writer.setColor(pixel.getX(), pixel.getY(), newColor);
    }

    private boolean shouldChangeColor(Pixel pixel) {
        Color pixelColor = getColor(pixel);
        return pixelColor.equals(oldColor);
    }

}
