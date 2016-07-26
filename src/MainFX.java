
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
        FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("/view/MainWindow.fxml"));
        Parent root = fXMLLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
}
