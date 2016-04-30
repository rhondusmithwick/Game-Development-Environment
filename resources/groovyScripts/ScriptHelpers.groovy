package groovyScripts

import api.IEntity
import api.ILevel
import groovy.transform.Field

import java.util.function.Consumer

/**
 * Created by rhondusmithwick on 4/30/16.
 * @author Rhondu Smithwick
 */
@Field ILevel universe = (ILevel) binding.getVariable("universe");

boolean containsVariable (String variableName) {
    return binding.variables.containsKey(variableName)
}

Object getVariable(String variableName) {
    return binding.getVariable(variableName);
}

List<IEntity> getEntitiesWithNamesAndIDs() {
    List<IEntity> entities = new ArrayList<>();
    if (containsVariable("entityID")) {
        String entityID = (String) getVariable("entityID");
        entities.add(universe.getEntity(entityID));
    }
    if (containsVariable("entityName")) {
        String entityName = (String) getVariable("entityName");
        entities.addAll(universe.getEntitiesWithName(entityName));
    }
    return entities;
}
