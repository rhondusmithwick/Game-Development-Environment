package model.component.events;

import api.IComponent;
import javafx.beans.property.SimpleObjectProperty;
import utility.SingleProperty;

import java.util.List;

public class EventPath implements IComponent {

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
