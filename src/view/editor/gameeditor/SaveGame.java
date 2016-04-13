package view.editor.gameeditor;

import java.util.List;
import api.ISerializable;
import enums.Indexes;

public class SaveGame implements ISerializable {
	
	
	private List<ISerializable> environments;
	private List<ISerializable> entities;
	private List<String> details;

	public SaveGame(List<String> details, List<ISerializable> entities, List<ISerializable> environments){
		this.details = details;
		this.entities = entities;
		this.environments = environments;
	}

	public String getName(){
		return details.get(Indexes.GAME_NAME.getIndex());
	}
	public String getDesc(){
		return details.get(Indexes.GAME_DESC.getIndex());
	}
	public String getIcon(){
		return details.get(Indexes.GAME_ICON.getIndex());
	}
	
	public List<ISerializable> getEntites(){
		return entities;
	}
	
	public List<ISerializable> getEnvironments(){
		return environments;
	}
}
