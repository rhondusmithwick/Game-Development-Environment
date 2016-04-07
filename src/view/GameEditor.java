package view;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class GameEditor extends Editor {
	
	private VBox vBox;
	private List<Node> entryList;
	
	GameEditor(){
		vBox = new VBox(20);
		vBox.setAlignment(Pos.TOP_LEFT);
		entryList = new ArrayList<>();
	}

	@Override
	public void loadDefaults() {
		// TODO Auto-generated method stub

	}

	@Override
	public Pane getPane() {
		populateLayout(vBox);
		return vBox;
	}

	@Override
	public void populateLayout(Pane pane) {
		Label nTitle = new Label("Game Name");
		TextArea nameBox = Utilities.makeTextArea("Game Name");
		nameBox.maxWidthProperty().bind(vBox.widthProperty().subtract(80));
		Label dTitle = new Label("Game Description");
		TextArea description = Utilities.makeTextArea("Game Description");
		description.setMinHeight(30);
		pane.getChildren().addAll(nTitle, nameBox, dTitle, description);

	}

	@Override
	public void updateEditor() {
		populateLayout(vBox);

	}

}
