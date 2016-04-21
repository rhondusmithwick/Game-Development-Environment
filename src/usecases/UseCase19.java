package usecases;

import api.ISystemManager;
import model.core.SystemManager;

/**
 * Created by rhondusmithwick on 3/31/16.
 *
 * @author Rhondu Smithwick
 */
public class UseCase19 {
    private final ISystemManager systemManager = new SystemManager();

    void doUseCase() {
        systemManager.pauseLoop();
    }
}
