package view;

import java.util.ArrayList;
import java.util.List;

import api.IEntity;
import api.ISerializable;
import enums.DefaultStrings;
import javafx.collections.ObservableList;
import model.component.character.Defense;
import model.component.character.Health;
import model.component.character.Score;
import model.component.movement.Position;
import model.component.physics.Mass;
import model.component.visual.ImagePath;
import model.entity.Entity;
import javafx.scene.shape.Rectangle;

public class DefaultsMaker {
	
	private final static String backgroundName = "Default Waterfall Background";
	private final static String backgroundPath = "resources/images/movingwaterfall.gif";
	private final static String platformName = "Default Mario Platform";
	private final static String platformPath = "resources/images/marioplatform.jpeg";
	private final static String character1Name = "Character 1";
	private final static String character1Path = "resources/images/white.png";
	private final static String character2Name = "Character 2";
	private final static String character2Path = "resources/images/blastoise.png";
	
	public static IEntity loadBackgroundDefault(){
		IEntity entity = new Entity(backgroundName);
		entity.loadSpecsFromPropertiesFile(DefaultStrings.BACKGROUND_TEMPLATE_PATH.getDefault());
		entity.addComponent(new Position());
		ImagePath path = new ImagePath(backgroundPath);
	    entity.addComponent(path);
		return entity;
	}
		
	public static IEntity loadPlatformDefault(ObservableList<ISerializable> masterEntities){
		IEntity entity = new Entity(platformName);
		entity.loadSpecsFromPropertiesFile(DefaultStrings.PLATFORM_TEMPLATE_PATH.getDefault());
		entity.addComponent(new Position());
		ImagePath path = new ImagePath(platformPath);
		entity.addComponent(path);
		//entity.addComponent(new Collision(imagePathToFitRectangle(entity.getComponent(ImagePath.class)),
			//entityListToIDs(masterEntities)));
		return entity;
	}
			
	public static IEntity loadCharacter1Default(){
		IEntity entity = new Entity(character1Name);
		entity.loadSpecsFromPropertiesFile(DefaultStrings.CHARACTER_TEMPLATE_PATH.getDefault());
		entity.addComponent(new Position());
		ImagePath path = new ImagePath(character1Path);
		entity.addComponent(path);
		entity.addComponent(new Defense(25));
		entity.addComponent(new Health(100));
		entity.addComponent(new Mass(100));
		entity.addComponent(new Score());
		return entity;
	}
	
	public static IEntity loadCharacter2Default(){
		IEntity entity = new Entity(character2Name);
		entity.loadSpecsFromPropertiesFile(DefaultStrings.CHARACTER_TEMPLATE_PATH.getDefault());
		entity.addComponent(new Position());
		ImagePath path = new ImagePath(character2Path);
		entity.addComponent(path);
		entity.addComponent(new Defense(25));
		entity.addComponent(new Health(100));
		entity.addComponent(new Mass(100));
		entity.addComponent(new Score());
		return entity;
	}
	
	private static Rectangle imagePathToFitRectangle(ImagePath path){
		if (path.getImageHeight()==0 || path.getImageWidth() == 0){
		}
		return new Rectangle((int) path.getImageHeight(), (int) path.getImageWidth());
	}
	
	private static List<String> entityListToIDs(ObservableList<ISerializable> masterEntities){
		List<String> ids = new ArrayList<String>();
		for (ISerializable entity : masterEntities){
			ids.add(((IEntity) entity).getID());
		}
		return ids;
	}

}
