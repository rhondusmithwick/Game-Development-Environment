package utility;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import view.utilities.Alerts;

/**
 * Created by rhondusmithwick on 4/29/16.
 *
 * @author Rhondu Smithwick
 */
public class FilePathRelativizer {
    private FilePathRelativizer () {
    }

    public static String relativize (String otherDirectory, ResourceBundle myResources) {
        String baseDirectory = System.getProperty("user.dir");
        Path base = Paths.get(baseDirectory);
        Path other = Paths.get(otherDirectory);
        try {
            return base.relativize(other).toString();
        } catch (IllegalArgumentException e) {
            Alerts.showError(myResources.getString("error"), myResources.getString("cannotRelativize"));
            return otherDirectory;
        }
    }

}
