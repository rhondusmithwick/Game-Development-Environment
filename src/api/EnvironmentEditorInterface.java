package api;

import java.util.List;
import entitytesting.Component;

public interface EnvironmentEditorInterface {

	List<Component> getComponents();
	
	List<System> getSystems(); // These will be our defined systems class

	void show();

	void writeToFile(String musicURL);

	void close(); 
	
}
