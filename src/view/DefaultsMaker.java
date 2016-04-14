package view;

import java.util.ArrayList;
import java.util.List;

import api.IEntity;
import api.ISerializable;
import enums.DefaultStrings;
import javafx.collections.ObservableList;
import model.component.movement.Position;
import model.component.physics.Collision;
import model.component.visual.ImagePath;
import model.entity.Entity;
import javafx.scene.shape.Rectangle;

public class DefaultsMaker {
	
	private final static String backgroundName = "Default Fire Background";
	private final static String backgroundPath = "resources/images/chinatown.png";
	private final static String platformName = "Default Mario Platform";
	private final static String platformPath = "resources/images/marioplatform.jpeg";
	
	public static IEntity loadBackgroundDefault(){
		IEntity entity = new Entity(backgroundName);
		entity.loadSpecsFromPropertiesFile(DefaultStrings.BACKGROUND_TEMPLATE_PATH.getDefault());
		entity.addComponent(new Position());
		ImagePath path = new ImagePath(backgroundPath);
		//Utilities.setUpImagePathSize(path);
	    entity.addComponent(path);
		return entity;
	}
		
	public static IEntity loadPlatformDefault(ObservableList<ISerializable> masterEntities){
		IEntity entity = new Entity(platformName);
		entity.loadSpecsFromPropertiesFile(DefaultStrings.PLATFORM_TEMPLATE_PATH.getDefault());
		// add render properties
		entity.addComponent(new Position());
		ImagePath path = new ImagePath(platformPath);
		//Utilities.setUpImagePathSize(path);
		entity.addComponent(path);
		//entity.addComponent(new Collision(imagePathToFitRectangle(entity.getComponent(ImagePath.class)),
			//entityListToIDs(masterEntities)));
		return entity;
	}
	
	private static Rectangle imagePathToFitRectangle(ImagePath path){
		if (path.getImageHeight()==0 || path.getImageWidth() == 0){
			//Utilities.setUpImagePathSize(path);
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
