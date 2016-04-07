package model.component.movement;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import api.IComponent;

public class Speed implements IComponent{
	private SimpleDoubleProperty speed;

	public Speed(){
        speed = new SimpleDoubleProperty(this, "speed", 0);

	}

	public double getSpeed(){
		return speed.getValue();
	}

	public DoubleProperty speedProperty(){
		return speed;
	}
	public void setSpeed(double s){
		speed.setValue(s);
	}
}
