package animation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rhondusmithwick on 4/22/16.
 *
 * @author Rhondu Smithwick
 */
public class Pixel {
    private final int x;
    private final int y;

    public Pixel(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public List<Pixel> getNeighbors() {
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
