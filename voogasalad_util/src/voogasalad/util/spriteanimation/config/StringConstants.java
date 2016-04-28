package voogasalad.util.spriteanimation.config;

/**
 * Created by rhondusmithwick on 4/23/16.
 *
 * @author Rhondu Smithwick
 */
public enum StringConstants {
    STYLE_SHEET("voogasalad/util/spriteanimation/resources/darktheme.css"),
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
