package model.physics;

/**
 * Representation of a Vector Used for applying forces to Entities
 *
 * @author Tom Wu
 */
public class Vector {
	private double vx, vy;

	public Vector(double Jx, double Jy) {
		this.vx = Jx;
		this.vy = Jy;
	}

	public Vector(double magnitude, double direction, boolean flag) {
		this.vx = magnitude * Math.cos(Math.toRadians(direction));
		this.vy = magnitude * Math.sin(Math.toRadians(direction));
	}

	public double getXComponent() {
		return this.vx;
	}

	public double getYComponent() {
		return this.vy;
	}

	public double getDotProduct(Vector b) {
		return this.getXComponent() * b.getXComponent() + this.getYComponent() * b.getYComponent();
	}

	public Vector normalizePosition() {
		double length = Math.sqrt(Math.pow(this.getXComponent(), 2) + Math.pow(this.getYComponent(), 2));
		return new Vector(this.getXComponent() / length, this.getYComponent() / length);
	}

}
