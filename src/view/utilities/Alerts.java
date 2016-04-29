package view.utilities;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class Alerts {

	private Alerts(){
		
	}
	
	/**
	 * Show an alert with a certain message and a given type.
	 * 
	 * @param String
	 *            title: title of alert, could be null
	 * @param String
	 *            header: header for the alert, could be null
	 * @param String
	 *            message: longer message further describing the alert, could be
	 *            null (though all three above shouldn't be null at the same
	 *            time or the alert is useless)
	 * @param AlertType
	 *            type: type of alert to display, i.e. ERROR, INFORMATION,
	 *            CONFIRMATION
	 * @return boolean result.get() == ButtonType.OK: returns true is the user
	 *         clicked the OK button, returns false otherwise
	 */

	public static boolean showAlert(String title, String header, String message, AlertType type) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(message);
		Optional<ButtonType> result = alert.showAndWait();
		return (result.get() == ButtonType.OK);
	}
	
	/**
	 * Show an error with a certain message.
	 * 
	 * @param error
	 *            message
	 */

	public static void showError(String title, String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.show();
	}
	
	public static ButtonType confirmationBox(String title, String header, String message) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(message);

		return null;
	}
}
