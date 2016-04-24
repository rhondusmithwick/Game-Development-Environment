package api;


import java.util.List;


public interface IEnvironmentEditor {

    List<IComponent> getComponents();

    List<System> getSystems(); // These will be our defined systems class

    void show();

    void writeToFile(String musicURL);

    void close();

}
