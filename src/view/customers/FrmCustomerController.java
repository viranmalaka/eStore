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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logger.LogController;
import org.apache.logging.log4j.Level;
import view.UICommonController;

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

    public void initData(String newCustomerID) { // for new customer
        lblTitle.setText("Add New Customer");
        txtCusID.setText(newCustomerID);
        forEdit = false;
        LogController.log(Level.TRACE, "Open -> Add New Customer Window");
    }

    public void initData(String customerID, String fName, String lName, String telephone, String address) {
        // for edit the customer
        lblTitle.setText("Edit Customer");
        txtCusID.setText(customerID);
        txtCusFName.setText(fName);
        txtCusLName.setText(lName);
        txtCusTelephone.setText(telephone);
        txtCusAddress.setText(address);
        forEdit = true;
        LogController.log(Level.TRACE, "Open -> Edit Customer Window");
    }

    @FXML
    private void btnSave_onAction(ActionEvent event) {
        //validating 
        switch (1) {case 1:
            String emptyItems = "";
            boolean hasEmpty = false;
            if (txtCusFName.getText().equals("")) {
                emptyItems += "First Name \n"; hasEmpty = true;
            }
            if (txtCusAddress.getText().equals("")) {
                emptyItems += "Address \n"; hasEmpty = true;
            }
            if (hasEmpty) {
                UICommonController.showAlertBox(Alert.AlertType.ERROR,
                        emptyItems,
                        UICommonController.CommonTitles.VALIDATING_ERROR,
                        UICommonController.CommonMessages.EMPTY_FIELDS);
                break;
            }
            
            
            
        }
        
        CustomersController.saveCustomer(txtCusID.getText(),
                txtCusFName.getText(),
                txtCusLName.getText(),
                txtCusAddress.getText(),
                txtCusTelephone.getText());
        ((Stage)btnSave.getScene().getWindow()).close();
        //log
        //alert
    }

    @FXML
    private void btnClose_onClose(ActionEvent event) {
    }

}
