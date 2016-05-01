package model.component.hud;

import java.util.HashMap;
import java.util.Map;
import api.IComponent;

public class HUD implements IComponent {
	private Map<String, PositionColor> shapes = new HashMap<String, PositionColor>();
	private Map<String, PositionColor> text = new HashMap<String, PositionColor>();
	
	public HUD() {
	}
	
	public HUD(Map<String, PositionColor> shapes) {
		setShapes(shapes);
	}
	
	public HUD(Map<String, PositionColor> text, boolean flag) {
		setText(text);
	}
	
	public HUD(Map<String, PositionColor> shapes, Map<String, PositionColor> text) {
		setShapes(shapes);
		setText(text);
	}
	
	@Override
	public void update() {
		// Do nothing.
	}

	public Map<String, PositionColor> getShapes() {
		return shapes;
	}

	public void setShapes(Map<String, PositionColor> shapes) {
		this.shapes = shapes;
	}

	public Map<String, PositionColor> getText() {
		return text;
	}

	public void setText(Map<String, PositionColor> text) {
		this.text = text;
	}
	
}
