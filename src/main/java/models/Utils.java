package models;

import javafx.scene.control.Alert;

public class Utils {
	public static void showAlertDialog(String title, String headerText, String contentText) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.showAndWait();
	}
}
