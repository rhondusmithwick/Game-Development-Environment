package view;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import api.IEntity;
import api.ISerializable;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.component.movement.Position;
import model.component.physics.Collision;
import model.component.visual.ImagePath;
import model.entity.Entity;
import javafx.scene.shape.Rectangle;

public class DefaultsMaker {
	
	public static IEntity loadBackgroundDefault(){
		IEntity entity = new Entity("Default Fire Background");
		entity.loadSpecsFromPropertiesFile("templates/Background");
		entity.addComponent(new Position());
		ImagePath path = new ImagePath("resources/images/fire.gif");
		Utilities.setUpImagePathSize(path);
	    entity.addComponent(path);
		return entity;
	}
		
	public static IEntity loadPlatformDefault(ObservableList<ISerializable> masterEntities){
		IEntity entity = new Entity("Default Mario Platform");
		//entity.loadSpecsFromPropertiesFile("templates/PlatformSprite");
		// add friction
		// add render properties
		entity.forceAddComponent(new Position(),true);
		ImagePath path = new ImagePath("resources/images/marioplatform.jpeg");
		Utilities.setUpImagePathSize(path);
		entity.forceAddComponent(path, true);
		entity.forceAddComponent(new Collision(imagePathToFitRectangle(entity.getComponent(ImagePath.class)),
			entityListToIDs(masterEntities)),true);
		return entity;
	}
	
	private static Rectangle imagePathToFitRectangle(ImagePath path){
		if (path.getImageHeight()==0 || path.getImageWidth() == 0){
			Utilities.setUpImagePathSize(path);
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
