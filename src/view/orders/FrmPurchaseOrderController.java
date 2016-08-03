/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.orders;

import controller.CommonControllers;
import controller.ItemController;
import controller.SupplierController;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import model.Item;
import model.Supplier;
import view.SelectController;
import view.UICommonController;

/**
 * FXML Controller class
 *
 * @author Malaka
 */
public class FrmPurchaseOrderController implements Initializable {

    //<editor-fold defaultstate="collapsed" desc="FXML Elements">
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
    private CheckBox chkPaid;
    @FXML
    private Label lblSupDetails;
    @FXML
    private Label lblUnit;
    @FXML
    private TextField txtPP;
    @FXML
    private TextField txtTP;
//</editor-fold>

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txtQty.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
        txtPP.setTextFormatter(new TextFormatter<>(new NumberStringConverter("#.00")));
        txtLabelPrice.setTextFormatter(new TextFormatter<>(new NumberStringConverter("#.00")));
        txtTP.setTextFormatter(new TextFormatter<>(new NumberStringConverter("#.00")));
    }
//<editor-fold defaultstate="collapsed" desc="Selecting Supplier Codes">

    @FXML
    private void btnSupplierSearch_onAction(ActionEvent event) {
        FXMLLoader createFXML = UICommonController.getInstance().createFXML("Select.fxml");
        Stage s = UICommonController.getInstance().getStage(createFXML);

        ((SelectController) createFXML.getController()).initData(this, Supplier.class);
        s.setResizable(false);
        s.showAndWait();
        if (((SelectController) createFXML.getController()).isSelected()) {
            SupplierController.setSupInPurchaseOrder(this, SupplierController.PersonColumns.SupplierID, txtSupplierID.getText());
        }
    }

    @FXML
    private void btn_AddNewSupplier_onAction(ActionEvent event) {
        if (SupplierController.openAddNewSupplierWindow()) {

            String id = CommonControllers.convertIndex(SupplierController.getNextIndex() - 1, 'S');
            txtSupplierID.setText(id);
            SupplierController.setSupInPurchaseOrder(this, SupplierController.PersonColumns.SupplierID, txtSupplierID.getText());
        }
    }

    @FXML
    private void txtSubName_onAction(ActionEvent event) {
        SupplierController.setSupInPurchaseOrder(this, SupplierController.PersonColumns.Name, txtSupplierName.getText());
    }

    @FXML
    private void txtSupID_onAction(ActionEvent event) {
        SupplierController.setSupInPurchaseOrder(this, SupplierController.PersonColumns.SupplierID, txtSupplierID.getText());
    }

    public void setSupplierValues(String id, String name, String add, String telephone) {
        txtSupplierID.setText(id);
        txtSupplierName.setText(name);
        lblSupDetails.setText("Address : " + add.replaceAll("\n", " ") + "; Telephone : " + telephone);
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Selecting Item Codes">
    @FXML
    private void btnItemSearch_onAction(ActionEvent event) {
        FXMLLoader createFXML = UICommonController.getInstance().createFXML("Select.fxml");
        Stage s = UICommonController.getInstance().getStage(createFXML);
        s.setResizable(false);
        ((SelectController) createFXML.getController()).initData(this, Item.class);

        s.showAndWait();
        if (((SelectController) createFXML.getController()).isSelected()) {
            ItemController.setItemInPurchaseOrder(this, ItemController.ItemColumns.ItemID, txtItemID.getText());
        }
    }

    @FXML
    private void btnAddNewItem_onAction(ActionEvent event) {
        if (ItemController.openAddNewItemWindow()) {
            String id = CommonControllers.convertIndex(ItemController.getNextIndex() - 1, 'I');
            txtItemID.setText(id);
            ItemController.setItemInPurchaseOrder(this, ItemController.ItemColumns.ItemID, txtItemID.getText());
        }
    }

    @FXML
    private void txtItemID_onAction(ActionEvent event) {
        ItemController.setItemInPurchaseOrder(this, ItemController.ItemColumns.ItemID, txtItemID.getText());
    }

    @FXML
    private void txtItemName_onAction(ActionEvent event) {
        ItemController.setItemInPurchaseOrder(this, ItemController.ItemColumns.Name, txtSupplierName.getText());
    }

    public void setItemValue(String id, String name, String scale) {
        txtItemID.setText(id);
        txtItemName.setText(name);
        lblUnit.setText(scale);
    }
//</editor-fold>

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
    private void txtQty_type(KeyEvent event) {
        try {
            String up = txtPP.getText();
            String tp = txtTP.getText();
            double q = Double.parseDouble(txtQty.getText());

            if (!up.equals("") && !tp.equals("")) {
                txtTP.setText(String.valueOf(Double.parseDouble(txtPP.getText()) * q));
            } else if (!up.equals("") && tp.equals("")) {
                txtTP.setText(String.valueOf(Double.parseDouble(txtPP.getText()) * q));
            } else if (up.equals("") && !tp.equals("")) {
                txtPP.setText(String.valueOf(Double.parseDouble(txtTP.getText()) / q));
            }

        } catch (NumberFormatException numberFormatException) {
        }
    }

    @FXML
    private void txtUP_type(KeyEvent event) {
        try {
            String q = txtQty.getText();
            String tp = txtTP.getText();
            double up = Double.parseDouble(txtPP.getText());

            if (!q.equals("") && !tp.equals("")) {
                txtTP.setText(String.valueOf(Double.parseDouble(txtQty.getText()) * up));
            } else if (!q.equals("") && tp.equals("")) {
                txtTP.setText(String.valueOf(Double.parseDouble(txtQty.getText()) * up));
            } else if (q.equals("") && !tp.equals("")) {
                txtQty.setText(String.valueOf(Double.parseDouble(txtTP.getText()) / up));
            }
        } catch (NumberFormatException numberFormatException) {
        }
    }

    @FXML
    private void txtTP_type(KeyEvent event) {
        try {
            String up = txtPP.getText();
            String q = txtQty.getText();
            double t = Double.parseDouble(txtTP.getText());

            if (!up.equals("") && !q.equals("")) {
                txtPP.setText(String.valueOf(t / Double.parseDouble(txtQty.getText())));
            } else if (!up.equals("") && q.equals("")) {
                txtQty.setText(String.valueOf(t / Double.parseDouble(txtPP.getText())));
            } else if (up.equals("") && !q.equals("")) {
                txtPP.setText(String.valueOf(t / Double.parseDouble(txtQty.getText())));
            }
        } catch (NumberFormatException numberFormatException) {
        }
    }

    public class ItemTableRaw {

    }
}
