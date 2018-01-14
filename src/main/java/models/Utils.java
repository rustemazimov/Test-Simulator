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

	public static class PasswordStrengthChecker {
		private static String homePassword;
		public static boolean isPasswordStrong(String password) {
			if (password.length() < 10)
			{
				return false;
			}
			homePassword = password;
			boolean isStrong = hasNumber() && hasUpperCase() && hasLowerCase();
			homePassword = null;
			return isStrong;
		}
		/*==============================   Helpers     ====================================*/
		private static boolean hasNumber() {
			return isBetween('0', '9');
		}

		private static boolean hasUpperCase() {
			return isBetween('A', 'Z');
		}

		private static boolean hasLowerCase() {
			return isBetween('a', 'z');
		}

		private static boolean isBetween(char first, char last) {
			for (int i = 0; i < homePassword.length(); i++) {
				char c = homePassword.charAt(i);
				if (c >= first && c <= last)
				{
					return true;
				}

			}
			return false;
		}
	}
}
