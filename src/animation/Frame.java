package animation;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class Frame extends Rectangle{

	private int myID;
	private Label myLabel;

	public Frame (int id, double x, double y, double width, double height){
		super(x, y, width, height);
		myID = id;
		makeLable();
	}
	
	private void makeLable(){
		myLabel = new Label(String.valueOf(myID));
		myLabel.setFont(new Font("Arial", 30));
		myLabel.setTextFill(Color.RED);
		myLabel.layoutXProperty().bind(this.xProperty());
		myLabel.layoutYProperty().bind(this.yProperty());
	}
	
	public Label getLable(){
		return myLabel;
	}
	
	public int getID(){
		return myID;
	}
		
}
