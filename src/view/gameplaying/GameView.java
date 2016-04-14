package view.gameplaying;

import javafx.scene.Group;
import javafx.scene.image.ImageView;

public class GameView {
	private Group viewGroup;

	public GameView(){
		viewGroup = new Group();

	}
	
	public Group getView(){
		return viewGroup;
		
	}

	public void addToView(ImageView entityView) {
		viewGroup.getChildren().add(entityView);
	}
}
