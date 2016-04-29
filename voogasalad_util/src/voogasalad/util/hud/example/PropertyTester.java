package voogasalad.util.hud.example;

import java.util.Observable;
import java.util.Observer;

import voogasalad.util.hud.source.Property;

/**
 * 
 * Sample file that shows how Property is observable
 * and *bindable*, if you desire to use the bindability
 * in your project.
 * 
 * @author bobby
 *
 */


public class PropertyTester implements Observer{
	
	Property value1;
	Property value2;
	Property value3;
	
	public PropertyTester(Property v1, Property v2, Property v3) {
		value1 = v1;
		value2 = v2;
		value3 = v3;
		value1.addObserver(this);
		value2.addObserver(this);
		value3.addObserver(this);
	}
	

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("UPDATED: " + arg);
	}

	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(value1);
		sb.append(", ");
		sb.append(value2);
		sb.append(", ");
		sb.append(value3);
		return sb.toString();
	}
	
	public static void main(String[] args) {
		Property p1 = new Property(1.5, "Health");
		Property p2 = new Property(p1);
		Property p3 = new Property (0.001, "Mage");
		
		PropertyTester c = new PropertyTester(p1, p2, p3);
		System.out.println(c.toString());
		c.value1.setValue(5.111111);
		System.out.println(c.toString());
		p2.bind(p3);
		System.out.println(c.toString());
		p1.setValue(0.333);
		System.out.println(c.toString());
		p2.setValue(0.343);
		System.out.println(c.toString());
		p3.setValue(0.3354);
		System.out.println(c.toString());
		
	}

}
