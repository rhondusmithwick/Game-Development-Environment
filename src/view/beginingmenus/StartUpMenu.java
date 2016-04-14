package view.beginingmenus;

import java.io.File;
import java.util.List;

import enums.GUISize;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public abstract class StartUpMenu {

	
	private Scene myScene;
	private Stage myStage;
	private Group root;
	private VBox myVBox;
	private static final String CSS = "resources/cssFiles/";
	private static final String MAIN_CSS = "main.css";
	
	public StartUpMenu(Stage myStage){
		this.myStage = myStage;
	}

	public void init() {
		myScene = new Scene(createDisplay(), GUISize.MAIN_SIZE.getSize(), GUISize.MAIN_SIZE.getSize());
		myScene.getStylesheets().add(new File(CSS + MAIN_CSS).toURI().toString());
		myStage.setScene(myScene);
		myStage.show();
	}
	
	protected Group createDisplay(){
		root = new Group();
		createVBox();
		return root;
	}

	private void createVBox() {
		myVBox = new VBox(GUISize.ORIG_MENU_PADDING.getSize());
		myVBox.prefHeightProperty().bind(myStage.heightProperty());
		myVBox.prefWidthProperty().bind(myStage.widthProperty());
		myVBox.setAlignment(Pos.CENTER);
		root.getChildren().add(myVBox);
	}
	
	public void addNodesToVBox(List<Node> toAdd){
		myVBox.getChildren().addAll(toAdd);
	}
	
}
