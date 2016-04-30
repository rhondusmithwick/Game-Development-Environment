package view.utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utility.FilePathRelativizer;
import javafx.stage.FileChooser.ExtensionFilter;
import view.enums.DefaultStrings;

public class FileUtilities {
	private FileUtilities(){
		
	}
	/**
	 * Prompts a file chooser box for the user to choose a file with ONE
	 * extension filter
	 * 
	 * @param ExtensionFilter
	 *            extension: file extension that the user can choose from, all
	 *            others are not allowed
	 * @param String
	 *            prompt: prompt for the file chooser box
	 * @param String
	 *            dir: default directory for the file chooser box
	 * @return File: return file selected by the user Note: this method works
	 *         with promptAndGetFile(List<ExtensionFilter> filters, String
	 *         prompt), which allows the addition of multiple extension filters,
	 *         whereas this method is called with only one extension filter is
	 *         needed; pairing the methods reduces replicated code
	 */

	public static File promptAndGetFile(ExtensionFilter extension, String prompt, String dir) {
		List<ExtensionFilter> filters = new ArrayList<ExtensionFilter>();
		filters.add(extension);
		return promptAndGetFile(filters, prompt, dir);
	}
	
	/**
	 * Prompts a file chooser box for the user to choose a file with MULTIPLE
	 * extension filters
	 * 
	 * @param List<ExtensionFilter>
	 *            filters: file extensions that the user can choose from, all
	 *            others are not allowed
	 * @param String
	 *            prompt: prompt for the file chooser box
	 * @param String
	 *            dir: default directory for the file chooser box
	 * @return File file: return file selected by the user
	 */
	public static File promptAndGetFile(List<ExtensionFilter> filters, String prompt, String dir) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(prompt);
		fileChooser.getExtensionFilters().addAll(filters);
		fileChooser.setInitialDirectory(new File(dir));
		File file = new File(FilePathRelativizer.relativize(fileChooser.showOpenDialog(new Stage()).getPath()));
		return file;
	}
	
	public static File promptAndGetFile(List<ExtensionFilter> filters, String prompt) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(prompt);
		fileChooser.getExtensionFilters().addAll(filters);
		File dir = new File(DefaultStrings.RESOURCES.getDefault());
		fileChooser.setInitialDirectory(dir);
		File file = new File(FilePathRelativizer.relativize(fileChooser.showOpenDialog(new Stage()).getPath()));
		return file;
	}
		
	/**
	 * Gets all file names from a given directory. Is static so that it can be
	 * accessed as the actual class is never instantiated, also so that function
	 * can be accessed without this object being passed.
	 *
	 * @param directoryLocation
	 *            String path to a file directory
	 * @return List of Strings of all file names within given directory
	 */
	public static List<String> getAllFromDirectory(String directoryLocation) {

		ArrayList<String> files = new ArrayList<>();
		File directory = new File(directoryLocation);
		File[] fileList = directory.listFiles();
		for (File file : fileList) {
			String name = file.getName();
			if(name.contains(".")){
				files.add(name.substring(0, name.lastIndexOf('.')));
			}
			else{
				files.add(name);
			}
		}
		return files;
	}
	
	public static List<ExtensionFilter> getImageFilters() {
		List<ExtensionFilter> filters = new ArrayList<ExtensionFilter>();
		filters.add(new FileChooser.ExtensionFilter("All Images", "*.*"));
		filters.add(new FileChooser.ExtensionFilter("JPG", "*.jpg"));
		filters.add(new FileChooser.ExtensionFilter("PNG", "*.png"));
		return filters;
	}
	
}
