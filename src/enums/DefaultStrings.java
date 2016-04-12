package enums;

import model.component.movement.Position;

public enum DefaultStrings {
	
	DEFAULT_LANGUAGE("english"),
	EVENT_EDITOR_NAME("EditorEvent"),
	DEFAULT_ICON("resources/guiImages/default_icon.png"),
	ENTITY_EDITOR_NAME("EditorEntity"),
	ENVIRONMENT_EDITOR_NAME("EditorEnvironment"),
	CREATE_GAME_LABEL("createGameButtonLabel"),
	PLAY_GAME_LABEL("playGameButtonLabel"),
	LANG_LOC("propertyFiles/"), 
	EDITOR_FACTORY_ERROR("editCreate"), 
	ERROR("error"), 
	NO_ENTITIES("noEntitiesError"),
	CREATE_LOC("resources/createdGames/"),
	XML(".xml"),
	TEMPLATE_LOC("resources/templates/"),
	POSITION_COMP_NAME(Position.class.getName());

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