package view.enums;

import javafx.geometry.Insets;

/**
 * this class is an enum of the insets for each area of the border pane
 *
 * @author calinelson
 */

public enum ViewInsets {
    GAME_EDIT(new Insets(20, 20, 20, 20)),
    EVENT_EDIT(new Insets(10, 10, 10, 10)),
    LOOP_EDIT(new Insets(20, 20, 20, 20));

    private final Insets inset;

    /**
     * creates new insets object for a borderpane area
     *
     * @param i insets object to use for enum
     */
    ViewInsets(Insets i) {
        this.inset = i;

    }

    /**
     * returns insets for border pane area based on area name
     *
     * @return insets object
     */
    public Insets getInset() {
        return this.inset;
    }

}
