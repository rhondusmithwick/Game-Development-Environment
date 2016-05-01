package view.editor.eventeditor;

import java.util.HashMap;

/**
 * Every time an Event's toString() method is called, a different format of String is received.
 * This class splices that String so the information can be displayed to the user neatly.
 *
 * @author Alankmc
 */
public class EventParser {
    /*
	 * {S:KEY_PRESSED=[resources/groovyScripts/ACAddGravity.groovy], A:KEY_PRESSED=[resources/providedScripts/SaveGame.groovy], S:KEY_PRESSED=[resources/groovyScripts/ACGamePositionHandler.groovy]}
	 * {PropertyTrigger; 013d935c-468f-458b-9fe4-7e97768e7d29; Sprite; ImageHeight=[resources/providedScripts/SaveGame.groovy], PropertyTrigger; 013d935c-468f-458b-9fe4-7e97768e7d29; Health; Health=[resources/groovyScripts/ACGamePositionHandler.groovy]}
	 * {PropertyTrigger; b61c3e45-9f66-43e1-89f4-097b89e8f120; Defense; ObjectProperty [bean: utility.SingleProperty@52b16026, name: Defense, value: 25.0]=[events.Action@3c2deb52], A=[events.Action@f1fe7ad]}

	 * {A=[resources/groovyScripts/ACGamePositionHandler.groovy]}

	 */

    /**
     * Parses the full input String, and returns a HashMap of all the names of the Trigger - Action tuples.
     *
     * @param String string
     * @return HashMap<String, String>
     */
    public HashMap<String, String> parse (String string) {
        System.out.println(string);

        if (string.equals("{}"))
            return null;


        // Take out the curly braces
        string = string.substring(1, string.length() - 2);

        if (string.split(";")[0].equals("PropertyTrigger")) {
            return parsePropertyEvent(string);
        } else {
            return parseKeyEvent(string);
        }
    }

    /**
     * Called internally if a propertyEvent is recognized.
     *
     * @param string
     * @return HashMap<String, String>
     */
    private HashMap<String, String> parsePropertyEvent (String string) {
        HashMap<String, String> parseResults = new HashMap<>();

        String[] events = string.split(",");
        String trigger;
        String[] triggerSplit;

        for (String event : events) {
            triggerSplit = event.split(";");
			/*
			trigger = triggerSplit[1].trim() + ":" +
					triggerSplit[2].trim() + ":" +
					triggerSplit[3].trim().split("=")[0];
					*/

//			trigger = triggerSplit[3].trim().split("=")[0];
//
//			parseResults.put(trigger, getFilename(triggerSplit[3].split("=")[1]));
        }

        return parseResults;
    }

    /**
     * Splices the filepath String, returning only the file name.
     *
     * @param string
     * @return HashMap<String, String>
     */
    private String getFilename (String string) {
        string = string.substring(1, string.length() - 1);
        String[] filepath = string.split("/");

        return filepath[filepath.length - 1];

    }

    /**
     * Called internally, if a Key Event is recognized from the String.
     *
     * @param string
     * @return HashMap<String, String>
     */
    // {A=[events.Action@f1fe7ad]}
    private HashMap<String, String> parseKeyEvent (String string) {
        HashMap<String, String> parseResults = new HashMap<>();

        String[] events = string.split(",");
        String key;

        for (String event : events) {
            key = event.split("=")[0].split(":")[0].trim();

            //parseResults.put(key, getFilename(event.split("=")[1]));
        }

        return parseResults;
    }
}
