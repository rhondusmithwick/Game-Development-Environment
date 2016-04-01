package usecases;

import api.IEnvironmentEditor;

public class UseCase24 {

    // game creator adds music to an environment
    void doUseCase() {
        IEnvironmentEditor myEditor = new EnvironmentEditor();
        myEditor.show();
        //waiting for user to enter data and press create button
        //once create button is pressed
        String musicURL = null; //get user input from editor
        myEditor.writeToFile(musicURL);
        myEditor.close();
    }


}
