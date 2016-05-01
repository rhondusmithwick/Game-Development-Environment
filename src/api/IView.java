package api;

import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.layout.Pane;

public interface IView {
	Pane getPane();

	SubScene getSubScene();
	
	void highlight(IEntity entity);

	void toggleHighlight(IEntity entity);

	IEntitySystem getEntitySystem();

	ILevel getLevel();
	
	void setScene(Scene scene);

	void dehighlight(IEntity entity);

}
