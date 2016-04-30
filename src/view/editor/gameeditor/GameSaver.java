package view.editor.gameeditor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import api.IEntity;
import api.ILevel;
import datamanagement.XMLWriter;
import javafx.collections.ObservableList;
import view.enums.DefaultStrings;
import view.enums.Indexes;


public class GameSaver {

	
	@SuppressWarnings("unchecked")
	public void saveGame(ObservableList<ILevel> levels, ObservableList<IEntity> entityList, List<String> details){
		ArrayList<String> metaData = new ArrayList<>(details);
		if(!levels.isEmpty()){
			metaData.add(levels.get(0).getName());
		}
		String fileName = getDirectory(details);
		new XMLWriter<List<String>>().writeToFile(fileName + DefaultStrings.METADATA_LOC.getDefault(), metaData);
		new XMLWriter<List<IEntity>>().writeToFile(fileName+DefaultStrings.ENTITIES_LOC.getDefault(), new ArrayList<IEntity>(entityList));
		saveLevels(levels, fileName);
	}


	private void saveLevels(ObservableList<ILevel> levels, String fileName) {
		File levelFile = new File(fileName + DefaultStrings.LEVELS_LOC.getDefault());
		levelFile.mkdirs();
		for(ILevel level: levels){
			new XMLWriter<ILevel>().writeToFile(fileName + DefaultStrings.LEVELS_LOC.getDefault() + level.getName() + DefaultStrings.XML.getDefault(), level);
		}
	}

	
	private String getDirectory(List<String> details) {
		String fileName = DefaultStrings.CREATE_LOC.getDefault() + details.get(Indexes.GAME_NAME.getIndex());
		File file = new File(fileName);
		if(file.exists()){
			file.delete();
		}
		file.mkdirs();
		return fileName;
	}
}
