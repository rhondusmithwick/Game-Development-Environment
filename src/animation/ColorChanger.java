package animation;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
        this.oldColor = rootPixel.getColor();
        this.seenMarker = new boolean[(int) writableImage.getWidth()][(int) writableImage.getHeight()];
    }

    public void change() {
        Queue<Pixel> queue = new LinkedList<>();
        queue.add(rootPixel);
        while (!queue.isEmpty()) {
            Pixel current = queue.poll();
            current.setSeen();
            if (current.shouldChangeColor()) {
                current.changeToNewColor();
                Predicate<Pixel> validNeighbor = (p) -> (p.isValid() && !p.haveSeen());
                current.getNeighbors().stream()
                        .filter(validNeighbor)
                        .forEach(queue::add);
            }
        }
    }

    public WritableImage getImage() {
        return writableImage;
    }

    private class Pixel {
        private final int x;
        private final int y;

        private Pixel(int x, int y) {
            this.x = x;
            this.y = y;
        }

        private Color getColor() {
            return reader.getColor(x, y);
        }

        private void changeToNewColor() {
            writer.setColor(x, y, newColor);
        }

        private boolean isValid() {
            return (x >= 0)
                    && (x < writableImage.getWidth())
                    && (y >= 0)
                    && (y < writableImage.getHeight());
        }

        private boolean haveSeen() {
            return seenMarker[x][y];
        }

        private void setSeen() {
            seenMarker[x][y] = true;
        }

        private boolean shouldChangeColor() {
            return getColor().equals(oldColor);
        }

        private List<Pixel> getNeighbors() {
            List<Pixel> neighbors = new ArrayList<>();
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i != 0 || j != 0) {
                        neighbors.add(new Pixel(x + i, y + j));
                    }
                }
            }
            return neighbors;
        }
    }
}
