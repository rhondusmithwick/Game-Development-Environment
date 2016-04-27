package view.enums;

import java.util.Arrays;
import java.util.List;

import api.IComponent;
import api.IEntity;
import model.component.character.Defense;
import model.component.character.Health;
import model.component.character.Score;
import model.component.movement.Position;
import model.component.physics.Mass;
import model.component.visual.Sprite;
import model.entity.Entity;

public enum DefaultEntities {
	
	CHAR_1("Character 1",  "templates/player", 
			Arrays.asList(new Position(), new Defense(25), new Health(100), new Mass(100), new Score(), new Sprite("resources/images/white.png")) ),
	
	CHAR_2("Character 2", "templates/player", 
			Arrays.asList(new Position(), new Defense(25), new Health(100), new Mass(100), new Score(), new Sprite("resources/images/blastoise.png"))),
	
	BACKGROUND("Default Waterfall Background", "templates/Background", 
			Arrays.asList(new Position(), new Sprite("resources/images/movingwaterfall.gif"))),
	
	PLATFORM("Default Mario Platform", "templates/PlatformSprite", 
			Arrays.asList(new Position(), new Sprite("resources/images/marioplatform.jpeg")));
	
	
	
    private final IEntity entity;

    /**
     * creates default for string str
     *
     * @param str default string
     */
    DefaultEntities(String name, String template, List<IComponent> components) {
    	entity = new Entity(name);
		entity.loadSpecsFromPropertiesFile(template);
		components.stream().forEach(e->entity.addComponent(e));

		
    }

    /**
     * returns default string
     *
     * @return default string for enum
     */
    public IEntity getDefault() {
        return this.entity;
    }
}
