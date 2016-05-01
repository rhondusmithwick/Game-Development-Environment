package model.physics;

/**
 * Representation of a Vector Used for applying forces to Entities
 *
 * @author Tom Wu
 */
@Deprecated
public class Vector {
	private double x, y;

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector(double magnitude, double direction, boolean flag) {
		this.x = magnitude * Math.cos(Math.toRadians(direction));
		this.y = magnitude * Math.sin(Math.toRadians(direction));
	}

	public double getXComponent() {
		return this.x;
	}

	public double getYComponent() {
		return this.y;
	}

	public double getDotProduct(Vector v) {
		return this.getXComponent() * v.getXComponent() + this.getYComponent() * v.getYComponent();
	}

	public Vector normalize() {
		double length = Math.sqrt(Math.pow(this.getXComponent(), 2) + Math.pow(this.getYComponent(), 2));
		return this.scalarMultiply(1.0 / length);
	}

	public Vector scalarMultiply(double c) {
		return new Vector(this.getXComponent() * c, this.getYComponent() * c);
	}

	public Vector add(Vector v) {
		return new Vector(this.getXComponent() + v.getXComponent(), this.getYComponent() + v.getYComponent());
	}

	public Vector negate() {
		return this.scalarMultiply(-1);
	}

}
