package utility;

import java.io.File;
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

    public static String getBaseDirectory () {
        return System.getProperty("user.dir");

    }

    public static String relativize (String otherDirectory) {
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

    public static String convertToResourceBase (String path, String packagePath) {
        String relativizedPath = relativize(path);
        return packagePath + extractFileName(relativizedPath);

    }

    public static String extractFileName (String filePathName) {
        if (filePathName == null)
            return null;

        int dotPos = filePathName.lastIndexOf('.');
        int slashPos = filePathName.lastIndexOf('\\');
        if (slashPos == -1)
            slashPos = filePathName.lastIndexOf('/');

        if (dotPos > slashPos) {
            return filePathName.substring(slashPos > 0 ? slashPos + 1 : 0,
                    dotPos);
        }

        return filePathName.substring(slashPos > 0 ? slashPos + 1 : 0);
    }

}



