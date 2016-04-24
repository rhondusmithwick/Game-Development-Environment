package animation;

/**
 * Created by rhondusmithwick on 4/23/16.
 *
 * @author Rhondu Smithwick
 */
public enum StringConstants {
    STYLE_SHEET("cssFiles/darktheme.css"),
    SAVE_ANIMATIONS_TO_FILE("Save Animations to File"),
    NEW_ANIMATION("New Animation"),
    NEW_SPRITE("New Sprite"),
    PREVIEW_ANIMATION("Preview Animation"),
    SAVE_ANIMATION("Save Animation"),
    ADD_FRAME("Add Frame"),
    DELETE_FRAME("Delete Frame"),
    ANIMATION_NAME_PROMPT("Enter Animation Name."),
    SELECT_EFFECT("-fx-effect: dropshadow(three-pass-box, rgba(0,0,50,0.8), 10, 0, 0, 0)"),
    NO_SELECT_EFFECT("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0), 0, 0, 0, 0)");


    private final String content;

    StringConstants(String content) {
        this.content = content;
    }

    public String get() {
        return content;
    }
}
