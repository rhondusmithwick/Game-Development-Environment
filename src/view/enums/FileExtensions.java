package view.enums;

import javafx.stage.FileChooser.ExtensionFilter;

/**
 * @author calinelson
 *         class representing file extensions used in file choosers in the view
 */

public enum FileExtensions {

    JPG("JPG", "*.jpg"),
    GIF("GIF", "*.gif"),
    PNG("PNG", "*.png"),
    XML("XML", "*.xml"),
    MP3("MP3", "*.mp3");


    private final ExtensionFilter filter;

    /**
     * creates extension filter for file type
     *
     * @param type file type of extension
     * @param ext  file extension
     */
    FileExtensions(String type, String ext) {
        this.filter = new ExtensionFilter(type, ext);
    }

    /**
     * returns the ExtensionFilter for the given enum file type
     *
     * @return ExtensionFilter for a file type
     */
    public ExtensionFilter getFilter() {
        return this.filter;
    }
}
