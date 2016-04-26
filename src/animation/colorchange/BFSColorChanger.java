package animation.colorchange;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Predicate;

/**
 * {@inheritDoc}
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
public class BFSColorChanger extends AbstractColorChanger {
    private final boolean[][] seenMarker;

    /**
     * Sole constructor.
     *
     * @param image    the image to modify
     * @param x        the x-value of the pixel to start at (where oldColor will be gotten)
     * @param y        the y-value fo the pixel to start at (where oldColor will be gotten from)
     * @param newColor the color to change to
     */
    public BFSColorChanger(Image image, double x, double y, Color newColor) {
        super(image, x, y, newColor);
        int xMax = (int) getWritableImage().getWidth();
        int yMax = (int) getWritableImage().getHeight();
        this.seenMarker = new boolean[xMax][yMax];
    }

    /**
     * {@inheritDoc}
     * <p>
     * Modifies all occurrences of the original color at the root pixel to be the newColor. Stops when a color differnt than oldColor is reached
     * </p>
     * <p>
     * This implementation relies on a modified Breadth First Search.
     * </p>
     *
     * @return the changed image
     */
    @Override
    public Image changeImage() {
        Queue<PixelLocation> queue = new LinkedList<>();
        queue.add(getRootPixelLocation());
        while (!queue.isEmpty()) {
            PixelLocation current = queue.poll();
            setSeen(current);
            if (shouldChangeColor(current)) {
                changeToNewColor(current);
                Predicate<PixelLocation> isValidNeighbor = (p) -> (isValid(p) && !haveSeen(p));
                current.getNeighbors().stream().filter(isValidNeighbor).forEach(queue::add);
            }
        }
        return getWritableImage();
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation returns true if the pixel is equal to the oldColor.
     * </p>
     *
     * @param pixelLocation the pixelLocation to check
     * @return true if should change color at this pixel location
     */
    @Override
    protected boolean shouldChangeColor(PixelLocation pixelLocation) {
        return getColor(pixelLocation).equals(getOldColor());
    }

    private void setSeen(PixelLocation pixelLocation) {
        seenMarker[pixelLocation.getX()][pixelLocation.getY()] = true;
    }

    private boolean haveSeen(PixelLocation pixelLocation) {
        return seenMarker[pixelLocation.getX()][pixelLocation.getY()];
    }

}
