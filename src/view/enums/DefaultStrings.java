package view.enums;

import model.component.movement.Position;

public enum DefaultStrings {
	
	DEFAULT_LANGUAGE("english"),
	EVENT_EDITOR_NAME("EditorEvent"),
	GUI_IMAGES("resources/images"),
	DEFAULT_ICON("resources/guiImages/default_icon.png"),
	ENTITY_EDITOR_NAME("EditorEntity"),
	ENVIRONMENT_EDITOR_NAME("EditorEnvironment"),
	LANG_LOC("languages/"),
	COMPONENT_LOC("propertyFiles/componentLocations"),
	SCRIPTS_LOC("propertyFiles/scriptLocations"),
	CREATE_LOC("resources/createdGames/"),
	TEMPLATE_BUNDLE_LOC("templates/"),
	POSITION_COMP_NAME(Position.class.getName()),
	XML(".xml"), 
	CSS_LOCATION("resources/cssFiles/"),
	MAIN_CSS("main.css"), 
	RHONDU("resources/testing/RhonduSmithwick.JPG"), 
	THEME("finalCountdown.mp3"), 
	MUSIC("resources/music/"),
	SOUNDFX("resources/soundfx/"),
	METADATA_LOC("/metadata.xml"),
	ENTITIES_LOC("/entities.xml"),
	LEVELS_LOC("/levels/"),
	TEMP_LIST("_templatelist"),
	DEF_NAME("DefaultName"), 
	GUI_RESOURCES("guiObject/guiComponents"),
	GUI_FACTORY("guiObject/GuiObjectFactory"),
	TEMPLATE_LANG("-templates"),
	TEMPLATE_DIREC_LOC("resources/templates"),
	RESOURCES("resources/"),
	COMPONENTS("-components"),
	 PROPERTIES("-properties");
	


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