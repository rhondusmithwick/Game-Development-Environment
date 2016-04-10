package enums;

public enum GUISize {

	TOP_TAB(25),
	AUTHORING_HEIGHT(700),
	AUTHORING_WIDTH(1000),
    AUTHORING_START(20),
    MAIN_SIZE(200),
    ORIG_MENU_PADDING(30),
    GAME_EDITOR_PADDING(20),
    GAME_EDITOR_HBOX_PADDING(50),
    ICON_SIZE(50),
	EVENT_EDITOR_PADDING(20),
	EVENT_EDITOR_HBOX_PADDING(10);

    private final int size;

    /**
     * creates new size enum for component name
     *
     * @param size size for component
     */
    GUISize(int size) {
        this.size = size;
    }

    /**
     * returns int size for given enum name
     *
     * @return int size
     */
    public int getSize() {
        return size;
    }
}
