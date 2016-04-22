package view.enums;

import model.component.movement.Position;

public enum DefaultStrings {
	
	DEFAULT_LANGUAGE("english"),
	EVENT_EDITOR_NAME("EditorEvent"),
	DEFAULT_ICON("resources/guiImages/default_icon.png"),
	ENTITY_EDITOR_NAME("EditorEntity"),
	ENVIRONMENT_EDITOR_NAME("EditorEnvironment"),
	LANG_LOC("propertyFiles/"), 
	COMPONENT_LOC("propertyFiles/componentLocations"),
	CREATE_LOC("resources/createdGames/"),
	TEMPLATE_DIREC_LOC("resources/templates/"),
	TEMPLATE_BUNDLE_LOC("templates/"),
	POSITION_COMP_NAME(Position.class.getName()),
	BACKGROUND_TEMPLATE_PATH("templates/Background"), 
	PLATFORM_TEMPLATE_PATH("templates/PlatformSprite"),
	XML(".xml"), 
	CHARACTER_TEMPLATE_PATH("templates/player"),
	CSS_LOCATION("resources/cssFiles/"),
	MAIN_CSS("main.css"),
	
	//Bruna Defaults
	BACKGROUND_NAME("Default Waterfall Background"),
	BACKGROUND_PATH("resources/images/movingwaterfall.gif"),
	PLATFORM_NAME("Default Mario Platform"),
	PLATFORM_PATH("resources/images/marioplatform.jpeg"),
	CHAR_1_NAME("Character 1"),
	CHAR_1_PATH("resources/images/white.png"),
	CHAR_2_NAME("Character 2"),
	CHAR_2_PATH("resources/images/blastoise.png");

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