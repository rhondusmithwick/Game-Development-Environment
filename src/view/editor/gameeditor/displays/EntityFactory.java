package view.editor.gameeditor.displays;

import java.util.Map;
import api.IComponent;
import api.IEntity;
import model.entity.Entity;
import model.entity.PropertiesTemplateLoader;
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

			componentFactory.addComponentToEntity(componentName.getName(), entity);



		}
	}
}
