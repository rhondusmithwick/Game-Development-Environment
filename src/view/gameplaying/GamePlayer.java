package view.gameplaying;

import model.component.visual.ImagePath;
import datamanagement.XMLReader;
import api.IEntity;
import api.ISystemManager;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class GamePlayer {

	private ISystemManager loaded;
	private GameView gameView;

	public GamePlayer(Stage myStage, String language) {
	}

	public void init(String savedGame) {
		loadGame(savedGame);
		gameView = new GameView();
		Stage stage = new Stage();
		Scene scene = new Scene(gameView.getView());
		stage.setScene(scene);
	}

	private void loadGame(String savedGame) {
		loaded = new XMLReader<ISystemManager>().readSingleFromFile(savedGame);
		displayEntities();
		

	}

	private void displayEntities() {
		for (IEntity entity: loaded.getEntitySystem().getAllEntities()){
			ImageView entityView = entity.getComponent(ImagePath.class).getImageView();
			gameView.addToView(entityView);
		}		
	}

}
