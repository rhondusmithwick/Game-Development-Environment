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
 * Created by rhondusmithwick on 4/21/16.
 *
 * @author Rhondu Smithwick
 */
public final class ColorChanger {

    private final PixelReader reader;
    private final Color newColor;
    private final Color oldColor;
    private final WritableImage writableImage;
    private final PixelWriter writer;
    private final Pixel rootPixel;
    private final boolean[][] seenMarker;

    public ColorChanger(Image image, double x, double y, Color newColor) {
        this.newColor = newColor;
        writableImage = new WritableImage(image.getPixelReader(), (int) image.getWidth(), (int) image.getHeight());
        this.reader = writableImage.getPixelReader();
        this.writer = writableImage.getPixelWriter();
        this.rootPixel = new Pixel((int) x, (int) y);
        this.oldColor = getColor(rootPixel);
        this.seenMarker = new boolean[(int) writableImage.getWidth()][(int) writableImage.getHeight()];
    }

    public void change() {
        Queue<Pixel> queue = new LinkedList<>();
        queue.add(rootPixel);
        while (!queue.isEmpty()) {
            Pixel current = queue.poll();
            setSeen(rootPixel);
            setSeen(current);
            if (shouldChangeColor(current)) {
                changeToNewColor(current);
                Predicate<Pixel> validNeighbor = (p) -> (isValid(p) && !haveSeen(p));
                current.getNeighbors().stream()
                        .filter(validNeighbor)
                        .forEach(queue::add);
            }
        }
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

    public WritableImage getImage() {
        return writableImage;
    }

}
