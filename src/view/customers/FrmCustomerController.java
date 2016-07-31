/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.customers;

import controller.CommonControllers;
import controller.CustomersController;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
        String cId = txtCusID.getText();
        String fname = txtCusFName.getText();
        String lname = txtCusLName.getText();
        String tp = txtCusTelephone.getText();
        String address = txtCusAddress.getText();

        //validating 
        switch (1) {
            case 1:
                String emptyItems = "";
                boolean hasEmpty = false;
                if (fname.equals("")) {
                    emptyItems += "First Name \n";
                    hasEmpty = true;
                }
                if (address.equals("")) {
                    emptyItems += "Address \n";
                    hasEmpty = true;
                }
                if (hasEmpty) {
                    UICommonController.showAlertBox(Alert.AlertType.ERROR,
                            emptyItems,
                            UICommonController.CommonTitles.VALIDATING_ERROR,
                            UICommonController.CommonHeadding.EMPTY_FIELDS);
                    break;
                }

                String errorFormatting = "";
                boolean hasErrorFormatting = false;
                if (!CommonControllers.isName(fname)) {
                    errorFormatting += String.format("'%s' is not a name \n", fname);
                    hasErrorFormatting = true;
                }
                if (!CommonControllers.isName(lname)) {
                    errorFormatting += String.format("'%s' is not a name \n", lname);
                    hasErrorFormatting = true;
                }
                if (!CommonControllers.isTelephoneNumber(tp)) {
                    errorFormatting += String.format("'%s' is not a telephone number \n", tp);
                    hasErrorFormatting = true;
                }

                if (hasErrorFormatting) {
                    UICommonController.showAlertBox(Alert.AlertType.ERROR,
                            errorFormatting,
                            UICommonController.CommonTitles.FORMATTING_ERROR,
                            UICommonController.CommonHeadding.INVALID_FORMATTINGS);
                    break;
                }

                if (forEdit) {
                    boolean updateCustomer = CustomersController.updateCustomer(cId, fname, lname, address, tp);
                    if (!updateCustomer) {
                        UICommonController.showAlertBox(Alert.AlertType.ERROR, "Error", "Customer is not Updated Successfully");
                        LogController.log(Level.ERROR, "Customer Updating is not done.");
                    } else {
                        UICommonController.showAlertBox(Alert.AlertType.INFORMATION, "Customer is Updated Successfully", "");
                        ((Stage) btnSave.getScene().getWindow()).close();
                        LogController.log(Level.INFO, "Customer Updated - " + cId);
                    }
                } else {
                    // adding new customer
                    boolean saveCustomer = CustomersController.saveCustomer(cId, fname, lname, address, tp);
                    if (!saveCustomer) {
                        UICommonController.showAlertBox(Alert.AlertType.ERROR, "Error", "Customer is not Saved Successfully");
                        LogController.log(Level.ERROR, "Customer Saving is not done.");
                    } else {
                        UICommonController.showAlertBox(Alert.AlertType.INFORMATION, "Customer is Saved Successfully", "");
                        ((Stage) btnSave.getScene().getWindow()).close();
                        LogController.log(Level.INFO, "New Customer Created - " + cId);
                    }
                }
        }
    }

    @FXML
    private void btnClose_onClose(ActionEvent event) {

        Optional<ButtonType> showAlertBox = UICommonController.showAlertBox(Alert.AlertType.INFORMATION, 
                "Are you sure ? you want to close.", 
                "",null,
                ButtonType.YES,ButtonType.NO);
        if (showAlertBox.get() == ButtonType.YES) {
            ((Stage) btnClose.getScene().getWindow()).close();
        }
    }

}
