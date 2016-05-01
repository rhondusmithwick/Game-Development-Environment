package model.component.hud;

import utility.Pair;
import utility.Quad;

public class PositionColor {
	private Pair<Double, Double> position;
	private Quad<Double, Double, Double, Double> color;
	
	public PositionColor(Pair<Double, Double> position, Quad<Double, Double, Double, Double> color) {
		this.position = position;
		this.color = color;
	}
	
	public Pair<Double, Double> getPosition() {
		return position;
	}

	public void setPosition(Pair<Double, Double> position) {
		this.position = position;
	}

	public Quad<Double, Double, Double, Double> getColor() {
		return color;
	}

	public void setColor(Quad<Double, Double, Double, Double> color) {
		this.color = color;
	}
	
 }
