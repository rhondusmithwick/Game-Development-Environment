package api;

import java.util.List;


public interface IEntityEditor {

    List<IComponent> getComponents ();

    IComponent editComponent (IComponent myComp); // Class specific method, might not be public

    IComponent getSingleComponent (IComponent myComp); // Class specific method, might not be public

    void show (); // add the editors contents to a tab in the tab pane and display it to the user

    void close (); // close editor tab

    void writeToFile (String filename);

}
