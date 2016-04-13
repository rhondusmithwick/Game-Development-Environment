package view.editor.gameeditor;

import java.util.List;
import model.entity.EntitySystem;
import api.ISerializable;
import enums.Indexes;
import model.component.visual.ImagePath;
import model.entity.Entity;

public class SaveGame implements ISerializable {


    private List<ISerializable> environments;
    private List<ISerializable> entities;
    private List<String> details;

    public SaveGame(List<String> details, List<ISerializable> entities, List<ISerializable> environments){
        this.details = details;
        this.entities = entities;
        removeImageViewsEntities();
        this.environments = environments;
        removeImageViewsEnv();
    }

    private void removeImageViewsEnv () {
        for(ISerializable e: environments){
            for(ImagePath i :     ((EntitySystem) e).getAllComponentsOfType(ImagePath.class)){
                i.setForSave();
            }
       }
        
        
    }

    private void removeImageViewsEntities () {
        for(ISerializable e: entities){
            ImagePath i = ((ImagePath) ((Entity) e).getComponent(ImagePath.class));
            i.setForSave();
        }

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

    
    private void reloadImageViewsEnv () {
        for(ISerializable e: environments){
            for(ImagePath i :     ((EntitySystem) e).getAllComponentsOfType(ImagePath.class)){
                i.reloadImageView();
            }
       }
        
        
    }

    private void reloadImageViewsEntities () {
        for(ISerializable e: entities){
            ImagePath i = ((ImagePath) ((Entity) e).getComponent(ImagePath.class));
            i.reloadImageView();
        }

    }
    
    
    
    
    public List<ISerializable> getEntites(){
        reloadImageViewsEntities();
        return entities;
    }



    public List<ISerializable> getEnvironments(){
        reloadImageViewsEnv();
        return environments;
    }
}
