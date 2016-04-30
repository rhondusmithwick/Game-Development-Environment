package guiObjects;

import java.io.File;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleObjectProperty;
import javafx.stage.FileChooser.ExtensionFilter;
import utility.FilePathRelativizer;
import view.utilities.FileUtilities;
/**
 * 
 * @author calinelson
 *
 */
public abstract class GuiObjectFileGetter extends GuiObject{
	public GuiObjectFileGetter(String name, String resourceBundle) {
		super(name, resourceBundle);
	}

	
	protected void setFile(File file, SimpleObjectProperty<String> property){
		if(file==null){
			return;
		}
		property.set(FilePathRelativizer.relativize(file.getPath()));
		setPreview(file);
		
	}
	
	protected void changeValue(SimpleObjectProperty<String> property, ResourceBundle myResources, String dir, List<ExtensionFilter> filters){
		File file = getFile(myResources, dir, filters);
		setFile(file, property);
	}
	
	protected File getFile(ResourceBundle myResources, String dir, List<ExtensionFilter> filters){
		return FileUtilities.promptAndGetFile(filters, myResources.getString("ChooseFile"), dir);
	}
	
	protected abstract void setPreview(File file);


	@Override
	public abstract Object getCurrentValue();

	@Override
	public abstract Object getGuiNode();

}
