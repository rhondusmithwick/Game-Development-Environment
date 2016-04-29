package voogasalad.util.hud.source;

/**
 * Class that represents a change in value (of a property)
 * @author bobby
 *
 */


public class ValueChange {
	private Object oldValue;
	private Object newValue;
	private Class<?> type;
	private String name;
	
	public ValueChange(Object oldValue, Object newValue, String name) {
		this.oldValue = oldValue;
		this.newValue = newValue;
		this.type = newValue.getClass();
		this.name = name;
	}
	
	public ValueChange(Object newValue, String name) {
		this(null, newValue, name);
	}
	
	
	/**
	 * Return the class of the field that was changed
	 * @return class
	 */
	
	public Class<?> getType() { 
		return type;
	}
	
	/**
	 * Returns the old value of the field
	 * @return old value
	 */
	
	public Object getOldValue() {
		return oldValue;
	}
	
	/**
	 * Returns the new value of the field
	 * @return new value
	 */
	
	public Object getNewValue() {
		return newValue;
	}
	
	
	/**
	 * Returns the name associated with this field
	 * (points, health, etc.)
	 * @return field name
	 */
	
	public String getFieldName() {
		return name;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		sb.append("- Old: ");
		sb.append(oldValue);
		sb.append(", New: ");
		sb.append(newValue);
		return sb.toString();
	}
	
}
