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
 * A class to change the color of an image, with particular emphasis on background colors.
 * <p>
 * <p>
 * Given a point, it will take the color at the point and changeImage all places with that color to the provided new color.
 * </p>
 * <p>
 * The implementation stops when it hits a color that is different, so it is most useful for background colors.
 * </p>
 * <p>
 * This implementation uses a modified Breadth First Search to change the color of the pixels.
 * </p>
 *
 * @author Rhondu Smithwick
 */
public class ColorChanger {
    private final PixelReader reader;
    private final Color newColor;
    private final Color oldColor;
    private final WritableImage writableImage;
    private final PixelWriter writer;
    private final PixelLocation rootPixelLocation;
    private final boolean[][] seenMarker;

    /**
     * Sole constructor.
     *
     * @param image    the image to modify
     * @param x        the x-value of the pixel to start at (where oldColor will be gotten)
     * @param y        the y-value fo the pixel to start at (where oldColor will be gotten from)
     * @param newColor the color to change to
     */
    public ColorChanger(Image image, double x, double y, Color newColor) {
        this.newColor = newColor;
        this.writableImage = new WritableImage(image.getPixelReader(), (int) image.getWidth(), (int) image.getHeight());
        this.reader = writableImage.getPixelReader();
        this.writer = writableImage.getPixelWriter();
        this.rootPixelLocation = new PixelLocation((int) x, (int) y);
        this.oldColor = getColor(rootPixelLocation);
        this.seenMarker = new boolean[(int) writableImage.getWidth()][(int) writableImage.getHeight()];
    }

    /**
     * Change an image.
     * <p>
     * Modifies all occurrences of the original color at the root pixel to be the newColor. Stops when a color differnt than oldColor is reached
     * </p>
     * <p>
     * This implementation relies on a modified Breadth First Search.
     * </p>
     *
     * @return the changed image
     */
    public Image changeImage() {
        Queue<PixelLocation> queue = new LinkedList<>();
        queue.add(rootPixelLocation);
        while (!queue.isEmpty()) {
            PixelLocation current = queue.poll();
            setSeen(current);
            if (shouldChangeColor(current)) {
                changeToNewColor(current);
                Predicate<PixelLocation> isValidNeighbor = (p) -> (isValid(p) && !haveSeen(p));
                current.getNeighbors().stream().filter(isValidNeighbor).forEach(queue::add);
            }
        }
        return writableImage;
    }

    private void setSeen(PixelLocation pixelLocation) {
        seenMarker[pixelLocation.getX()][pixelLocation.getY()] = true;
    }

    private boolean isValid(PixelLocation pixelLocation) {
        int x = pixelLocation.getX();
        int y = pixelLocation.getY();
        return (x >= 0)
                && (x < writableImage.getWidth())
                && (y >= 0)
                && (y < writableImage.getHeight());
    }

    private boolean haveSeen(PixelLocation pixelLocation) {
        return seenMarker[pixelLocation.getX()][pixelLocation.getY()];
    }

    private Color getColor(PixelLocation pixelLocation) {
        return reader.getColor(pixelLocation.getX(), pixelLocation.getY());
    }

    private void changeToNewColor(PixelLocation pixelLocation) {
        writer.setColor(pixelLocation.getX(), pixelLocation.getY(), newColor);
    }

    private boolean shouldChangeColor(PixelLocation pixelLocation) {
        return getColor(pixelLocation).equals(oldColor);
    }

}
