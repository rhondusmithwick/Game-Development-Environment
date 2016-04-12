package events;

import api.IEntity;

import java.util.Map;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Created by rhondusmithwick on 4/10/16.
 *
 * @author Rhondu Smithwick
 */
public class EntityAction extends Action implements ChangeListener {
    private final IEntity entity;

    public EntityAction(String script, IEntity entity) {
        super(script);
        this.entity = entity;
    }


    public EntityAction(String script, Map<String, Object> parameters, IEntity entity) {
        super(script, parameters);
        this.entity = entity;
    }

    @Override
    protected void setUp() {
        getEngine().put("entity", entity);
    }


	@Override
	public void changed(ObservableValue arg0, Object arg1, Object arg2) {
		// TODO Auto-generated method stub
		this.activate();
	}
}
