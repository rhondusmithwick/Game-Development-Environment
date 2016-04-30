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
public class GuiObjectDoubleSlider extends GuiObjectSlider{

	public GuiObjectDoubleSlider(String name, String resourceBundle, String language, SimpleObjectProperty<?> property,
			Object object) {
		super(name, resourceBundle, language, property, object);
	}
	
	@Override
	protected Slider createSlider(String name, SimpleObjectProperty<?> property, Object object){
		Slider slider = new Slider(Double.parseDouble(getResourceBundle().getString(name+"Min")),Double.parseDouble(getResourceBundle().getString(name+ "Max")), (Double) object); 
		slider.setValue((Double) property.getValue());
		return slider;
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	protected void bindProperty(SimpleObjectProperty property, Slider slider) {
		slider.valueProperty().addListener(new ChangeListener<Number>() {
            @SuppressWarnings("unchecked")
			public void changed(ObservableValue<? extends Number> ov,Number old_val, Number new_val) {
            	property.setValue(slider.valueProperty().get());
            
            }
        });
	}

}
