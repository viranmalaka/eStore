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
 * this is singleton class
 */
public class UIControllCommon {
    private static UIControllCommon instance;
    
    private UIControllCommon() {
    }
    
    public static UIControllCommon getInstance(){
        if (instance == null) {
            instance = new UIControllCommon();
        }
        return  instance;
    }
    
    public Object openFXMLWindow (String path, Modality m, boolean resize,String title){
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
    
    public Object openFXMLWindow(String path, Modality m, boolean resize, String title, boolean wait,boolean centerScreen){
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
            }else{
                stage.show();
            }
            
            return fXMLLoader.getController();
        } catch (IOException iOException) {
            //log
            iOException.printStackTrace();
        }
        return null;
    }
 }
