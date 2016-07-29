
import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.UIControllCommon;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Malaka
 */
public class MainFX extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        UIControllCommon.getInstance().openFXMLWindow("MainWindow.fxml", Modality.NONE, true,"eStore - CapricornSoft Corporation");

    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
}
