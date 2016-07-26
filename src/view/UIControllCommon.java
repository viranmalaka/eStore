/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Malaka
 * 
 * here there are some common things 
 */
public class UIControllCommon {
    private static Object o;
    public static void openFXMLWindow(Object x, String path, Modality m, boolean resize){
        try {
            FXMLLoader fXMLLoader = new FXMLLoader(x.getClass().getResource("/view/" + path));
            Parent root = fXMLLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(resize);
            stage.initModality(m);
            stage.show();
        } catch (IOException iOException) {
            //log
            iOException.printStackTrace();
        }
    }
    
    public static void openFXMLWindow(Object x, String path){
        openFXMLWindow(x, path, Modality.NONE, true);
    }
    
    
    
}
