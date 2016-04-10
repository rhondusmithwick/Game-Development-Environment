package gui;

import java.util.ArrayList;
import java.util.List;

import api.ISerializable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.Property;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class GuiObjectRadioButton extends GuiObject{
	private ArrayList<RadioButton> buttonList;
	private List<String> radioOptions;
	private Label errorLabel;
	private Label radioLabel;
	private ToggleGroup radioButtonGroup;
	private int initialToggle;
	public GuiObjectRadioButton(String name, String resourceBundle,EventHandler<ActionEvent> event, Property<?> property, ListProperty<?> list, ISerializable serial) {
		super(name, resourceBundle);
		buttonList = new ArrayList<RadioButton>();
		radioButtonGroup = new ToggleGroup();
		radioOptions = (List<String>) list;
		initialToggle = Integer.parseInt(getResourceBundle().getString(name+"Default"));
		radioLabel = new Label(getResourceBundle().getString(getObjectName()+"LABEL"));

		initializeRadioButtons();
		bindProperty(property);
	
	}
	public void bindProperty(Property property){
		((Property) radioButtonGroup.selectedToggleProperty()).bindBidirectional(property);
	}
	
	public void initializeRadioButtons() {

		
		for (int index = 0; index < radioOptions.size(); index++){
			Toggle curToggle = new RadioButton(radioOptions.get(index));
			if (index==initialToggle){
				curToggle.setSelected(true);
			}
			buttonList.add((RadioButton) curToggle);
		}
		
		for(RadioButton button: buttonList){
			button.setToggleGroup(radioButtonGroup);
			
		}
		

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



	public void setToggle(String newToggle){
		for (int index = 0; index < radioOptions.size(); index++){
			Toggle curToggle = new RadioButton(radioOptions.get(index));
			if (radioOptions.get(index)==newToggle){
				curToggle.setSelected(true);
			}
		}
	}
	@Override
	public Object getCurrentValue() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Control getControl() {
		return null;
	}
	@Override
	public Object getGuiNode() {
		
		
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


}