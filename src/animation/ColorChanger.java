package animation;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import static javafx.scene.input.KeyCode.Y;


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

    public ColorChanger(Image image, double x, double y, Color newColor) {
        this.newColor = newColor;
        writableImage = new WritableImage(image.getPixelReader(), (int) image.getWidth(), (int) image.getHeight());
        this.reader = writableImage.getPixelReader();
        this.writer = writableImage.getPixelWriter();
        this.oldColor = getColor((int) x, (int) y);
        recurse((int) x, (int) y);
    }


    private void recurse(int x, int y) {
        if (!testPoint(x, y)) {
            return;
        }
        writer.setColor(x, y, newColor);
        for (int i = -1; i <= 1; i++ ) {
            for (int j = -1; j <= 1; j++) {
                if (i == j) continue;;
                recurse(x + i, y + j);
            }
        }
    }

    public WritableImage getImage() {
        return writableImage;
    }

    public Color getColor(int x, int y) {
        return reader.getColor(x, y);
    }

    private boolean testPoint(int x, int y) {
        if (x < 0 || x >= writableImage.getWidth() ||
                y < 0 || y >= writableImage.getHeight()) {
            return false;
        }
        Color currentColor = getColor(x, y);
        return currentColor.equals(oldColor);
    }

}
