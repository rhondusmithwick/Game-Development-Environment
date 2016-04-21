package view.editor.gameeditor;

import java.io.File;
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
		String fileName = DefaultStrings.CREATE_LOC.getDefault() + details.get(Indexes.GAME_NAME.getIndex());
		File file = new File(fileName);
		if(!file.exists()){
			file.mkdirs();
		}
		
		new XMLWriter<List<String>>().writeToFile(fileName + "/metadata.xml", details);
	}
}
