package model.physics;

public class Impulse {
    private double Jx, Jy;

    public Impulse (double Jx, double Jy) {
        this.Jx = Jx;
        this.Jy = Jy;
    }

    public Impulse (double magnitude, double direction, boolean flag) {
        this.Jx = magnitude * Math.cos(Math.toRadians(direction));
        this.Jy = magnitude * Math.sin(Math.toRadians(direction));
    }

    public double getJx () {
        return this.Jx;
    }

    public double getJy () {
        return this.Jy;
    }

}
