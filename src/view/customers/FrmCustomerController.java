/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.customers;

import controller.CustomersController;
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
public class FrmCustomerController implements Initializable {

    @FXML
    private Button btnSave;
    @FXML
    private Button btnClose;
    @FXML
    private TextField txtCusID;
    @FXML
    private TextField txtCusFName;
    @FXML
    private TextField txtCusLName;
    @FXML
    private TextField txtCusTelephone;
    @FXML
    private TextArea txtCusAddress;
    @FXML
    private Label lblTitle;

    
    private boolean forEdit;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void initData(boolean edit){
        forEdit = edit;
        
        if (edit) {
            lblTitle.setText("Edit Customer");
        } else {
            lblTitle.setText("Add New Customer");
            
            //txtCusID.setText(CustomersController.getNextIndex());
        }
    }
    
    @FXML
    private void btnSave_onAction(ActionEvent event) {
            CustomersController.saveCustomer(Integer.parseInt(txtCusID.getText().substring(1)),
                    txtCusFName.getText(),
                    txtCusLName.getText(),
                    txtCusAddress.getText(),
                    txtCusTelephone.getText());
            //log
            //alert
    }

    @FXML
    private void btnClose_onClose(ActionEvent event) {
    }
    
}
