/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.suppliers;

import controller.CommonControllers;
import controller.SupplierController;
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

    boolean foredit;
    private boolean added;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void initData(String supplierID, String firstName, String lastName, String telephone, String address) {
        //for edit supplier
        lblTitle.setText("Edit Supplier");
        txtSupID.setText(supplierID);
        txtSupFName.setText(firstName);
        txtSupLName.setText(lastName);
        txtSupTelephone.setText(telephone);
        txtSupAddress.setText(address);
        foredit = true;

        LogController.log(Level.TRACE, "Open -> Edit Supplier Window");
    }

    public void initData(String supplierID) { // for new supplier
        lblTitle.setText("Add New Supplier");
        txtSupID.setText(supplierID);
        foredit = false;

        LogController.log(Level.TRACE, "Open -> Add New Supplier Window");
    }

    @FXML
    private void btnSave_onAction(ActionEvent event) {
        String sId = txtSupID.getText();
        String fname = txtSupFName.getText();
        String lname = txtSupLName.getText();
        String tp = txtSupTelephone.getText();
        String address = txtSupAddress.getText();

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

                if (foredit) {
                    boolean updateSupplier = SupplierController.updateSupplier(sId, fname, lname, address, tp);
                    if (!updateSupplier) {
                        UICommonController.showAlertBox(Alert.AlertType.ERROR, "Error", "Supplier is not Updated Successfully");
                        LogController.log(Level.ERROR, "Supplier Updating is not done.");
                        added = false;
                    } else {
                        UICommonController.showAlertBox(Alert.AlertType.INFORMATION, "Supplier is Updated Successfully", "");
                        ((Stage) btnSave.getScene().getWindow()).close();
                        LogController.log(Level.INFO, "Supplier Updated - " + sId);
                        added = true;
                    }
                } else {
                    // adding new supplier
                    boolean saveSupplier = SupplierController.saveSupplier(sId, fname, lname, address, tp);
                    if (!saveSupplier) {
                        UICommonController.showAlertBox(Alert.AlertType.ERROR, "Error", "Supplier is not Saved Successfully");
                        LogController.log(Level.ERROR, "Supplier Saving is not done.");
                        added = false;
                    } else {
                        UICommonController.showAlertBox(Alert.AlertType.INFORMATION, "Supplier is Saved Successfully", "");
                        ((Stage) btnSave.getScene().getWindow()).close();
                        LogController.log(Level.INFO, "New Supplier Created - " + sId);
                        added = true;
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
            added = false;
        }
    }

    public boolean isAdded() {
        return added;
    }

}
