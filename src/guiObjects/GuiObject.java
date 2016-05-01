package guiObjects;

import java.util.ResourceBundle;

/**
 * Abstract class for gui objects
 *
 * @author calinelson
 */
public abstract class GuiObject {
    private final String objectName;
    private final ResourceBundle myResources;

    /**
     * constructor for gui objects
     *
     * @param name           object name
     * @param resourceBundle resource bundle
     */
    public GuiObject (String name, String resourceBundle) {
        this.objectName = name;
        this.myResources = ResourceBundle.getBundle(resourceBundle);
    }

    /**
     * return object name
     *
     * @return string object name
     */
    public String getObjectName () {
        return objectName;
    }

    /**
     * return resource bundle string
     *
     * @return return string for resourceBundle
     */
    public ResourceBundle getResourceBundle () {
        return myResources;
    }

    /**
     * get current value of object
     *
     * @return object current value
     */
    public abstract Object getCurrentValue ();

    /**
     * return guiNode
     *
     * @return object
     */
    public abstract Object getGuiNode ();


}