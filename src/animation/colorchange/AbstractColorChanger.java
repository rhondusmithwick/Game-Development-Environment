package animation.colorchange;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * A class to change the color of an image, with particular emphasis on background colors.
 *
 * @author Rhondu Smithwick
 */
public abstract class AbstractColorChanger {
    private final PixelReader reader;
    private final Color newColor;
    private final Color oldColor;
    private final WritableImage writableImage;
    private final PixelWriter writer;
    private final PixelLocation rootPixelLocation;

    /**
     * Sole constructor.
     *
     * @param image    the image to modify
     * @param x        the x-value of the pixel to start at (where oldColor will be gotten)
     * @param y        the y-value fo the pixel to start at (where oldColor will be gotten from)
     * @param newColor the color to change to
     */
    public AbstractColorChanger(Image image, double x, double y, Color newColor) {
        this.newColor = newColor;
        this.writableImage = new WritableImage(image.getPixelReader(), (int) image.getWidth(), (int) image.getHeight());
        this.reader = writableImage.getPixelReader();
        this.writer = writableImage.getPixelWriter();
        this.rootPixelLocation = new PixelLocation((int) x, (int) y);
        this.oldColor = getColor(rootPixelLocation);
    }

    /**
     * Change this image.
     *
     * @return the changed image
     */
    public abstract Image changeImage();

    /**
     * Check if you should change the pixel at this pixelLocation.
     *
     * @param pixelLocation the pixelLocation to check
     * @return true if should change color at this pixel location
     */
    protected abstract boolean shouldChangeColor(PixelLocation pixelLocation);


    /**
     * Check whether a pixelLocation is valid, that is, within the image.
     *
     * @param pixelLocation the pixel location to check
     * @return true if the pixelLocation is within the image
     */
    protected boolean isValid(PixelLocation pixelLocation) {
        int x = pixelLocation.getX();
        int y = pixelLocation.getY();
        return (x >= 0)
                && (x < writableImage.getWidth())
                && (y >= 0)
                && (y < writableImage.getHeight());
    }

    /**
     * Get the color at this pixelLocation.
     *
     * @param pixelLocation the pixelLocation
     * @return the color at specified pixelLocation
     */
    protected Color getColor(PixelLocation pixelLocation) {
        return reader.getColor(pixelLocation.getX(), pixelLocation.getY());
    }

    /**
     * Change this pixelLocation to the new Color.
     *
     * @param pixelLocation the pixelLocation to change
     */
    protected void changeToNewColor(PixelLocation pixelLocation) {
        writer.setColor(pixelLocation.getX(), pixelLocation.getY(), newColor);
    }

    protected PixelLocation getRootPixelLocation() {
        return rootPixelLocation;
    }

    protected WritableImage getWritableImage() {
        return writableImage;
    }

    protected Color getOldColor() {
        return oldColor;
    }
}
