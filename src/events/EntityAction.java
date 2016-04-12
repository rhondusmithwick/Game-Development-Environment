package events;

import api.IEntity;

import java.util.Map;

/**
 * Created by rhondusmithwick on 4/10/16.
 *
 * @author Rhondu Smithwick
 */
public class EntityAction extends Action {
    private final IEntity entity;
    private final IComponent component; 

    public EntityAction(String script, IEntity entity, IComponent component) {
        super(script);
        this.entity = entity;
        this.component = component; 
    }


    public EntityAction(String script, Map<String, Object> parameters, IEntity entity) {
        super(script, parameters);
        this.entity = entity;
    }

    @Override
    protected void setUp() {
        getEngine().put("entity", entity);
        component.getProperties().get(0).addListener(this);
    }
}
