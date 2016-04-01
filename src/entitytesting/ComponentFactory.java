package entitytesting;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;


/**
 * Created by rhondusmithwick on 3/31/16.
 *
 * @author Rhondu Smithwick
 */
public class ComponentFactory {
    private static final String DEFAULT_LOCATIONS = "componentLocations";
    private static final String DEFAULT_DELIMITER = "; ";
    private final ResourceBundle componentLocations = ResourceBundle.getBundle(DEFAULT_LOCATIONS);

    public List<Component> readFromPropertyFile(String fileName) {
        List<Component> components = new ArrayList<>();
        ResourceBundle bundle = ResourceBundle.getBundle(fileName);
        Enumeration<String> iter = bundle.getKeys();
        while (iter.hasMoreElements()) {
            String componentName = iter.nextElement();
            components.add(createComponent(componentName, bundle));
        }
        return components;
    }

    public Component createComponent(String componentName, ResourceBundle bundle) {
        String[] inputs = bundle.getString(componentName).split(DEFAULT_DELIMITER);
        try {
            Class<?> theClass = Class.forName(componentLocations.getString(componentName));
            Constructor<?> theConstructor = theClass.getConstructor(String[].class);
            Object[] passed = {inputs};
            return (Component) theConstructor.newInstance(passed);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }


}
