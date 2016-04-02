package api;

import model.component.base.Component;

import java.util.List;


public interface IEntityEditor {

    List<Component> getComponents();

    Component editComponent(Component myComp); // Class specific method, might not be public

    Component getSingleComponent(Component myComp); // Class specific method, might not be public

    void show(); //add the editors contents to a tab in the tab pane and display it to the user

    void close(); //close editor tab

    void writeToFile(String filename);

}
