
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.MainWindowController;
import view.UICommonController;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Malaka
 */
public class MainFX extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader createFXML = UICommonController.getInstance().createFXML("MainWindow.fxml");
        Stage stage = UICommonController.getInstance().getStage(createFXML);
        stage.initModality(Modality.NONE);
        stage.setResizable(true);
        stage.setTitle("eStore - CapricornSoft Corporation");
        
        stage.show();  
    }

    public static void main(String[] args) {
        launch(args);
    }
}
