package animation;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;


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
    private final Point2D startPoint;

    public ColorChanger(Image image, double x, double y, Color newColor) {
        this.newColor = newColor;
        writableImage = new WritableImage(image.getPixelReader(), (int) image.getWidth(), (int) image.getHeight());
        this.reader = writableImage.getPixelReader();
        this.writer = writableImage.getPixelWriter();
        this.oldColor = getColor((int) x, (int) y);
        startPoint = new Point2D(x, y);
    }

    public void change() {
        attemptRecurse((int) startPoint.getX(), (int) startPoint.getY());
    }

    private void attemptRecurse(int x, int y) {
        try {
            recurse(x, y);
        } catch (StackOverflowError e) {
            return;
        }
    }

    private void recurse(int x, int y) {
        if (shouldChangeColor(x, y)) {
            writer.setColor(x, y, newColor);
            sweep(x, y);
        }
    }

    private void sweep(int x, int y) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    int newX = x + i;
                    int newY = y + j;
                    if (isValid(newX, newY)) {
                        attemptRecurse(newX, newY);
                    }
                }
            }
        }
    }

    private boolean shouldChangeColor(int x, int y) {
        Color currentColor = getColor(x, y);
        return currentColor.equals(oldColor);
    }

    public WritableImage getImage() {
        return writableImage;
    }

    private Color getColor(int x, int y) {
        return reader.getColor(x, y);
    }

    private boolean isValid(int x, int y) {
        return (x >= 0)
                && (x < writableImage.getWidth())
                && (y >= 0)
                && (y < writableImage.getHeight());
    }
}
