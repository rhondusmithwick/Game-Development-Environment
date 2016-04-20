package testing.games;

import javafx.application.Application;
import javafx.stage.Stage;
import datamanagement.XMLWriter;
import model.component.character.Health;
import model.component.visual.ImagePath;
import model.entity.Entity;
import api.IEntity;

public class AniTest extends Application{
	private String charizardImage = "resources/images/charizard.png";
	private String blastoiseImage = "resources/images/blastoise.png";
	private String ironImage = "resources/images/ironman.png";
	private static String outFolder = "src/testing/gameData/";
	public static void main(String[] args) {
		AniTest test = new AniTest();
		IEntity char1 = test.createCharizard();
		IEntity char2 = test.createBlastoise();
		IEntity char3 = test.createIronMan();
		new XMLWriter<IEntity>().writeToFile(outFolder+"entity/charizard.xml", char1);
		new XMLWriter<IEntity>().writeToFile(outFolder+"entity/blastoise.xml", char2);
		new XMLWriter<IEntity>().writeToFile(outFolder+"entity/ironman.xml", char3);
	}
	
	public IEntity createCharizard() {
		IEntity character = new Entity("Ani1");
		character.addComponent(new ImagePath(charizardImage));
		character.addComponent(new Health(100.0));
		return character;
	}
	
	public IEntity createBlastoise() {
		IEntity character = new Entity("Ani2");
		character.addComponent(new ImagePath(blastoiseImage));
		character.addComponent(new Health(100.0));
		return character;
	}
	
	public IEntity createIronMan() {
		IEntity character = new Entity("Ani3");
		character.addComponent(new ImagePath(ironImage));
		character.addComponent(new Health(100.0));
		return character;
	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
