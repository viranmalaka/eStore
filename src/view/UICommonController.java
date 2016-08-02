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
import javafx.scene.control.Dialog;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logger.LogController;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author Malaka
 *
 * here there are some common things this is singleton class
 */
public class UICommonController {
    private static UICommonController instance = new UICommonController();
    private UICommonController() {
    }

    public static UICommonController getInstance() {
        return instance;
    }

    public FXMLLoader createFXML(String path) {
        return new FXMLLoader(getClass().getResource("/view/" + path));
    }

    public Stage getStage(FXMLLoader fXMLLoader) {
        try {
            Parent root = fXMLLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            return stage;
        } catch (IOException ex) {
            LogController.log(Level.ERROR, ex.toString());
        }
        return null;
    }


//<editor-fold defaultstate="collapsed" desc="Dialog Box Things">

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

    public static Optional<ButtonType> showAlertBox(AlertType type, String msg, String title, String hedder) {
        return showAlertBox(type, msg, title, hedder, ButtonType.OK);
    }

    public static Optional<ButtonType> showAlertBox(AlertType type, String msg, String title) {
        return showAlertBox(type, msg, title, null);
    }

    public static interface CommonHeadding {

        String EMPTY_FIELDS = "Following fields should not be empty";
        String INVALID_FORMATTINGS = "Following fields have invalid formattings";
    }

    public static interface CommonTitles {

        String VALIDATING_ERROR = "Validating Error";
        String FORMATTING_ERROR = "Formatting Error";
    }

//</editor-fold>
}
