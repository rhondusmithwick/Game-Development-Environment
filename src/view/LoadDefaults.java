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

public class LoadDefaults {
	
	public static IEntity loadBackgroundDefault(){
		IEntity entity = new Entity("Default Dragon Temple Background");
		entity.loadSpecsFromPropertiesFile("templates/Background");
		entity.addComponent(new Position());
	    entity.addComponent(new ImagePath("resources/images/fire.gif"));
		//entity.forceAddComponent(new Position(),true);
	    //entity.forceAddComponent(new ImagePath("resources/images/dragontemple.gif"),true);
		return entity;
	}
	
	public static IEntity loadPlatformDefault(ObservableList<ISerializable> masterEntities){
		IEntity entity = new Entity("Default Mario Platform");
		//entity.loadSpecsFromPropertiesFile("resources/templates/PlatformSprite.properties");
		// add friction
		// add render properties
		entity.forceAddComponent(new Position(),true);
		ImagePath path = new ImagePath("resources/images/marioplatform.jpeg");
		entity.forceAddComponent(path, true);
		entity.forceAddComponent(new Collision(imagePathToFitRectangle(entity.getComponent(ImagePath.class)),
			entityListToIDs(masterEntities)),true);
		return entity;
	}
	
	private static Rectangle imagePathToFitRectangle(ImagePath path){
		URI resource = new File(path.getImagePath()).toURI();
		Image image = new Image(resource.toString());
		ImageView imageView = new ImageView(image);
		return new Rectangle((int) imageView.getFitWidth(), (int) imageView.getFitHeight());
	}
	
	private static List<String> entityListToIDs(ObservableList<ISerializable> masterEntities){
		List<String> ids = new ArrayList<String>();
		for (ISerializable entity : masterEntities){
			ids.add(((IEntity) entity).getID());
		}
		return ids;
	}

}
