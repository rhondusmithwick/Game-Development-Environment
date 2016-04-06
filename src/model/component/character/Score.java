package model.component.character;

import javafx.beans.property.SimpleDoubleProperty;
import model.component.IComponent;
import utility.Unit;

/***
 * Created by Anirudh Jonnavithula on 04/03/16
 *
 * @author ajonnav
 */
public class Score implements IComponent {

    private final Unit<SimpleDoubleProperty> unit;

    public Score() {
        unit = new Unit<>(new SimpleDoubleProperty(this, "score", 0.0));
    }

    public Score(double score) {
        this();
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
