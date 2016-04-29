package view.editor.eventeditor;

import java.util.HashMap;

public class EventParser 
{	
	public EventParser()
	{

	}
	
	/*
	 * {S:KEY_PRESSED=[resources/groovyScripts/ACAddGravity.groovy], A:KEY_PRESSED=[resources/providedScripts/SaveGame.groovy], S:KEY_PRESSED=[resources/groovyScripts/ACGamePositionHandler.groovy]}
	 */
	public HashMap<String, String> parseKeyEvent(String string)
	{
		System.out.println(string);
		
		if ( string.equals("{}") )
			return null;
		
		HashMap<String, String> parseResults = new HashMap<String, String>();
		
		// Take out the curly braces
		string = string.substring(1, string.length() - 2);
		
		String[] events = string.split(",");
		String key;
		String[] filepath;
		String action;
		for (String event: events)
		{
			key = event.split("=")[0].split(":")[0].trim();
			action = event.split("=")[1];
			
			// Take out the square braces
			action = action.substring(1, action.length() - 1);
			filepath = action.split("/");
			action = filepath[filepath.length - 1];
			
			parseResults.put(key, action);
		}
		
		return parseResults;
	}
}
