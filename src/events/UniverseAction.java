package events;

import api.IEntitySystem;

/**
 * Created by rhondusmithwick on 4/10/16.
 *
 * @author Rhondu Smithwick
 */
public class UniverseAction extends Action {
    private final IEntitySystem universe;

    public UniverseAction(String script, IEntitySystem universe) {
        super(script);
        this.universe = universe;
    }


    @Override
    protected void setUp() {
        getEngine().put("universe", universe);
    }


}
