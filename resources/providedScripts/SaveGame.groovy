package providedScripts

import api.ISystemManager
import datamanagement.XMLWriter
import groovy.transform.Field

/**
 * Created by rhondusmithwick on 4/20/16.
 * @author Rhondu Smithwick
 */

@Field String fileName = (String) binding.getVariable("fileName");
@Field ISystemManager systemManager = (ISystemManager) binding.getVariable("systemManager");

new XMLWriter<ISystemManager>().writeToFile(fileName, systemManager);