package model.component.character;

import javafx.beans.property.SimpleDoubleProperty;
import utility.Unit;
import model.component.IComponent;

/***
 * Created by Anirudh Jonnavithula on 04/03/16
 * 
 * @author ajonnav
 *
 */
public class Score extends Unit<SimpleDoubleProperty> implements IComponent{
	
	public Score() {
		setValue1(new SimpleDoubleProperty(this, "score", 0));
	}
	
	public Score(double score) {
		this();
		setScore(score);
	}

	public SimpleDoubleProperty scoreProperty() {
		return getValue1();
	}
	
	public void setScore(double score) {
		scoreProperty().set(score);
	}
	
	public double getScore() {
		return scoreProperty().get();
	}

}
