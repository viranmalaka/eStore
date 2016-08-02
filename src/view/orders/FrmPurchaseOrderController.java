/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.orders;

import controller.CustomersController;
import controller.SupplierController;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;

/**
 * FXML Controller class
 *
 * @author Malaka
 */
public class FrmPurchaseOrderController implements Initializable {

    @FXML
    private DatePicker dtpOrderDate;
    @FXML
    private TextField txtSupplierID;
    @FXML
    private TextField txtSupplierName;
    @FXML
    private Button btnSupplierSearch;
    @FXML
    private Button btn_AddNewSupplier;
    @FXML
    private TextField txtItemID;
    @FXML
    private TextField txtItemName;
    @FXML
    private Button btnItemSearch;
    @FXML
    private TextField txtQty;
    @FXML
    private DatePicker dtpExpireDate;
    @FXML
    private TextField txtLabelPrice;
    @FXML
    private TextField txtPurchasePrice;
    @FXML
    private Button btnRemove;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnCancle;
    @FXML
    private Label lblTotal;
    @FXML
    private Button btnSaveOrder;
    @FXML
    private Button btnClose;
    @FXML
    private TableView<?> tblItems;
    @FXML
    private TextField txtOrderID;
    @FXML
    private TextField txtPurchasePrice1;
    @FXML
    private CheckBox chkPaid;
    @FXML
    private Label lblSupDetails;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    

    @FXML
    private void btnSupplierSearch_onAction(ActionEvent event) {
    }

    @FXML
    private void btn_AddNewSupplier_onAction(ActionEvent event) {
    }

    @FXML
    private void btnItemSearch_onAction(ActionEvent event) {
    }

    @FXML
    private void dtpExpireDate_onChange(InputMethodEvent event) {
    }

    @FXML
    private void btnRemove_onAction(ActionEvent event) {
    }

    @FXML
    private void btnAdd_onAction(ActionEvent event) {
    }

    @FXML
    private void btnCancle_onAction(ActionEvent event) {
    }

    @FXML
    private void btnSaveOrder_onAction(ActionEvent event) {
    }

    @FXML
    private void btnClose_onAction(ActionEvent event) {
    }

    public void initData(String newPOId) { // create
        txtOrderID.setText(newPOId);
        dtpOrderDate.setValue(LocalDate.now());
        
    }

    @FXML
    private void txtSupID_onAction(ActionEvent event) {
        SupplierController.setSupInPurchaseOrder(this, SupplierController.PersonColumns.SupplierID, txtSupplierID.getText());
    }

    @FXML
    private void txtSubName_onAction(ActionEvent event) {
        SupplierController.setSupInPurchaseOrder(this, SupplierController.PersonColumns.Name, txtSupplierID.getText());
    }

    public void setSupplierValues(String id, String name, String add, String telephone) {
        txtSupplierID.setText(id);
        txtSupplierName.setText(name);
        lblSupDetails.setText("Address : " + add + "; Telephone : " + telephone);
    }

    @FXML
    private void txtsupID_onTxtChange(InputMethodEvent event) {
        txtSupplierName.setText("");
        lblSupDetails.setText("Address and Telephone");
    }

    @FXML
    private void txtSupName_onTxtChange(InputMethodEvent event) {
        txtSupplierID.setText("");
        lblSupDetails.setText("Address and Telephone");
    }

    
    
    public class ItemTableRaw{
        
    }
}
