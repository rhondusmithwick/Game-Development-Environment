package voogasalad.util.hud.source;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * Main Controller class for the HUD package.
 * Contains a HUDModel, an implementation of an
 * AbstractHUDSCreen, and a dataSource
 * @author bobby
 *
 */



public class HUDController implements Observer{
	
	private HUDModel model;
	private AbstractHUDScreen view;
	
	
	/**
	 * Initializes the HUD by setting up the model and view.
	 * @param filename - file where you saved your preferences from authoring environment
	 * @param dataSource - object that holds all of your relevant game data, or references to other classes
	 * that contain it
	 * @param valueFinder - you need to write a custom ValueFinder class for your own project
	 */
	
	
	public void init(String filename, Object dataSource, IValueFinder valueFinder) {
		setModel(new HUDModel());
		valueFinder.setController(this);
		valueFinder.setDataSource(dataSource);
		
		List<String> fieldsToObserve = getFieldsToFollow(filename);
		Map<Integer, String> rowToValueMap = new HashMap<>();
		for (int i = 0; i<fieldsToObserve.size(); i++) {
			Property myProperty = valueFinder.find(fieldsToObserve.get(i));
			model.getData().put(myProperty.getFieldName(), myProperty);
			rowToValueMap.put(i, myProperty.getFieldName());
		}
		setView(new HUDScreen(model.getData(), rowToValueMap));
	}
	
	
	/**
	 * Very simple file reader that returns the fields to follow based
	 * on the input from a text file
	 * @param filename
	 * @return
	 */
	
	private List<String> getFieldsToFollow(String filename) {
		List<String> params = new ArrayList<>();
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
			String currentLine = bufferedReader.readLine();

			while(currentLine != null) {
				String trimmedWord = currentLine.trim();
				if (trimmedWord.length() != 0) {
					params.add(trimmedWord);
				}
				currentLine = bufferedReader.readLine();
			}
			bufferedReader.close();
		} 
		catch (IOException e) {
			System.err.println("A error occured reading file: " + e);
			e.printStackTrace();
		}
		return params;
	}
	
	
	
	
	/**
	 * Sets the model
	 * @param model
	 */
	
	private void setModel(HUDModel model) {
		this.model = model;
	}
	
	/**
	 * Sets the view
	 * @param view
	 */
	
	private void setView(AbstractHUDScreen view) {
		this.view = view;
	}
	
	
	
	/**
	 * Handles the change whenever any object (most likely Property)
	 * that this is observing has changed. It does so by telling both the
	 * model and view to update themselves.
	 */
	
	@Override
	public void update(Observable o, Object arg) {
		ValueChange change = (ValueChange) arg;
		model.handleChange(change);
		view.handleChange(change);
	}
	
	/**
	 * Returns the view
	 * @return AbstractHUDScreen
	 */
	public AbstractHUDScreen getView() {
		return view;
	}

}
