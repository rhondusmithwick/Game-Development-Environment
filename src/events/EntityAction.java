package events;

import api.IComponent;
import api.IEntity;
import events.Action;

import java.util.Map;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Created by rhondusmithwick on 4/10/16.
 *
 * @author Rhondu Smithwick
 */
public class EntityAction extends Action{
    private final IEntity entity;
    private final IComponent component; 

    public EntityAction(String script, IEntity entity, IComponent component) {
        super(script);
        this.entity = entity;
        this.component = component; 
    }

    public EntityAction(String script, Map<String, Object> parameters, IEntity entity, IComponent component) {
        super(script, parameters);
        this.entity = entity;
        this.component = component;
    }

    @Override
    protected void setUp() {
        getEngine().put("entity", entity);
        entity.getComponent(component.getClass()).getProperties().get(0).addListener(this);
        //component.getProperties().get(0).addListener(this);
    }


	@Override
	public void changed(ObservableValue arg0, Object arg1, Object arg2) {
		// TODO Auto-generated method stub
		this.activate();
	}
}
