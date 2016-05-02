package view.enums;

public enum Indexes {

    GAME_NAME(0),
    GAME_DESC(1),
    GAME_ICON(2),
    XML_GAME_DETAILS(0),
    GAME_FIRST_LEVEL(3);


    private final int index;

    /**
     * creates new size enum for component name
     *
     * @param size size for component
     */
    Indexes (int index) {
        this.index = index;
    }

    /**
     * returns int size for given enum name
     *
     * @return int size
     */
    public int getIndex () {
        return index;
    }
}
