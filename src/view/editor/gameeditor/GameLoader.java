package view.editor.gameeditor;
/**
 * @author calinelson
 */
import java.io.File;
import java.util.List;

import api.IEntity;
import api.ILevel;
import datamanagement.XMLReader;
import javafx.collections.ObservableList;
import view.enums.DefaultStrings;

public class GameLoader {
	
	public void loadGame(String fileName, GameDetails gameDetails, ObservableList<IEntity> masterEntityList, ObservableList<ILevel> masterEnvironmentList ){
		
        fileName = DefaultStrings.CREATE_LOC.getDefault() + fileName;
        gameDetails.setDetails(new XMLReader<List<String>>().readSingleFromFile(fileName + DefaultStrings.METADATA_LOC.getDefault()));
        masterEntityList.addAll(new XMLReader<List<IEntity>>().readSingleFromFile((fileName + DefaultStrings.ENTITIES_LOC.getDefault())));
        loadLevels(fileName, masterEnvironmentList);
    }


    private void loadLevels(String fileName, ObservableList<ILevel> masterEnvironmentList) {
        fileName = fileName + DefaultStrings.LEVELS_LOC.getDefault();
        File file = new File(fileName);
        for(File f: file.listFiles()){
            masterEnvironmentList.add(new XMLReader<ILevel>().readSingleFromFile(f.getPath()));
        }

    }
	
}


