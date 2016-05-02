package providedScripts

import api.IComponent
import groovy.transform.BaseScript
import groovy.transform.Field
import voogasalad.util.reflection.Reflection


/**
 * Created by cyao42 on 5/2/2016.
 */
@BaseScript ScriptHelpers ScriptHelpers

@Field ResourceBundle componentLocations = ResourceBundle.getBundle("resources/propertyFiles/componentLocations");

@Field String componentStringField = containsVariable("componentString") ? getString("componentString") : "";
@Field String propertyNameField = containsVariable("propertyName") ? getString("propertyname") : "";

@Field Class<?> componentClass = Class.forName(componentLocations.getString(componentStringField));

def changeValue = { entity ->
        if (entity.hasComponent(componentClass)) {
            IComponent component = entity.getComponent(componentClass);
            if (newValue != null) {
                Reflection.callMethod(component, "set" + propertyNameField, newValue);
            }
            print "HEEEY";
        }
}

