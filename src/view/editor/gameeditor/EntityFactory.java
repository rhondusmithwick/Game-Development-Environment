package view.editor.gameeditor;

import java.util.Map;
import java.util.ResourceBundle;

import api.IComponent;
import api.IEntity;
import model.entity.Entity;
import model.entity.PropertiesTemplateLoader;
import view.Utilities;
import view.editor.entityeditor.ComponentFactory;
import view.enums.DefaultStrings;

public class EntityFactory {
	
	private final ComponentFactory componentFactory = new ComponentFactory();
	
	public IEntity createEntity(String language, String template){

		IEntity entity = createEntity();
		Map<Class<? extends IComponent>, Integer> numComponents = new PropertiesTemplateLoader().loadSpecs(DefaultStrings.TEMPLATE_BUNDLE_LOC.getDefault() + template);
		numComponents.keySet().stream().forEach(e->addComponent(e, entity, numComponents, language));
		return entity;
	}
	

	public IEntity createEntity(){
		return new Entity();
		
	}


	private void addComponent(Class<? extends IComponent> componentName, IEntity entity, Map<Class<? extends IComponent>, Integer> numComponents, String language) {
		int numToAdd = numComponents.get(componentName);
		for(int i=0; i<numToAdd; i++){
			try {
					entity.forceAddComponent(componentFactory.getComponent(componentName, entity), true);

			
				
			} catch (Exception e) {
				
				ResourceBundle resources = ResourceBundle.getBundle(language);
				Utilities.showError(resources.getString("error"), resources.getString("createComp"));
			}
		}
	}
}
