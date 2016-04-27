package view.editor.entityeditor;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;
import api.IComponent;
import api.IEntity;
import view.Utilities;
import view.enums.DefaultStrings;

public class ComponentAdder extends ComponentModifier{
	
	private ResourceBundle myResources, myLocs;
	private IEntity entity;
	private final ComponentFactory componentFactory = new ComponentFactory();
	private List<String> myComponents;
	
	public ComponentAdder(String language, IEntity entity){
		super(language);
		myResources = ResourceBundle.getBundle(language);
		myLocs = ResourceBundle.getBundle(DefaultStrings.COMPONENT_LOC.getDefault());
		myComponents = new ArrayList<>();
		this.entity=entity;
	}
	
	@Override
	public void modifyComponentList() {
		Enumeration<String> iter = myLocs.getKeys();
		while(iter.hasMoreElements()) {
			myComponents.add(myResources.getString(iter.nextElement()));
		}
		String componentName = super.makeAndShowChooser("chooseComponent", myComponents);
		if(componentName == null){
			return;
		}
		try {
			IComponent component = componentFactory.getComponent(Class.forName(myLocs.getString(componentName)), entity);
			entity.forceAddComponent(component, true);
		} catch (Exception e) {
			Utilities.showError(myResources.getString("error"), myResources.getString("addCompError"));
		}
	}

}
