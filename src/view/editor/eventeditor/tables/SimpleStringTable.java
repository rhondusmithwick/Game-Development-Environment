package view.editor.eventeditor.tables;

import java.util.Collection;

/**
 * A Table that just contains Strings.
 *
 * @author Alankmc
 */
public class SimpleStringTable extends Table {

    public SimpleStringTable (TableManager manager, String name) {
        super(manager, name, null, null);

    }

    /**
     * Pass in a ArrayList of Strings.
     */

    @Override
    public void fillEntries (Object dataHolder) {
        for (String string : ((Collection<String>) dataHolder)) {
            getEntries().add(new Entry(string, string));
        }
    }

}
