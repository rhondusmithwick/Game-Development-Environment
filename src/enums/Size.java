package enums;

public enum Size {

	
    EX_BUTTON(20),
    VIEW_PADDING(10);

    private final int size;

    /**
     * creates new size enum for component name
     *
     * @param size size for component
     */
    Size(int size) {
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
