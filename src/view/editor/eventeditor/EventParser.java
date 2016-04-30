package view.editor.eventeditor;

import java.util.HashMap;

public class EventParser 
{	
	public EventParser()
	{

	}
	
	/*
	 * {S:KEY_PRESSED=[resources/groovyScripts/ACAddGravity.groovy], A:KEY_PRESSED=[resources/providedScripts/SaveGame.groovy], S:KEY_PRESSED=[resources/groovyScripts/ACGamePositionHandler.groovy]}
	 * {PropertyTrigger; 013d935c-468f-458b-9fe4-7e97768e7d29; Sprite; ImageHeight=[resources/providedScripts/SaveGame.groovy], PropertyTrigger; 013d935c-468f-458b-9fe4-7e97768e7d29; Health; Health=[resources/groovyScripts/ACGamePositionHandler.groovy]}
	 * {A=[resources/groovyScripts/ACGamePositionHandler.groovy]}

	 */
	public HashMap<String, String> parse(String string)
	{
		System.out.println(string);
		
		if ( string.equals("{}") )
			return null;
		
		
		// Take out the curly braces
		string = string.substring(1, string.length() - 2);
		
		if ( string.split(";")[0].equals("PropertyTrigger") )
		{
			return parsePropertyEvent(string);
		}
		else  
		{
			return parseKeyEvent(string);
		}
	}
	
	private HashMap<String, String> parsePropertyEvent(String string)
	{
		HashMap<String, String> parseResults = new HashMap<String, String>();

		String[] events = string.split(",");
		String trigger;
		String[] triggerSplit;
		
		for (String event: events)
		{
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
	
	private String getFilename(String string)
	{
		string = string.substring(1, string.length() - 1);
		String[] filepath = string.split("/");
		
		return filepath[filepath.length - 1];
		
	}
	
	private HashMap<String, String> parseKeyEvent(String string)
	{
		HashMap<String, String> parseResults = new HashMap<String, String>();
		
		String[] events = string.split(",");
		String key;

		for (String event: events)
		{
			key = event.split("=")[0].split(":")[0].trim();
			
			parseResults.put(key, getFilename(event.split("=")[1]));
		}
		
		return parseResults;
	}
}
