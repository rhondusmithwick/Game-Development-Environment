package enums;

import model.component.movement.Position;

public enum DefaultStrings {
	
	DEFAULT_LANGUAGE("english"),
	EVENT_EDITOR_NAME("EditorEvent"),
	DEFAULT_ICON("resources/guiImages/default_icon.png"),
	ENTITY_EDITOR_NAME("EditorEntity"),
	ENVIRONMENT_EDITOR_NAME("EditorEnvironment"),
	LANG_LOC("propertyFiles/"), 
	CREATE_LOC("resources/createdGames/"),
	TEMPLATE_LOC("resources/templates/"),
	POSITION_COMP_NAME(Position.class.getName()),
	BACKGROUND_TEMPLATE_PATH("templates/Background"), 
	PLATFORM_TEMPLATE_PATH("templates/PlatformSprite"),
	XML(".xml");

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