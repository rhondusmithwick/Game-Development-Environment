package model.component.character;

import javafx.beans.property.SimpleDoubleProperty;
import com.thoughtworks.xstream.annotations.XStreamAlias;

public class Score {

	@XStreamAlias("score")
	private final SimpleDoubleProperty score = new SimpleDoubleProperty(this, "score", 0);;

	public Score(double score) {
		setScore(score);
	}

	public void setScore(double score) {
		this.score.set(score);
	}
	
	public double getScore() {
		return score.get();
	}
	
	public void add(double dScore) {
		setScore(getScore() + dScore);
	}

}
