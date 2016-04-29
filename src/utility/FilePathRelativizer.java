package utility;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by rhondusmithwick on 4/29/16.
 *
 * @author Rhondu Smithwick
 */
public class FilePathRelativizer {
    private FilePathRelativizer () {
    }

    public static String relativize (String otherDirectory) {
        String baseDirectory = System.getProperty("user.dir");
        Path base = Paths.get(baseDirectory);
        Path other = Paths.get(otherDirectory);
        return base.relativize(other).toString();
    }

}
