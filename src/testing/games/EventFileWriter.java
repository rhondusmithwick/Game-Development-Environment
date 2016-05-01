package testing.games;

import datamanagement.XMLWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EventFileWriter {

    private final List<String> eventList = new ArrayList<>();

    public void addEvent (String scriptPath, String... args) {
        String toAdd = getEventID(scriptPath, args);
        eventList.add(toAdd);
    }

    public void removeEvent (String scriptPath, String... args) {
        String toRemove = getEventID(scriptPath, args);
        eventList.remove(toRemove);
    }

    private String getEventID (String scriptPath, String... args) {
        String temp = "";
        for (String s : args) {
            temp += s + ":";
        }
        temp = temp.substring(0, temp.length() - 1);
        temp = temp + "|" + scriptPath;
        return temp;
    }

    public File writeEventsToFile (String filePath) {
        return new XMLWriter<String>().writeToFile(filePath, eventList);
    }

    public String writeEventsToString (String filePath) {
        return new XMLWriter<String>().writeToString(eventList);
    }
}
