package utility;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by rhondusmithwick on 4/29/16.
 *
 * @author Rhondu Smithwick
 */
public class FilePathRelativizer {
    private static FilePathRelativizer instance;
    private String baseDirectory;

    private FilePathRelativizer () {
    }

    public static FilePathRelativizer getInstance () {
        if (instance == null) {
            instance = new FilePathRelativizer();
            instance.setBaseDirectory(System.getProperty("user.dir"));
        }
        return instance;
    }

    private void setBaseDirectory (String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    public String relativize (String otherDirectory) throws URISyntaxException {
        Path base = Paths.get(baseDirectory);
        Path other = Paths.get(otherDirectory);
        return base.relativize(other).toString();
    }
}
