package view.gameplaying;

import model.component.movement.Position;
import model.component.visual.ImagePath;
import datamanagement.XMLReader;
import enums.GUISize;
import api.IEntity;
import api.ILevel;
import api.ISerializable;
import api.ISystemManager;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class GamePlayer {

	//private SaveGame loaded;
	private GameView gameView;
	private Stage myStage;

	public GamePlayer(Stage stage, String language) {
		myStage = stage;
	}

	public void init(String savedGame) {
		gameView = new GameView();
		Scene scene = new Scene(gameView.getView());
		myStage.setScene(scene);
		loadGame(savedGame);
	}

	private void loadGame(String savedGame) {
		//loaded = new XMLReader<SaveGame>().readSingleFromFile(savedGame);
		displayEntities();
		

	}

	private void displayEntities() {
		
		//for (ISerializable system: loaded.getEnvironments()){
			//for (IEntity entity: ((ILevel) system).getAllEntities()){
					//ImageView entityView = entity.getComponent(ImagePath.class).getImageView();
					//entityView.setLayoutX(entity.getComponent(Position.class).getX());
					//entityView.setLayoutY(entity.getComponent(Position.class).getY());
					//gameView.addToView(entityView);
			//}

		//}		
	}

}
