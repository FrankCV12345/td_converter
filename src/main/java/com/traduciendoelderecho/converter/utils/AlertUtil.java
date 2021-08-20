package com.traduciendoelderecho.converter.utils;

import javafx.scene.control.Alert;

public class AlertUtil {

    private AlertUtil() {
    }

    public static void showAlert(String title, String msg, Alert alert) {
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
