package model.component.character;

import model.component.base.Unit;
import api.IComponent;

/***
 * Created by Anirudh Jonnavithula on 04/03/16
 * 
 * @author aj168
 *
 */
public class Score extends Unit<Double> implements IComponent{
	
	public Score() {
		super(0.0);
	}
	
	public Score(double score) {
		super(score);
	}

	public void setScore(double score) {
		setValue1(score);
	}
	
	public double getScore() {
		return getValue1();
	}
	
	public void add(double dScore) {
		setValue1(getValue1() + dScore);
	}

}
