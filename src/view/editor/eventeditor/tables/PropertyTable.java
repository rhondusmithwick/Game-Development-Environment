package view.editor.eventeditor.tables;

import api.IComponent;
import javafx.beans.property.SimpleObjectProperty;

import java.util.ResourceBundle;

/**
 * Property Table, displaying a certain Component's properties.
 *
 * @author Alankmc
 */
public class PropertyTable extends Table {
    public PropertyTable (PropertyTableManager manager, String language) throws NoSuchMethodException, SecurityException {

        super(manager, ResourceBundle.getBundle(language).getString("pickProperty"),
                manager.getClass().getMethod("propertyWasClicked", SimpleObjectProperty.class),
                SimpleObjectProperty.class);

    }

    /**
     * Takes in and IComponent, and adds in a Property for each one present in the Component.
     * 
     * @param Object dataHolder
     */
    @Override
    public void fillEntries (Object dataHolder) {
        for (SimpleObjectProperty<?> property : ((IComponent) dataHolder).getProperties()) {
            getEntries().add(new Entry(property, property.getName()));
        }
    }
}
