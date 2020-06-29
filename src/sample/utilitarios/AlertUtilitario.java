package sample.utilitarios;

import javafx.scene.control.Alert;

public class AlertUtilitario {
    public static void showAlert(String title, String msg, Alert alert){
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
