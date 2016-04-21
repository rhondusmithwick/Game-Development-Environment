package providedScripts

import api.ISystemManager
import datamanagement.XMLWriter

/**
 * Created by rhondusmithwick on 4/20/16.
 * @author Rhondu Smithwick
 */

new XMLWriter<ISystemManager>().writeToFile(fileName, systemManager);