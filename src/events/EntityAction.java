package events;

import api.IEntity;

import javax.script.ScriptException;

/**
 * Created by rhondusmithwick on 4/10/16.
 *
 * @author Rhondu Smithwick
 */
public class EntityAction extends Action {
    private final IEntity entity;

    public EntityAction(String script, IEntity entity) {
        super(script);
        this.entity = entity;
    }

    @Override
    protected void setUp() {
        getEngine().put("entity", entity);
    }
}
