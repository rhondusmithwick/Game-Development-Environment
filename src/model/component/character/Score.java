package model.component.character;

import api.IComponent;
import javafx.beans.property.SimpleDoubleProperty;
import utility.Unit;

/***
 * Created by Anirudh Jonnavithula on 04/03/16
 *
 * @author ajonnav
 */
public class Score implements IComponent {

    private final Unit<SimpleDoubleProperty> unit = new Unit<>(new SimpleDoubleProperty(this, "score", 0.0));

    public Score() {
    }

    public Score(double score) {
        setScore(score);
    }

    public SimpleDoubleProperty scoreProperty() {
        return unit._1();
    }

    public double getScore() {
        return scoreProperty().get();
    }

    public void setScore(double score) {
        scoreProperty().set(score);
    }

}
