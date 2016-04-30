package testing.animation;

import model.component.visual.AnimatedSprite;
import model.entity.Entity;
import view.editor.AnimationEditor;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AnimationEditorTesting extends Application{
	@Override
	public void start(Stage stage) throws Exception {
		Entity entity = new Entity();
		entity.addComponent(new AnimatedSprite());
		AnimationEditor animatorEditor = new AnimationEditor(entity);
		animatorEditor.populateLayout();
		Group g = new Group();
		Scene scene = new Scene(g);

		g.getChildren().add(animatorEditor.getPane());
		stage.setScene(scene);
		stage.show();
		
	}
	public static void main(String[] args) {
	launch(args);
}
}
