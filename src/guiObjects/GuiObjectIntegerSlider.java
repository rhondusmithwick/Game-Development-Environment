package guiObjects;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;

/**
 * 
 * @author calinelson
 *
 */
public class GuiObjectIntegerSlider extends GuiObjectSlider {

	public GuiObjectIntegerSlider(String name, String resourceBundle, String language, SimpleObjectProperty<?> property, Object object) {
		super(name, resourceBundle, language, property, object);
	}
	
	@Override
	protected Slider createSlider(String name, SimpleObjectProperty<?> property, Object object){
		Slider slider = new Slider(Integer.parseInt(getResourceBundle().getString(name+"Min")),Integer.parseInt(getResourceBundle().getString(name+ "Max")), (Integer) object);
		slider.setMajorTickUnit(Integer.parseInt(getResourceBundle().getString(name+"MajorIncrement")));
		slider.setMinorTickCount(Integer.parseInt(getResourceBundle().getString(name + "MinorIncrement")));
		slider.setSnapToTicks(true);
		slider.setValue((Integer) property.getValue());
		return slider;
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	protected void bindProperty(SimpleObjectProperty property, Slider slider) {
		slider.valueProperty().addListener(new ChangeListener<Number>() {
            @SuppressWarnings("unchecked")
			public void changed(ObservableValue<? extends Number> ov,Number old_val, Number new_val) {
            	SimpleObjectProperty<Integer> newproperty = (SimpleObjectProperty<Integer>) property;
            	newproperty.setValue( ((Double) slider.valueProperty().get()).intValue());
            
            }
        });
	}

}
