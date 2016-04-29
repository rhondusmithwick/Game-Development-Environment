package view.editor.entityeditor;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;
import api.IEntity;
import view.enums.DefaultStrings;

public class ComponentAdder extends ComponentModifier{

	private ResourceBundle myComponentNames;
	private final ResourceBundle myLocs =ResourceBundle.getBundle(DefaultStrings.COMPONENT_LOC.getDefault());
	private IEntity entity;
	private final ComponentFactory componentFactory = new ComponentFactory();
	private List<String> myComponents;

	public ComponentAdder(String language, IEntity entity){
		super(language);
		this.myComponentNames = ResourceBundle.getBundle(language + DefaultStrings.COMPONENTS.getDefault());
		myComponents = new ArrayList<>();
		this.entity=entity;
	}

	@Override
	public void modifyComponentList() {
		Enumeration<String> iter = myLocs.getKeys();
		while(iter.hasMoreElements()) {
			myComponents.add(myComponentNames.getString(iter.nextElement()));
		}
		String componentName = super.makeAndShowChooser("chooseComponent", myComponents);
		if(componentName != null){
			componentFactory.addComponentToEntity(myLocs.getString(componentName), entity);
		}

	}

}
