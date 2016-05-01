package model.component.hud;

import java.util.Map;

import com.google.common.collect.Maps;

import api.IComponent;
import utility.Pair;

public class HUD implements IComponent {
	private Map<String, Pair<Double, Double>> shapes = Maps.newLinkedHashMap();
	private Map<String, Pair<Double, Double>> text = Maps.newLinkedHashMap();
	
	public HUD() {
	}
	
	public HUD(Map<String, Pair<Double, Double>> shapes) {
		setShapes(shapes);
	}
	
	public HUD(Map<String, Pair<Double, Double>> text, boolean flag) {
		setText(text);
	}
	
	public HUD(Map<String, Pair<Double, Double>> shapes, Map<String, Pair<Double, Double>> text) {
		setShapes(shapes);
		setText(text);
	}
	
	@Override
	public void update() {
		// Do nothing.
	}
	
	public Map<String, Pair<Double, Double>> getShapes() {
		return this.shapes;
	}
	
	public void setShapes(Map<String, Pair<Double, Double>> map) {
		this.shapes = map;
	}
	
	public Map<String, Pair<Double, Double>> getText() {
		return this.text;
	}
	
	public void setText(Map<String, Pair<Double, Double>> map) {
		this.text = map;
	}
	
}
