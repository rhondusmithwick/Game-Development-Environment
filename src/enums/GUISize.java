package enums;

public enum GUISize {

	TOP_TAB(25),
    AUTHORING_START(20);

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
