package api;

import javafx.scene.SubScene;
import javafx.scene.layout.Pane;

public interface IView {
	Pane getPane();

	SubScene getSubScene();
}
