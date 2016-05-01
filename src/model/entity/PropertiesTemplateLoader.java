package model.entity;

import api.IComponent;
import api.ITemplateLoader;
import com.google.common.collect.Maps;

import java.util.Enumeration;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * A ITemplateLoader for resource files.
 * {@inheritDoc}
 *
 * @author Rhondu Smithwick
 */
public class PropertiesTemplateLoader implements ITemplateLoader<Class<? extends IComponent>> {

    private static final String COMPONENT_LOCATIONS = "propertyFiles/componentLocations";

    /**
     * The resource Bundle for component locations.
     */
    private final ResourceBundle locationsBundle = ResourceBundle.getBundle(COMPONENT_LOCATIONS);

    /**
     * {@inheritDoc}
     * An implementation for resource bundles.
     *
     * @param resourceFile the resource file.
     * @return the specs map
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<Class<? extends IComponent>, Integer> loadSpecs (String resourceFile) {
        ResourceBundle bundle = ResourceBundle.getBundle(resourceFile);
        Map<Class<? extends IComponent>, Integer> specs = Maps.newHashMap();
        Enumeration<String> iter = bundle.getKeys();
        while (iter.hasMoreElements()) {
            String component = iter.nextElement();
            Integer numSpec = Integer.parseInt(bundle.getString(component));
            String location = locationsBundle.getString(component);
            try {
                Class<? extends IComponent> componentClass = (Class<? extends IComponent>) Class.forName(location);
                specs.put(componentClass, numSpec);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return specs;
    }
}
