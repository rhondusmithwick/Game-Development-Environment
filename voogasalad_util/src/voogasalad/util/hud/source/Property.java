package voogasalad.util.hud.source;

import java.util.HashSet;
import java.util.Observable;
import java.util.Set;


/**
 * Based on existing javafx properties, this class is
 * more extensible and also works with xstream.
 * 
 * It can hold any sort of object value, but once an instance's
 * initial value is set, it can only be set to values of the
 * same class type in the future.
 * 
 * They can also be bound to other properties which hold the same 
 * value type, so that any time one property is updated, every
 * property that it's bound to also has their values updated
 * 
 * @author bobby
 *
 */


public class Property extends Observable {
	
	private Class<?> type;
	private Object value;
	private Set<Property> bindings;
	private String name;
	
	public Property(Property value) {
		bindings = new HashSet<>();
		this.type = value.getType();
		bind(value);
	}
	
	public Property(Object value, String name) {
		this.name = name;
		this.value = value;
		this.type = value.getClass();
		bindings = new HashSet<>();
	}
	
	
	/**
	 * Set a new value for this property. Will be valid
	 * so long as it's of the same type.
	 * @param newValue of property
	 * @throws IllegalArgumentException if the new value is of a different type
	 */
	
	public void setValue(Object newValue) throws IllegalArgumentException {
		if (newValue.getClass().equals(type)) {
			if (newValue.equals(value)) {
				ValueChange change = new ValueChange(value, newValue, name);
				value = newValue;
				setChanged();
				notifyObservers(change);
				updateBound();
			}
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	
	/**
	 * Get the value of this property
	 * @return Object
	 */
	
	public Object getValue() {
		return value;
	}
	
	
	/**
	 * Get the class of this property
	 * @return Class
	 */
	
	public Class<?> getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return value.toString();
	}
	
	
	/**
	 * Will bind this property to another property, so long as they are
	 * of the same type. If either of the two properties are also bound
	 * to other properties, all properties will now be linked.
	 * 
	 * Will override the value of this property with the value of the other property
	 * 
	 * Any time that either of the two linked properties are changed, the other
	 * property will have that new value as well.
	 * 
	 * @param other property
	 * @throws IllegalArgumentException if other property is of a different type
	 */
	
	public void bind(Property other) throws IllegalArgumentException {
		if (other.getType().equals(type)) {
			this.name = other.getFieldName();
			bindings.add(other);
			setValue(other.getValue());
			other.getBindings().add(this);
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	
	/**
	 * Removes the bidirectional binding between this binding
	 * and another binding
	 * @param other property
	 */
	
	public void unbind(Property other) {
		bindings.remove(other);
		other.getBindings().remove(this);
	}
	
	
	/**
	 * Updates all bindings with the current value of this property
	 */
	
	
	public void updateBound() {
		for (Property p: bindings) {
			if (!p.getValue().equals(value)) {
				p.setValue(value);
			}
		}
	}
	
	/**
	 * Returns a set of the bound properties to this
	 * @return bindings
	 */
	
	public Set<Property> getBindings() {
		return bindings;
	}
	
	/**
	 * Returns the associated field name of this property.
	 * For example, "Points" or "Health".
	 * @return field name
	 */
	
	public String getFieldName() {
		return name;
	}
	
}
