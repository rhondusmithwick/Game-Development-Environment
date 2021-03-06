package providedScripts

import api.IEntity
import api.ILevel
import groovy.transform.Field

/**
 * This describes a set of functions that we ant every groovy script to have.
 * @author Rhondu Smithwick
 */
@Field ILevel universe = (ILevel) binding.getVariable("universe");

boolean containsVariable(String variableName) {
    return binding.variables.containsKey(variableName);
}

Object getVariable(String variableName) {
    return binding.getVariable(variableName);
}

void setVariable(String variableName, Object value) {
    binding.setVariable(variableName, value);
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

void workOnEntities(Closure closure) {
    getEntitiesWithNamesAndIDs().each(closure);
}


Double getDouble(String variableName) {
    return getVariable(variableName) as double;
}

String getString(String variableName) {
	return getVariable(variableName) as String;
}