package gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.function.BiConsumer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.Agent;

public class GuiObjectRadioButton extends GuiObject{
	private String initialToggle;
	private ArrayList<RadioButton> buttonList;
	private ToggleGroup radioButtonGroup;
	private List<String> radioOptions;
	protected boolean isNewSelection;
	private Label errorLabel;
	private BiConsumer<Observable,String> setValue;
	private Label radioLabel;
	public GuiObjectRadioButton(String name,
			String resourceBundle, Agent obs, String startToggle, List<String> options, BiConsumer<Observable,String> myFunction) {
		super(name, resourceBundle, obs);
		initialToggle = startToggle;
		radioOptions = options;
		isNewSelection = false;
		setValue = myFunction;
	
	}

	@Override
	public Object createObjectAndReturnObject() {
		buttonList = new ArrayList<RadioButton>();

		radioButtonGroup = new ToggleGroup();
		
		buttonList = new ArrayList<RadioButton>();
		for (int index = 0; index < radioOptions.size(); index++){
			Toggle curToggle = new RadioButton(radioOptions.get(index));
			if (radioOptions.get(index).equals(initialToggle)){
				curToggle.setSelected(true);
			}
			buttonList.add((RadioButton) curToggle);
		}
		
		for(RadioButton button: buttonList){
			button.setToggleGroup(radioButtonGroup);
			
		}
		
		
		radioButtonGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
	        @Override
			public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {

	            RadioButton chk = (RadioButton)t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button
	            isNewSelection = true;
	            if (setValue!=null){
	            setValue.accept(getSerializable(), chk.getText());
	            }
	        }
	    });
		
		radioLabel = new Label(getResourceBundle().getString(getObjectName()+"LABEL"));
		VBox stateControls = new VBox();
		stateControls.getChildren().add(radioLabel);
		for(RadioButton button: buttonList){
			stateControls.getChildren().add(button);
		}
		
		errorLabel = new Label("");
		stateControls.getChildren().add(errorLabel);
		errorLabel.setVisible(false);
		return stateControls;
	}
		

	public String getValue(){
		RadioButton b = (RadioButton) radioButtonGroup.getSelectedToggle();
		if (b==null){
			errorLabel.setText(getResourceBundle().getString("RadioErrorLabel"));
			errorLabel.setVisible(true);
			errorLabel.setTextFill(Color.RED);
			return null;
		}
		return b.getText();
		
	}


	public void setIsNewSelection(boolean b){
		isNewSelection = false;
	}
	public boolean isNewSelected() {
		return isNewSelection;
	}
	public void setToggle(String newToggle){
		for (int index = 0; index < radioOptions.size(); index++){
			Toggle curToggle = new RadioButton(radioOptions.get(index));
			if (radioOptions.get(index)==newToggle){
				curToggle.setSelected(true);
			}
		}
	}


}