//EXAMPLE FILE: works with TLGCS project, will not work with yours
//
//package voogasalad.util.hud.example;
//
//import gameengine.controller.Game;
//import gameengine.model.AttributeType;
//import voogasalad.util.hud.source.HUDController;
//import voogasalad.util.hud.source.IValueFinder;
//import voogasalad.util.hud.source.Property;
//
//
///**
// * Example implementation of IValueFinder. This file does NOT compile and will NOT
// * work with your project. This simply serves as an example.
// * 
// * @author bobby
// *
// */
//
//public class ExampleValueFinder implements IValueFinder {
//
//	private HUDController controller;
//	private Game data; //for other projects, your data will be a different class
//	
//	
//	
//	
//	
//	
//	@Override
//	public Property find(String key) {
//		Property ret = null;
//		switch (key.toLowerCase()) {
//			case "points":
//				ret = data.getCurrentLevel().getMainCharacter().getAttribute(AttributeType.POINTS).getProperty();
//				break;
//			case "health":
//				//reflection here to save LOC?
//				break;
//			case "ammo":
//				//
//				break;
//			default:
//				ret = new Property("Value Not Found", key);
//				break;
//		
//		}
//		ret.addObserver(controller); //THIS LINE IS IMPORTANT - OTHERWISE CONTROLLER WILL NEVER KNOW ABOUT UPDATED VALUES
//		return ret;
//	}
//
//
//
//	@Override
//	public void setController(HUDController controller) {
//		this.controller = controller;
//	}
//
//	@Override
//	public void setDataSource(Object dataSource) throws IllegalArgumentException {
//		if (dataSource instanceof Game) {
//			this.data = (Game) dataSource;
//		} else {
//			throw new IllegalArgumentException();
//		}
//	}
//	
//}
//
