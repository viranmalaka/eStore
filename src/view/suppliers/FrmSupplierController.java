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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Malaka
 */
public class FrmSupplierController implements Initializable {

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
    @FXML
    private Label lblTitle;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void initData(boolean edit){
        if (edit) {
            lblTitle.setText("Edit Supplier");
        } else {
            lblTitle.setText("Add New Supplier");
        }
    }

    @FXML
    private void btnSave_onAction(ActionEvent event) {
    }

    @FXML
    private void btnClose_onClose(ActionEvent event) {
    }
    
}
