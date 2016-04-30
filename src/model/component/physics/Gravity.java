package model.component.physics;

import api.IComponent;
import javafx.beans.property.SimpleObjectProperty;
import utility.TwoProperty;

import java.util.List;

/**
 * The gravity component.
 *
 * @author Roxanne Baker
 */
@SuppressWarnings("serial")
public class Gravity implements IComponent {

	private final TwoProperty<Double, Double> gravityProperty = new TwoProperty<>("gx", 0.0, "gy", 20.0);

	/**
	 * Empty constructor. Defaults to 20 down.
	 */
	public Gravity() {
	}

	/**
	 * Construct with an initial value.
	 *
	 * @param gravity
	 *            the initial value
	 */
	public Gravity(double gravity) {
		this(0.0, gravity);
	}

	public Gravity(double gx, double gy) {
		this.setGravityX(gx);
		this.setGravityY(gy);
	}

	/**
	 * Get the gravity property.
	 *
	 * @return the gravity property
	 */
	public SimpleObjectProperty<Double> gravityXProperty() {
		return gravityProperty.property1();
	}

	public SimpleObjectProperty<Double> gravityYProperty() {
		return gravityProperty.property2();
	}

	public double getGravityX() {
		return gravityXProperty().get();
	}

	public void setGravityX(double gx) {
		gravityXProperty().set(gx);
	}

	public double getGravityY() {
		return gravityYProperty().get();
	}

	public void setGravityY(double gy) {
		gravityYProperty().set(gy);
	}

	@Override
	public List<SimpleObjectProperty<?>> getProperties() {
		return gravityProperty.getProperties();
	}

	@Override
	public void update() {
		setGravityX(getGravityX());
		setGravityY(getGravityY());
	}
}
