package model.component.events;

import java.util.List;

import javafx.beans.property.SimpleObjectProperty;
import utility.SingleProperty;
import api.IComponent;

public class EventPath implements IComponent{
	/**
     * Single Proprety.
     */
    private final SingleProperty<String> singleProperty = new SingleProperty<>("EventPath", "");

    /**
     * Empty constructor. Starts at "".
     */
    public EventPath() {
    }

    /**
     * Start with a default value.
     *
     * @param eventPath the default value
     */
    public EventPath(String eventPath) {
        setEventPath(eventPath);
    }

    /**
     * Get the eventPath property.
     *
     * @return the eventPath property
     */
    public SimpleObjectProperty<String> eventPathProperty() {
        return singleProperty.property1();
    }

    public String getEventPath() {
        return eventPathProperty().get();
    }

    public void setEventPath(String eventPath) {
    	eventPathProperty().set(eventPath);
    }

    @Override
    public List<SimpleObjectProperty<?>> getProperties() {
        return singleProperty.getProperties();
    }
}
