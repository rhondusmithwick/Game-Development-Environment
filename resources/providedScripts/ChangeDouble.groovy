package providedScripts

import groovy.transform.BaseScript
import groovy.transform.Field

/**
 * Created by cyao42 on 5/2/2016.
 */
@BaseScript ChangeComponentValue ChangeComponentValue

@Field Double newValue = containsVariable("newValue") ? getVariable("newValue") : null;

workOnEntities(changeValue);


