package view.editor.entityeditor;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;
import api.IEntity;
import view.enums.DefaultStrings;

public class ComponentAdder extends ComponentModifier{

	private ResourceBundle myResources;
	private final ResourceBundle myLocs =ResourceBundle.getBundle(DefaultStrings.COMPONENT_LOC.getDefault());
	private IEntity entity;
	private final ComponentFactory componentFactory = new ComponentFactory();
	private List<String> myComponents;

	public ComponentAdder(String language, IEntity entity){
		super(language);
		myResources = ResourceBundle.getBundle(language);
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
		if(componentName != null){
			componentName = myLocs.getString(componentName);
			if(componentName.endsWith(".Collision")){
				entity.forceAddComponent(componentFactory.getComponent(componentName, entity), true);
			}else{
				entity.forceAddComponent(componentFactory.getComponent(myLocs.getString(componentName)),true);
			}
		}

	}

}
