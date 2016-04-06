package model.entity;

import com.google.common.collect.Maps;
import model.component.IComponent;

import java.util.Enumeration;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by rhondusmithwick on 4/6/16.
 *
 * @author Rhondu Smithwick
 */
public class ResourceFileSpecLoader implements SpecLoader<Class<? extends IComponent>> {

    private final ResourceBundle locationsBundle = ResourceBundle.getBundle("resources/componentLocations");

    @Override
    public Map<Class<? extends IComponent>, Integer> loadSpecs(String resourceFile) {
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
