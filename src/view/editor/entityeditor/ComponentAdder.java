package view.editor.entityeditor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;
import api.IEntity;
import model.component.visual.AnimatedSprite;
import model.component.visual.Sprite;
import view.enums.DefaultStrings;

/**
 * 
 * @author calinelson
 *
 */
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
			String component = iter.nextElement();
			if( !(component.equals("Sprite") && entity.hasComponent(AnimatedSprite.class)) && 
					!(component.equals("AnimatedSprite") && entity.hasComponent(Sprite.class))){
				myComponents.add(myComponentNames.getString(component));
			}
		}
		Collections.sort(myComponents);
		String componentName = super.makeAndShowChooser("chooseComponent", myComponents);
		if(componentName != null){
			componentFactory.addComponentToEntity(myLocs.getString(componentName), entity);
		}

	}

}
