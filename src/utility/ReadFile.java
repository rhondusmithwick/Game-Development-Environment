package utility;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;

/**
 * Read everything in a file to a String.
 *
 * @author Rhondu Smithwick
 */
public final class ReadFile {
    private ReadFile () {
    }

    public static String readFile (String fileName) {
        String fileString = "";
        try {
            fileString = Files.toString(new File(fileName), Charsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Problem reading file " + fileName);
        }
        return fileString;
    }
}
