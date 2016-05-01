package guiObjects;

import java.io.File;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleObjectProperty;
import javafx.stage.FileChooser.ExtensionFilter;
import utility.FilePathRelativizer;
import view.utilities.FileUtilities;
/**
 * abstract class to provide frameworks for file getters
 * @author calinelson
 *
 */
public abstract class GuiObjectFileGetter extends GuiObject{
	/**
	 * constructor to make filegetter
	 * @param name name of property 
	 * @param resourceBundle string of property bundle
	 */
	public GuiObjectFileGetter(String name, String resourceBundle) {
		super(name, resourceBundle);
	}

	/**
	 * set selected file
	 * @param file file to set
	 * @param property property to set value of
	 */
	protected void setFile(File file, SimpleObjectProperty<String> property){
		if(file==null){
			return;
		}
		property.set(FilePathRelativizer.relativize(file.getPath()));
		setPreview(file);
		
	}
	
	/**
	 * change value of property
	 * @param property property to change value of
	 * @param myResources display string resource bundle
	 * @param dir directory to look in
	 * @param filters file filters
	 */
	protected void changeValue(SimpleObjectProperty<String> property, ResourceBundle myResources, String dir, List<ExtensionFilter> filters){
		File file = getFile(myResources, dir, filters);
		setFile(file, property);
	}
	
	/**
	 * get User selected file
	 * @param myResources display strings resource bundle
	 * @param dir directory to look in
	 * @param filters file filters to user
	 * @return selected file
	 */
	protected File getFile(ResourceBundle myResources, String dir, List<ExtensionFilter> filters){
		return FileUtilities.promptAndGetFile(filters, myResources.getString("ChooseFile"), dir);
	}
	
	/**
	 * set preview of object determined by file
	 * @param file selected file
	 */
	protected abstract void setPreview(File file);


	@Override
	public abstract Object getCurrentValue();

	@Override
	public abstract Object getGuiNode();

}
