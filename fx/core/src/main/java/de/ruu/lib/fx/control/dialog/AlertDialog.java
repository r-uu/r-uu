package de.ruu.lib.fx.control.dialog;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public abstract class AlertDialog
{
	public static void showAndWait(String content)
	{
		showAndWait(content, AlertType.INFORMATION);
	}

	public static void showAndWait(String content, AlertType type)
	{
		Alert alert = new Alert(type);
		alert.setContentText(content);
		alert.showAndWait();
	}
}
