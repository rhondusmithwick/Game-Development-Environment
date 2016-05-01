package view.utilities;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.Vooga;
import view.enums.GUISize;
/**
 * Create new main menu instance and set stage to it
 * @author calinelson
 *
 */
public class ToMainMenu {
	/**
	 * send program to main menu
	 * @param pane pane used to get scene and window from
	 */
	public static void toMainMenu(Pane pane){
        Stage myStage = (Stage) pane.getScene().getWindow();
        myStage.setWidth(GUISize.MAIN_SIZE.getSize());
        myStage.setHeight(GUISize.MAIN_SIZE.getSize());
        Vooga vooga = new Vooga(myStage);
        vooga.init();
	}
}
