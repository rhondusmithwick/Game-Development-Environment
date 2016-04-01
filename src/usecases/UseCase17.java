package usecases;
import api.IEntityEditor;
public class UseCase17 {
	
	// game creator adds power up
	void doUseCase(){
	IEntityEditor myEditor = new EntityEditor();
	myEditor.show();
	//waiting for user to enter data and press create button
	//once create button is pressed
	String filename =  null; //file name for current game
	myEditor.writeToFile(filename);
	myEditor.close();
	}
	

}
