package usecases;

import api.ISystemManager;
import model.core.SystemManager;

/**
 * Created by rhondusmithwick on 3/31/16.
 *
 * @author Rhondu Smithwick
 */
@SuppressWarnings("unused")
public class UseCase40 {

    private final ISystemManager systemManager = new SystemManager();
    //private final IEnvironmentEditor editor = new EnvironmentEditor();

    void doUseCase () {
        systemManager.pauseLoop(); // stop the game
        //editor.show();  // show the editor
    }

}
