package usecases;

import api.IEntitySystem;
import api.IEventSystem;
import api.ISystem;
import api.ISystemManager;
import javafx.animation.Timeline;

import java.util.List;


/**
 * Created by rhondusmithwick on 3/31/16.
 *
 * @author Rhondu Smithwick
 */
public class SystemManager implements ISystemManager {
    @Override
    public void pauseLoop() {

    }

    @Override
    public Timeline buildLoop() {
        return null;
    }

    @Override
    public void step() {

    }

    @Override
    public List<ISystem> getSystems() {
        return null;
    }

    @Override
    public IEntitySystem getEntitySystem() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IEventSystem getEventSystem() {
        // TODO Auto-generated method stub
        return null;
    }
}
