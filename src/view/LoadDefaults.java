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
		IEntity entity = new Entity();
		//entity.loadSpecsFromPropertiesFile("resources/templates/Background.properties");
		entity.setSpec(Position.class, 1);
		//entity.addComponent(new Position());
		//entity.addComponent(new ImagePath("resources/images/ghosttemple.jpg"));
		return entity;
	}
	
	public static IEntity loadPlatformDefault(ObservableList<ISerializable> masterEntities){
		IEntity entity = new Entity();
		
		entity.loadSpecsFromPropertiesFile("resources/templates/PlatformSprite.properties");
		// add friction
		// add render properties
		//entity.addComponent(new Position());
		//ImagePath path = new ImagePath("resources/images/marioplatform");
		//entity.addComponent(path);
		//entity.addComponent(new Collision(imagePathToFitRectangle(entity.getComponent(ImagePath.class)),
			//	entityListToIDs(masterEntities)));
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
