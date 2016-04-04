package enums;

public enum DefaultStrings {

	DISPLAY_LOC("resources/guiStrings/english"),
    BACKGROUND_COLOR("-fx-background-color: cornflowerblue");



    private final String content;

    /**
     * creates default for string str
     *
     * @param str default string
     */
    DefaultStrings(String str) {
        this.content = str;
    }

    /**
     * returns default string
     *
     * @return default string for enum
     */
    public String getDefault() {
        return this.content;
    }
	
	
	
	
}
