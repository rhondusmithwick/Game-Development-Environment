package animation.colorchange;

import java.util.ArrayList;
import java.util.List;

/**
 * The class to hold a pixel's location.
 *
 * @author Rhondu Smithwick
 */
public class PixelLocation {
    private final int x;
    private final int y;

    /**
     * Sole constructor.
     *
     * @param x the x-value
     * @param y the y-value
     */
    public PixelLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get this pixel's immediate neighbors in the 8 directions.
     *
     * @return this pixel's neighbors
     */
    public List<PixelLocation> getNeighbors() {
        List<PixelLocation> neighbors = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    neighbors.add(new PixelLocation(x + i, y + j));
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
