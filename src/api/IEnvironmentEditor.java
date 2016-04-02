package api;

import model.component.base.Component;

import java.util.List;

public interface IEnvironmentEditor {

    List<Component> getComponents();

    List<System> getSystems(); // These will be our defined systems class

    void show();

    void writeToFile(String musicURL);

    void close();

}
