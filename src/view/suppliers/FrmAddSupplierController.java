/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.suppliers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Malaka
 */
public class FrmAddSupplierController implements Initializable {

    @FXML
    private Button btnSave;
    @FXML
    private Button btnClose;
    @FXML
    private TextField txtSupID;
    @FXML
    private TextField txtSupFName;
    @FXML
    private TextField txtSupLName;
    @FXML
    private TextField txtSupTelephone;
    @FXML
    private TextArea txtSupAddress;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnSave_onAction(ActionEvent event) {
    }

    @FXML
    private void btnClose_onClose(ActionEvent event) {
    }
    
}
