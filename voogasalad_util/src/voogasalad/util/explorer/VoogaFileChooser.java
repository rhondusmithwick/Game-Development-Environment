package voogasalad.util.explorer;



import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * A file chooser that relocates files to a local resource in order to access them.
 * 
 * @author DoovalSalad
 *
 */
public class VoogaFileChooser{
	
	/**
	 * Constants
	 */
    private static final String USER_RESOURCES_PATH = "user_resources/";
    private static final String LOCAL_PATH= "voogasalad/util/explorer/";
    private static final String ALREADY_EXISTS = "This filename already exits";
    private static final String CANNOT_IMPORT = "Could not import file";
    
    private FileChooser fileChooser;
    
    public VoogaFileChooser(){
        fileChooser = new FileChooser();
    }
    
    /**
     * Opens the file chooser and moves the file accordingly.
     * @return
     * @throws VoogaException
     */
    public String launch() throws VoogaException{
        File file = fileChooser.showOpenDialog(null);
        if (isLocal(file)){
            String path = file.getPath().split(LOCAL_PATH)[1];
            return path;
        } else {
            String path= moveToLocalPath(file);
            return path;
        }
    }
    
    /**
     * Filters based on valid extensions.
     * @param filters
     */
    public void addFilters(ExtensionFilter... filters){
        fileChooser.getExtensionFilters().addAll(filters); 
    }
    
    /**
     * Determines whether the file is local already.
     * @param file
     * @return
     */
    private boolean isLocal(File file){
        return file.getPath().contains(LOCAL_PATH);
    }
    
    /**
     * Moves the file to a local path and returns this path.
     * @param file
     * @return
     * @throws VoogaException
     */
    private String moveToLocalPath(File file) throws VoogaException{
        String path = USER_RESOURCES_PATH+file.getName();
        File newLocation = new File(path);
        
        if (newLocation.exists()){
            throw new VoogaException(ALREADY_EXISTS);
        }
        
        try {
            Files.copy(file.toPath(), newLocation.toPath(), StandardCopyOption.COPY_ATTRIBUTES);
        }
        catch (IOException e) {
        	e.printStackTrace();
            throw new VoogaException(CANNOT_IMPORT);
        }

        return path;
    }
    
    

}
