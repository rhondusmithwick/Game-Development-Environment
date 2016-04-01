package entitytesting;

/**
 * Created by rhondusmithwick on 3/30/16.
 *
 * @author Rhondu Smithwick
 */

public class Position implements Component {
    private Double x;
    private Double y;

    public Position() {
        setPosition(0, 0);
    }

    public Position(double x, double y) {
        setPosition(x, y);
    }

    public Position(String[] inputs) {
        this(Double.parseDouble(inputs[0]), Double.parseDouble(inputs[1]));
    }

    public void setPosition(double x, double y) {
        setX(x);
        setY(y);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", getX(), getY());
    }

    @Override
    public boolean unique() {
        return true;
    }

}
