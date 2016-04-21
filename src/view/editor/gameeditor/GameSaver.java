package view.editor.gameeditor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import api.IEntity;
import api.ILevel;
import datamanagement.XMLWriter;
import enums.DefaultStrings;
import enums.Indexes;
import javafx.collections.ObservableList;


public class GameSaver {

	
	@SuppressWarnings("unchecked")
	public void saveGame(ObservableList<ILevel> levels, ObservableList<IEntity> entityList, List<String> details){
		String fileName = getDirectory(details);
		new XMLWriter<List<String>>().writeToFile(fileName + "/metadata.xml", details);
		new XMLWriter<List<IEntity>>().writeToFile(fileName+"/entities.xml", new ArrayList<IEntity>(entityList));
		saveLevels(levels, fileName);
	}


	private void saveLevels(ObservableList<ILevel> levels, String fileName) {
		File levelFile = new File(fileName + "/levels");
		levelFile.mkdirs();
		for(ILevel level: levels){
			new XMLWriter<ILevel>().writeToFile(fileName + "/levels/" + level.getName() + ".xml", level);
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
