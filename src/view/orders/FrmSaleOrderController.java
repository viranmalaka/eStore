/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.orders;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Malaka
 */
public class FrmSaleOrderController implements Initializable {

    @FXML
    private DatePicker dtpOrderDate;
    @FXML
    private TextField txtCustomerID;
    @FXML
    private TextField txtCustomerName;
    @FXML
    private Button btnSearchCustomer;
    @FXML
    private Button btnAddNewCustomer;
    @FXML
    private TextField txtItemID;
    @FXML
    private TextField txtItemName;
    @FXML
    private Button btnItemSearch;
    @FXML
    private TextField txtQty;
    @FXML
    private Label lblUnit;
    @FXML
    private TextField txtDiscount;
    @FXML
    private Button btnRemove;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnCancle;
    @FXML
    private Label lblAvailableQty;
    @FXML
    private Label lblUnitPrice;
    @FXML
    private Label lblItemTotal;
    @FXML
    private Label lblTotal;
    @FXML
    private Button btnCreateOrder;
    @FXML
    private TableView<?> tblItems;
    @FXML
    private Button btnClose;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnSearchCustomer_onAction(ActionEvent event) {
    }

    @FXML
    private void btnAddNewCustomer_onAction(ActionEvent event) {
    }

    @FXML
    private void btnSearchItem_onAction(ActionEvent event) {
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
    private void btnCreateOrder_onAction(ActionEvent event) {
    }

    @FXML
    private void btnClose_onAction(ActionEvent event) {
    }
    
}
