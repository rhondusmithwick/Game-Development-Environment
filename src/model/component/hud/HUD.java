package model.component.hud;

import api.IComponent;
import javafx.beans.property.SimpleObjectProperty;
import utility.SingleProperty;

public class HUD implements IComponent {
	private static final String DEFAULT = "shape:Rectangle;width:100;height:100;color:10,10,10,1";
	private final SingleProperty<String> singleProperty = new SingleProperty<>("HUD", DEFAULT);

	public HUD() {
	}
	
	public HUD(String data) {
		setHUD(data);
	}
	
	public void setHUD(String hud) {
		HUDProperty().set(hud);
	}
	
	public SimpleObjectProperty<String> HUDProperty() {
        return singleProperty.property1();
    }
	
	public String getHUD() {
        return HUDProperty().get();
    }

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
}
