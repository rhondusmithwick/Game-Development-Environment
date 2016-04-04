package model.component.character;

import javafx.beans.property.SimpleDoubleProperty;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import api.IComponent;

public class Health implements IComponent{
	
	@XStreamAlias("health")
	private final SimpleDoubleProperty health;

	public Health(SimpleDoubleProperty health) {
		setHealth(health);
	}

	public SimpleDoubleProperty getHealth() {
		
	}
}
