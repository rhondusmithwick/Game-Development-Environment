package animation;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.Queue;


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
        Queue<Point2D> queue = new LinkedList<>();
        boolean[][] seenMarker = new boolean[(int) writableImage.getWidth()][(int) writableImage.getHeight()];
        queue.add(startPoint);
        while (!queue.isEmpty()) {
            Point2D current = queue.poll();
            int x = (int) current.getX();
            int y = (int) current.getY();
            seenMarker[x][y] = true;
            if (shouldChangeColor(x, y)) {
                writer.setColor(x, y, newColor);
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (i != 0 || j != 0) {
                            int newX = x + i;
                            int newY = y + j;
                            if (isValid(newX, newY) && !seenMarker[newX][newY]) {
                                queue.add(new Point2D(newX, newY));
                            }
                        }
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
