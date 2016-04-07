package testing;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.Vooga;
import model.component.character.Health;
import model.component.character.Score;
import model.component.movement.Position;
import model.component.movement.Velocity;
import model.component.movement.X;
import model.component.visual.ImagePath;
import model.entity.Entity;
import api.IEntity;

public class BenTester extends Application{
	private Vooga vooga;
	private Stage stage;
	private static final String IMAGE_PATH = "resources/RhonduSmithwick.JPG";
	
	public BenTester() {
		stage = new Stage();
		vooga = new Vooga(stage);
	}
	
	public void start(Stage s) {
		Scene scene = vooga.init();
		s.setScene(scene);
		s.show();
		
		List<IEntity> list = new ArrayList<IEntity>();
		IEntity character = new Entity(0);
		character.addComponent(new Health((double) 100));
		character.addComponent(new Score((double) 100));
		Position pos = new Position(250.0, 250.0);
		character.addComponent(pos);
		character.addComponent(new ImagePath(IMAGE_PATH));
		character.addComponent(new Velocity(1.0, 0.0));
		list.add(character);
		drawEntities(list);

//		IEntitySystem system = new EntitySystem();
//		system.addEntities(list);
//		PhysicsEngine physics = new PhysicsEngine(system);
//
//		while(true) {
//			(new Thread(() -> test(physics, system, pos))).start();
//			try {
//				TimeUnit.MILLISECONDS.sleep(100);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
	}
	
//	private void test(IPhysicsEngine physics, IEntitySystem system, Position pos) {
//		physics.update(system, 1);
//		System.out.println(pos);
//	}
	
	private void drawEntities(List<IEntity> list) {
		for(IEntity entity: list) {
			Position pos = entity.getComponent(Position.class);
			ImagePath imagePath = entity.getComponent(ImagePath.class);
			ImageView image = createImage(imagePath, pos);
			vooga.draw(image);
		}
	}
	
	private ImageView createImage(ImagePath path, Position pos) {
		URI resource = new File(path.getImagePath()).toURI();
		Image image = new Image(resource.toString());
		ImageView imageView = new ImageView(image);
		imageView.setX(pos.getX());
		imageView.setY(pos.getY());
		imageView.setFitHeight(100);
        imageView.setPreserveRatio(true);
//		imageView.xProperty().bind(pos.xProperty());
//		imageView.yProperty().bind(pos.yProperty());
		return imageView;
	}
	
	public static void main(String[] args) {
        launch(args);
    }
	
}
