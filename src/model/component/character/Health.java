package model.component.character;

import model.component.base.Unit;
import api.IComponent;

/***
 * Created by Anirudh Jonnavithula on 04/03/16
 * 
 * @author ajonnav
 *
 */
public class Health extends Unit<Double> implements IComponent{

	public Health() {
		super(0.0);
	}
	
	public Health(double health) {
		super(health);
	}

	public void setHealth(double health) {
		setValue1(health);
	}
	
	public double getHealth() {
		return getValue1();
	}
	
	public void add(double dHealth) {
		setValue1(getValue1() + dHealth);
	}
}
