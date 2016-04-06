package gui;
import api.ISerializable;
import model.component.movement.Position;

/**
 * This is part of my masterpiece code. I reused this from my CellSociety project. This class introduces polymorphism and abstraction to the GUI elements. 
 * This Factory class creates GuiObjects that are linked to an agent. 
 * @author Melissa Zhang
 *
 */


public class GuiObjectFactory {
	private static final String GUI_RESOURCES = "gui";

	public GuiObjectFactory(){
	}
	public GuiObject createNewGuiObject(String type, ISerializable serial){
		switch(type){
			case("ORIENTATION"):{
				return new GuiObjectSlider(type, GUI_RESOURCES,null, ((Position) serial).orientationProperty(),0.0, 360.0, 5.0, 0);
			}


			}
		
		return null;
	}
}