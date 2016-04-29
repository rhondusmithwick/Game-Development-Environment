package utility;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

/**
 * Created by rhondusmithwick on 4/29/16.
 *
 * @author Rhondu Smithwick
 */
public class FilePathRelativizer {
    private FilePathRelativizer () {
    }

    public static String getBaseDirectory() {
        return System.getProperty("user.dir");

    }
    public static String relativize (String otherDirectory, ResourceBundle myResources) {
        Path base = Paths.get(getBaseDirectory());
        Path other = Paths.get(otherDirectory);
        try {
            return base.relativize(other).toString();
        } catch (IllegalArgumentException e) {
            boolean inner = new File(base.toString(), other.toString()).exists();
            if (inner) {
                return otherDirectory;
            }
            throw new IllegalArgumentException("Relativize problem");
        }
    }

}
