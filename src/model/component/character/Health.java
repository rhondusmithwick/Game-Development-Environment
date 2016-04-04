package model.component.character;

import javafx.beans.property.SimpleDoubleProperty;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import api.IComponent;

public class Health implements IComponent{
	
	@XStreamAlias("health")
	private final SimpleDoubleProperty health = new SimpleDoubleProperty(this, "health", 0);;

	public Health(double health) {
		setHealth(health);
	}

	public void setHealth(double health) {
		this.health.set(health);
	}
	
	public double getHealth() {
		return health.get();
	}
	
	public void add(double dHealth) {
		setHealth(getHealth() + dHealth);
	}
}
