/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Malaka
 *
 * here there are some common things this is singleton class
 */
public class UICommonController {

    private static UICommonController instance;

    private UICommonController() {
    }

    public static UICommonController getInstance() {
        if (instance == null) {
            instance = new UICommonController();
        }
        return instance;
    }

    public Object openFXMLWindow(String path, Modality m, boolean resize, String title) {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("/view/" + path));
            Parent root = fXMLLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(resize);
            stage.initModality(m);
            stage.setTitle(title);
            stage.show();
            return fXMLLoader.getController();
        } catch (IOException iOException) {
            //log
            iOException.printStackTrace();
        }
        return null;
    }

    public Object openFXMLWindow(String path, Modality m, boolean resize, String title, boolean wait, boolean centerScreen) {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("/view/" + path));
            Parent root = fXMLLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(resize);
            stage.initModality(m);
            stage.setTitle(title);

            if (centerScreen) {
                stage.centerOnScreen();
            }

            if (wait) {
                stage.showAndWait();
            } else {
                stage.show();
            }

            return fXMLLoader.getController();
        } catch (IOException iOException) {
            //log
            iOException.printStackTrace();
        }
        return null;
    }

    public static Optional<ButtonType> showAlertBox(AlertType type, String msg, String title, String hedder, ButtonType... elements) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(hedder);
        if (elements != null) {
            alert.getButtonTypes().clear();
            alert.getButtonTypes().addAll(elements);
        }
        alert.setContentText(msg);
        return alert.showAndWait();
    }
    public static Optional<ButtonType> showAlertBox(AlertType type, String msg, String title, String hedder){
        return showAlertBox(type, msg, title, hedder, ButtonType.OK);
    }
        
    
    public static Optional<ButtonType> showAlertBox(AlertType type, String msg, String title){
        return showAlertBox(type, msg, title, null);
    }
    
    public static interface CommonHeadding{
        String EMPTY_FIELDS = "Following fields should not be empty";
        String INVALID_FORMATTINGS = "Following fields have invalid formattings";
    }
    
    public static interface CommonTitles{
        String VALIDATING_ERROR = "Validating Error";
        String FORMATTING_ERROR = "Formatting Error";
    }
    
}
