package view;

import java.util.ArrayList;
import java.util.List;

import api.IEntity;
import api.ISerializable;
import javafx.collections.ObservableList;
import model.component.character.Defense;
import model.component.character.Health;
import model.component.character.Score;
import model.component.movement.Position;
import model.component.physics.Mass;
import model.component.visual.ImagePath;
import model.entity.Entity;
import view.enums.DefaultStrings;
import javafx.scene.shape.Rectangle;

public class DefaultsMaker {
	


	
	public static IEntity loadBackgroundDefault(){
		IEntity entity = new Entity(DefaultStrings.BACKGROUND_NAME.getDefault());
		entity.loadSpecsFromPropertiesFile(DefaultStrings.BACKGROUND_TEMPLATE_PATH.getDefault());
		entity.addComponent(new Position());
		ImagePath path = new ImagePath(DefaultStrings.BACKGROUND_PATH.getDefault());
	    entity.addComponent(path);
		return entity;
	}
		
	public static IEntity loadPlatformDefault(ObservableList<ISerializable> masterEntities){
		IEntity entity = new Entity(DefaultStrings.PLATFORM_NAME.getDefault());
		entity.loadSpecsFromPropertiesFile(DefaultStrings.PLATFORM_TEMPLATE_PATH.getDefault());
		entity.addComponent(new Position());
		ImagePath path = new ImagePath(DefaultStrings.PLATFORM_PATH.getDefault());
		entity.addComponent(path);
		//entity.addComponent(new Collision(imagePathToFitRectangle(entity.getComponent(ImagePath.class)),
			//entityListToIDs(masterEntities)));
		return entity;
	}
			
	public static IEntity loadCharacter1Default(){
		IEntity entity = new Entity(DefaultStrings.CHAR_1_NAME.getDefault());
		entity.loadSpecsFromPropertiesFile(DefaultStrings.CHARACTER_TEMPLATE_PATH.getDefault());
		entity.addComponent(new Position());
		ImagePath path = new ImagePath(DefaultStrings.CHAR_1_PATH.getDefault());
		entity.addComponent(path);
		entity.addComponent(new Defense(25));
		entity.addComponent(new Health(100));
		entity.addComponent(new Mass(100));
		entity.addComponent(new Score());
		return entity;
	}
	
	public static IEntity loadCharacter2Default(){
		IEntity entity = new Entity(DefaultStrings.CHAR_2_NAME.getDefault());
		entity.loadSpecsFromPropertiesFile(DefaultStrings.CHARACTER_TEMPLATE_PATH.getDefault());
		entity.addComponent(new Position());
		ImagePath path = new ImagePath(DefaultStrings.CHAR_2_PATH.getDefault());
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
