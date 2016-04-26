package animation.config;

/**
 * Enum for our double constants.
 *
 * @author Rhondu Smithwick
 */
public enum DoubleConstants {
    WIDTH(800),
    HEIGHT(600),
    DURATION_MIN(100),
    DURATION_MAX(3000),
    DURATION_DEFAULT(1000),
    KEY_INPUT_SPEED(5);

    private final double content;

    DoubleConstants(double content) {
        this.content = content;
    }

    public double get() {
        return content;
    }
}
